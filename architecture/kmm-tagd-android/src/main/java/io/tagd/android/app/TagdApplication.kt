/*
 * Copyright (C) 2021 The TagD Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.tagd.android.app

import android.app.Activity
import android.app.Application
import android.app.Notification
import android.app.Service
import android.content.BroadcastReceiver
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import io.tagd.android.app.loadingstate.AppLoadingStateHandler
import io.tagd.android.app.loadingstate.AppLoadingStepDispatcher
import io.tagd.arch.control.AppService
import io.tagd.arch.control.ApplicationController
import io.tagd.arch.control.ApplicationInjector
import io.tagd.arch.control.IApplication
import io.tagd.arch.control.LifeCycleAwareApplicationController
import io.tagd.arch.control.VersionTracker
import io.tagd.arch.domain.crosscutting.async.cancelAsync
import io.tagd.arch.domain.crosscutting.async.present
import io.tagd.arch.present.mvp.PresentableView
import io.tagd.arch.present.mvp.PresenterFactory
import io.tagd.arch.scopable.ScopableManager
import io.tagd.di.Global
import io.tagd.di.Key
import io.tagd.di.Scope
import io.tagd.di.get

/**
 * The [TagdApplication] addresses some of the core problems associated to an android [Application].
 * It comes with
 * - enriched states - [TagdApplication.State]
 * - handles injection
 * - handles launch time loading
 * - default exception handler
 * - an application controller
 */
open class TagdApplication : Application(), IApplication {

    /**
     * The [TagdApplication] states
     */
    enum class State {
        /**
         * The state from the [TagdApplication] construction to [onCreate] end.
         */
        INITIALIZING,

        /**
         * The state for any app level loading work. This is the next state after [INITIALIZING].
         * In [LOADING] state, the [TagdApplication] is supposed to perform
         * - loading system libraries
         * - dependency injection
         * - retrieving application specific global state
         * - performing upgrade
         * - fetch remote config
         */
        LOADING,

        /**
         * When the application completes loading launch time dependencies, then it moves to [READY]
         * state. This is safe to assume that, the app is now have all the global state,
         * dependencies, libraries etc.
         *
         * This is the first opportunity to trigger any domain logic.
         */
        READY,

        /**
         * When the last shown window(/activity) goes to background, then
         * [ActivityLifeCycleObserver] determines whether it is a true [BACKGROUND] state to app.
         */
        BACKGROUND,

        /**
         * When an application specific window(/activity) comes into visibility, then
         * [ActivityLifeCycleObserver] determines whether it is a true [FOREGROUND] state to app.
         */
        FOREGROUND,

        /**
         * When the application's process is going to be killed then it will be called.
         */
        RELEASED
    }

    final override val outerScope: Scope
        get() = Global

    /**
     * Note - if the client overrides [name], then they must override [thisScope] as well
     */
    override val name: String = "application"

    @Suppress("LeakingThis")
    override val thisScope: Scope = Scope(name).also {
        Global.addSubScope(it)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected var lifecycleState: State = State.INITIALIZING
        private set

    private lateinit var loadingStateHandler: AppLoadingStateHandler

    private lateinit var loadingStepDispatcher: AppLoadingStepDispatcher

    private lateinit var launcherResolver: LauncherResolver

    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var launcher: Launcher<*>
        private set

    protected open var controller: ApplicationController<*>? = null

    private lateinit var versionTracker: VersionTracker

    private var hasVersionChange: Boolean = false

    var scopableManager: ScopableManager? = null
        private set

    protected open var activityLifecycleObserver: ActivityLifeCycleObserver? = null

    @Suppress("MemberVisibilityCanBePrivate")
    val currentActivity
        get() = activityLifecycleObserver?.currentActivity()

    @Suppress("MemberVisibilityCanBePrivate")
    val previousActivity
        get() = activityLifecycleObserver?.previousActivity()

    /**
     * Unsafe if Activity is not a [PresentableView]
     */
    override fun currentView(): PresentableView? {
        return currentActivity as? PresentableView
    }

    /**
     * Unsafe if Activity is not a [PresentableView]
     */
    override fun previousView(): PresentableView? {
        return previousActivity as? PresentableView
    }

    override fun versionTracker(): VersionTracker {
        return versionTracker
    }

    override fun controller(): ApplicationController<*>? = controller

    protected open var presenterFactory = PresenterFactory("presenter-factory")

    override fun presenterFactory(): PresenterFactory? {
        return presenterFactory
    }

    inline fun <reified S : AppService> appService(key: Key<S>? = null): S? {
        return thisScope.get<AppService, S>(key ?: io.tagd.di.key())
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////  Application's Life Cycle  ///////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        @Suppress("LeakingThis")
        controller = onCreateController()
    }

    override fun onCreate() {
        setupSelf()
        setupController()
        super.onCreate()
        dispatchLoading()
    }

    /* ------------------------------------  Setup Self ----------------------------------------- */

    protected open fun setupSelf() {
        setupActivityLifeCycleObserver()
        setupScopableManager()
        setupLoadingStateHandler()
        setupLauncherResolver()
        setupExceptionHandler()
        setupInjector()
    }

    private fun setupActivityLifeCycleObserver() {
        registerActivityLifecycleCallbacks(newActivityLifeCycleObserver())
    }

    fun dispatchOnActivityPreCreate(
        activity: Activity,
        savedInstanceState: Bundle?
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            activityLifecycleObserver?.onActivityPreCreatedCompat(activity, savedInstanceState)
        }
    }

    private fun setupScopableManager() {
        scopableManager = newScopableManager()
    }

    protected open fun newScopableManager(): ScopableManager {
        return AppScopableManager()
    }

    private fun setupLoadingStateHandler() {
        loadingStepDispatcher = newLoadingStepDispatcher()
        loadingStateHandler = newLoadingStateHandler(loadingStepDispatcher)
    }

    private fun setupLauncherResolver() {
        launcherResolver = newLauncherResolver()
    }

    private fun setupExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(newUncaughtExceptionHandler())
    }

    private fun setupInjector() {
        newInjector(loadingStateHandler).also { injector ->
            ApplicationInjector.setInjector(injector)
            injector.setup()
        }
    }

    /* ----------------------------------  Setup Controller  ------------------------------------ */

    private fun setupController() {
        controller?.onCreate()
    }

    protected open fun onCreateController(): ApplicationController<*> =
        LifeCycleAwareApplicationController(this)


    /* -----------------------------  Life Cycle State Dispatcher  ------------------------------ */

    private fun dispatchLoading() {
        lifecycleState = State.LOADING
        onLoading()
        controller?.onLoading()
    }

    @WorkerThread
    internal fun dispatchInject() {
        onInject()
    }

    private fun dispatchLaunch() {
        this.onLaunch()
        controller?.onLaunch()
        loadingStepDispatcher.dispatchStepComplete(
            AppLoadingStateHandler.Steps.LAUNCHING
        )
    }

    @WorkerThread
    internal fun dispatchUpgrade() {
        onUpgrade(versionTracker.previousVersion, versionTracker.currentVersion)
        controller?.onUpgrade(versionTracker.previousVersion, versionTracker.currentVersion)
    }

    @MainThread
    internal fun dispatchReady() {
        onReady()
    }

    /* ------------------------------  Life Cycle State Handler  -------------------------------- */

    /**
     * The applications must override this to load any long running launch time dependencies
     * like system libraries, database setup etc
     */
    @MainThread
    protected open fun onLoading() {
        loadingStateHandler.start()
    }

    fun resolveLauncher(activity: Activity, savedInstanceState: Bundle?) {
        launcher = launcherResolver.resolve(activity, savedInstanceState)
        dispatchLaunch()
    }

    fun resolveLauncher(service: Service, marshalledJob: String) {
        launcher = launcherResolver.resolve(service, marshalledJob)
        dispatchLaunch()
    }

    fun resolveLauncher(receiver: BroadcastReceiver, marshalledEvent: String) {
        launcher = launcherResolver.resolve(receiver, marshalledEvent)
        dispatchLaunch()
    }

    protected open fun onLaunch() {
        //no-op
    }

    protected open fun onInject() {
        appService<ApplicationInjector<TagdApplication>>()?.inject()
    }

    /**
     * This must be called after [onInject], the reason being [newVersionTracker] may need
     * IO dependencies to read versions. Example - reading version information from
     * SharedPreferences.
     */
    internal fun setupVersionTracker() {
        versionTracker = newVersionTracker()
    }

    open fun onUpgrade(oldVersion: Int, currentVersion: Int) {
        present(delay = 1L) {
            loadingStepDispatcher.dispatchStepComplete(
                AppLoadingStateHandler.Steps.UPGRADING
            )
        }
    }

    @MainThread
    protected open fun onReady() {
        lifecycleState = State.READY
        controller?.onReady()
        appService<AwaitReadyLifeCycleEventsDispatcher>()?.dispatchReady()
    }

    @MainThread
    open fun onBackground() {
        lifecycleState = State.BACKGROUND
        controller?.onBackground()
    }

    @MainThread
    open fun onForeground() {
        lifecycleState = State.FOREGROUND
        controller?.onForeground()
    }

    /* ---------------------  Setup's Default Implementations Provider  ------------------------- */

    protected open fun newActivityLifeCycleObserver(): ActivityLifeCycleObserver {
        return ActivityLifeCycleObserver(this).also {
            activityLifecycleObserver = it
        }
    }

    protected open fun newLoadingStateHandler(
        dispatcher: AppLoadingStepDispatcher
    ): AppLoadingStateHandler {

        return AppLoadingStateHandler(dispatcher)
    }

    protected open fun newLoadingStepDispatcher(): AppLoadingStepDispatcher {
        return AppLoadingStepDispatcher(this)
    }

    protected open fun newLauncherResolver(): LauncherResolver =
        LauncherResolver.Builder()
            .schemes(listOf("http", "https"))
            .hosts(listOf())
            .build()

    protected open fun newUncaughtExceptionHandler(): AppUncaughtExceptionHandler {
        return AppUncaughtExceptionHandler(
            this,
            Thread.getDefaultUncaughtExceptionHandler()
        )
    }

    protected open fun newInjector(
        handler: AppLoadingStateHandler
    ): ApplicationInjector<out TagdApplication> {

        return TagdApplicationInjector(this, handler)
    }

    protected open fun newVersionTracker(): VersionTracker {
        return VersionTracker(1, 1)
    }

    @MainThread
    open fun onPushNotification(notification: Notification) {
        //no-op
    }

    @MainThread
    open fun onDeeplink(uri: Uri) {
        //no-op
    }

    fun launcherDeepLink(): Uri? {
        return if (launcher is DeepLinkLauncher) {
            (launcher as DeepLinkLauncher).uri
        } else {
            null
        }
    }

    /* -----------------------------  Application's Exit Handler  ------------------------------- */

    override fun onTerminate() {
        onExit()
        super.onTerminate()
    }

    @MainThread
    open fun onExit() {
        release()
    }

    override fun release() {
        lifecycleState = State.RELEASED
        cancelAsync(this)
        loadingStepDispatcher.release()
        loadingStateHandler.release()
        scopableManager?.release()
        controller?.onDestroy()
        controller = null
    }
}