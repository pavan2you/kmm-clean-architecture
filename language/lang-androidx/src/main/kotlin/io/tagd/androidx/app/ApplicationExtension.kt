package io.tagd.androidx.app

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Application
import android.os.Build
import java.lang.reflect.InvocationTargetException

fun Application.processName(): String? {
    return if (Build.VERSION.SDK_INT >= 28) Application.getProcessName() else try {
        @SuppressLint("PrivateApi")
        val activityThread = Class.forName("android.app.ActivityThread")
        val methodName = "currentProcessName"
        val getProcessName = activityThread.getDeclaredMethod(methodName)
        getProcessName.invoke(null) as String
    } catch (e: ClassNotFoundException) {
        throw RuntimeException(e)
    } catch (e: NoSuchMethodException) {
        throw RuntimeException(e)
    } catch (e: IllegalAccessException) {
        throw RuntimeException(e)
    } catch (e: InvocationTargetException) {
        throw RuntimeException(e)
    }

    // Using the same technique as Application.getProcessName() for older devices
    // Using reflection since ActivityThread is an internal API
}

//Judge if it's in the main process
fun Application.isMainProcess(): Boolean {
    val am = getSystemService(android.content.Context.ACTIVITY_SERVICE) as ActivityManager?
    val processInfoList = am?.runningAppProcesses
    val mainProcessName: String = applicationInfo?.processName.orEmpty()
    val myPid: Int = android.os.Process.myPid()
    for (info in processInfoList.orEmpty()) {
        if (info.pid == myPid && mainProcessName == info.processName) {
            return true
        }
    }
    return false
}