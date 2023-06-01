package io.tagd.convention.plugin.internal.android.androidblock

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

internal fun Project.configureBuildTypesInAndroidApplicationBlock(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {

    commonExtension.apply {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
            }
        }
    }
}