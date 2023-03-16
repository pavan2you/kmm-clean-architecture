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
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import io.tagd.arch.control.ApplicationController
import io.tagd.arch.control.IApplication
import io.tagd.arch.control.LifeCycleAwareApplicationController
import io.tagd.arch.domain.crosscutting.async.cancelAsync
import io.tagd.arch.domain.crosscutting.async.present
import io.tagd.di.Global
import io.tagd.di.Key
import io.tagd.di.get

open class TagdApplication : Application(), IApplication {

    enum class State {
        INITIALIZING, LAUNCHING, LOADING, READY, BACKGROUND, FOREGROUND, RELEASED
    }

    protected var controller: ApplicationController<*>? = null

    var lifecycleState : State = State.INITIALIZING
        private set

    private lateinit var launcherResolver : LauncherResolver

    lateinit var launcher : Launcher<*>

    override fun onCreate() {
        super.onCreate()
        setupSelf()
        initController()
    }

    protected open fun setupSelf() {
        setupLauncherResolver()
        setupActivityCallbacksObserver()
        setupExceptionHandler()
        setupInjector()
        onInject()
    }

    protected open fun setupLauncherResolver() {
        launcherResolver = configLauncherResolver()
    }

    protected open fun configLauncherResolver() =
        LauncherResolver.Builder().schemes(listOf("http", "https")).hosts(listOf()).build()

    fun resolveLauncher(activity: Activity, savedInstanceState: Bundle?) {
        launcher = launcherResolver.resolve(activity, savedInstanceState)
        onLaunch()
    }

    fun resolve(service: Service, marshalledJob: String) {
        launcher = launcherResolver.resolve(service, marshalledJob)
        onLaunch()
    }

    fun resolve(receiver: BroadcastReceiver, marshalledEvent: String) {
        launcher = launcherResolver.resolve(receiver, marshalledEvent)
        onLaunch()
    }

    private fun setupActivityCallbacksObserver() {
        registerActivityLifecycleCallbacks(ActivityLifeCycleObserver(this))
    }

    protected open fun setupExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(
            AppUncaughtExceptionHandler(
                this,
                Thread.getDefaultUncaughtExceptionHandler()
            )
        )
    }

    protected open fun setupInjector() {
        Injector.setInjector(Injector(this))
    }

    protected open fun onInject() {
        appService<Injector>()?.inject()
    }

    private fun initController() {
        controller = onCreateController()
        controller?.onCreate()
    }

    protected open fun onCreateController(): ApplicationController<*> =
        LifeCycleAwareApplicationController(this)

    protected open fun onLaunch() {
        lifecycleState = State.LAUNCHING
        interceptOnLaunch()
        controller?.onLaunch()

        lifecycleState = State.LOADING
        onLoading()
        controller?.onLoading()
    }

    protected open fun interceptOnLaunch() {
        //no-op
    }

    /**
     * The applications must override this to load any long running launch time dependencies
     * like system libraries, database setup etc
     */
    protected open fun onLoading() {
        Handler(Looper.getMainLooper()).postDelayed({
            dispatchOnLoadingComplete()
        }, 1L)
    }

    /**
     * If applications overriding [TagdApplication.onLoading] then they must ensure
     * [dispatchOnLoadingComplete] will be called as the last step
     */
    protected fun dispatchOnLoadingComplete() {
        onReady()
    }

    @MainThread
    protected open fun onReady() {
        lifecycleState = State.READY
        controller?.onReady()
        appService<AwaitReadyLifeCycleEventsDispatcher>()?.dispatchOnReady()
    }

    @MainThread
    open fun onForeground() {
        lifecycleState = State.FOREGROUND
        controller?.onForeground()
    }

    @MainThread
    open fun onBackground() {
        lifecycleState = State.BACKGROUND
        controller?.onBackground()
    }

    @MainThread
    open fun onPushNotification(notification: Notification) {
        //no-op
    }

    @MainThread
    open fun onDeeplink(uri: Uri) {
        //no-op
    }

    inline fun <reified S : AppService> appService(key: Key<S>? = null): S? {
        return Global.get<AppService, S>(key ?: io.tagd.di.key())
    }

    override fun controller(): ApplicationController<*>? = controller

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
        controller?.onDestroy()
        controller = null
    }
}