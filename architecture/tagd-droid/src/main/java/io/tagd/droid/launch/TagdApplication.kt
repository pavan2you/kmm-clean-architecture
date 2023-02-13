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

package io.tagd.droid.launch

import android.app.Application
import android.app.Notification
import android.net.Uri
import androidx.annotation.MainThread
import io.tagd.arch.control.ApplicationController
import io.tagd.arch.control.IApplication
import io.tagd.arch.control.LifeCycleAwareApplicationController
import io.tagd.arch.domain.crosscutting.async.cancelAsync
import io.tagd.di.Global
import io.tagd.di.Key
import io.tagd.di.get
import io.tagd.droid.lifecycle.ReadyLifeCycleEventDispatcher

open class TagdApplication : Application(), IApplication {

    enum class LaunchSource {
        UNKNOWN, LAUNCHER, NOTIFICATION, DEEPLINK, CONTENT, SYSTEM
    }

    enum class State {
        INIT, LAUNCHING, READY, EXIT
    }

    var appState : State = State.INIT
        protected set

    var launchSource : LaunchSource = LaunchSource.UNKNOWN

    protected var controller: ApplicationController<IApplication>? = null

    override fun onCreate() {
        super.onCreate()
        setup()
        controller = onCreateController()
        controller?.onCreate()

        onInject()
        onLaunch()
    }

    protected open fun onCreateController(): ApplicationController<IApplication> =
        LifeCycleAwareApplicationController(this)

    protected open fun setup() {
        setupExceptionHandler()
        setupInjector()
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

    protected open fun onLaunch() {
        appState = State.LAUNCHING
        controller?.onLaunch()

        onLoading()
        controller?.onLoading()
    }

    /**
     * The applications must override this to load any long running launch time dependencies
     * like system libraries, database setup etc
     */
    protected open fun onLoading() {
        dispatchOnLoadingComplete()
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
        appState = State.READY
        controller?.onReady()
        appService<ReadyLifeCycleEventDispatcher>()?.dispatchOnReady()
    }

    @MainThread
    open fun onForeground() {
        controller?.onForeground()
    }

    @MainThread
    open fun onBackground() {
        controller?.onBackground()
    }

    @MainThread
    open fun onPushNotification(notification: Notification) {
    }

    @MainThread
    open fun onDeeplink(uri: Uri) {
    }

    inline fun <reified S : AppService> appService(key: Key<S>? = null): S? {
        return Global.get<AppService, S>(key ?: io.tagd.di.key())
    }

    override fun <A : IApplication> controller(): ApplicationController<A>? =
        controller as? ApplicationController<A>

    override fun onTerminate() {
        onExit()
        super.onTerminate()
    }

    @MainThread
    open fun onExit() {
        release()
    }

    override fun release() {
        appState = State.EXIT
        cancelAsync(this)
        controller?.onDestroy()
        controller = null
    }
}