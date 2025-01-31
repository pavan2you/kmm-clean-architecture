package io.tagd.arch.app

interface ApplicationLifeCycleListener : AppService {

    fun onForeground(application: IApplication)

    fun onBackground(application: IApplication)
}