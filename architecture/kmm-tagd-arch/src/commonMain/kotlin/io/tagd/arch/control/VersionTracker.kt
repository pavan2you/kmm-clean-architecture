package io.tagd.arch.control

import io.tagd.arch.datatype.DataObject

open class VersionTracker(val previousVersion: Int, val currentVersion: Int) : DataObject() {

    fun isVersionChange()
        = currentVersion > previousVersion

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as VersionTracker

        if (previousVersion != other.previousVersion) return false
        if (currentVersion != other.currentVersion) return false

        return true
    }

    override fun hashCode(): Int {
        var result = previousVersion
        result = 31 * result + currentVersion
        return result
    }

    override fun toString(): String {
        return "VersionTracker(previousVersion=$previousVersion, currentVersion=$currentVersion)"
    }
}