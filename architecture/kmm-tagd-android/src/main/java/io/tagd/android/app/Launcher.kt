package io.tagd.android.app

import android.app.Activity
import android.app.Service
import android.content.BroadcastReceiver
import android.net.Uri
import android.os.Bundle
import java.lang.ref.WeakReference

class LauncherResolver private constructor(
    val schemes: List<String>,
    val hosts: List<String>
) {

    fun resolve(activity: Activity, savedInstanceState: Bundle?): Launcher<*> {
        val isAppDeepLink = activity.intent.data?.let { uri ->
            hosts.contains(uri.host) || schemes.contains(uri.scheme)
        } ?: kotlin.run { false }

        return if (isAppDeepLink) {
            DeepLinkLauncher(activity.intent.data!!)
        } else if (savedInstanceState == null) {
            PageLauncher(activity = WeakReference(activity))
        } else {
            SystemLauncher()
        }
    }

    fun resolve(service: Service, marshalledJob: String): JobLauncher {
        return JobLauncher(WeakReference(service), marshalledJob)
    }

    fun resolve(receiver: BroadcastReceiver, marshalledEvent: String): EventLauncher {
        return EventLauncher(WeakReference(receiver), marshalledEvent)
    }

    class Builder {

        private lateinit var schemes: List<String>
        private lateinit var hosts: List<String>

        fun schemes(schemes: List<String>): Builder {
            this.schemes = schemes
            return this
        }

        fun hosts(hosts: List<String>): Builder {
            this.hosts = hosts
            return this
        }

        fun build(): LauncherResolver {
            return LauncherResolver(schemes, hosts)
        }
    }
}

enum class Source {
    UNKNOWN,
    PAGE,
    NOTIFICATION,
    DEEPLINK,
    JOB /* Service */,
    EVENT /* Broad Cast Receiver */,
    SYSTEM
}

abstract class Launcher<T>(open val source: Source, val cause: T)

open class SystemLauncher : Launcher<String>(source = Source.SYSTEM, cause = "system")

open class PageLauncher(val activity: WeakReference<Activity>) :
    Launcher<String>(source = Source.PAGE, cause = activity.get()!!::class.java.name)

class DeepLinkLauncher(val uri: Uri) : Launcher<Uri>(source = Source.DEEPLINK, cause = uri)

class NotificationLauncher(marshalledNotification: String) :
    Launcher<String>(source = Source.NOTIFICATION, cause = marshalledNotification)

data class JobLauncher(val service: WeakReference<Service>, val marshalledJob: String) :
    Launcher<String>(source = Source.JOB, cause = marshalledJob)

data class EventLauncher(
    val receiver: WeakReference<BroadcastReceiver>,
    val marshalledEvent: String
) : Launcher<String>(source = Source.EVENT, cause = marshalledEvent)