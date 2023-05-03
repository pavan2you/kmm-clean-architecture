package io.tagd.androidx.app

import android.annotation.SuppressLint
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