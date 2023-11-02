package io.tagd.arch.control

interface ApplicationLifeCycleListener : AppService {

    fun onForeground(application: IApplication)

    fun onBackground(application: IApplication)
}