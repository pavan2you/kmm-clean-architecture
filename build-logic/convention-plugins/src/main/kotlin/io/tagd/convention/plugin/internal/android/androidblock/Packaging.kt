package io.tagd.convention.plugin.internal.android.androidblock

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

internal fun Project.configurePackageOptionsInAndroidBlock(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}