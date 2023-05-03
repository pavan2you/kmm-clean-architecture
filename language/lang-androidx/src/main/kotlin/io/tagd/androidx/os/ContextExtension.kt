package io.tagd.androidx.os

import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import io.tagd.langx.ref.WeakReference

fun Context.weakConnectivityManager(): WeakReference<ConnectivityManager>? {
    return (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.let {
        WeakReference(it)
    }
}

fun Context.weakTelephonyManager(): WeakReference<TelephonyManager>? {
    return (getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager)?.let {
        WeakReference(it)
    }
}

fun Context.weakNotificationManager() : WeakReference<NotificationManager>? {
    return (getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.let {
        WeakReference(it)
    }
}