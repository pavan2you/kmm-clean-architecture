package io.tagd.langx.time

import io.tagd.langx.System

open class Interval(val start: UnixEpochInMillis, var end: UnixEpochInMillis) {

    open fun duration() = end.millisSince1970.millis - start.millisSince1970.millis

    fun copy(start: UnixEpochInMillis = this.start, end: UnixEpochInMillis = this.end): Interval {
        return Interval(start = start, end = end)
    }

    fun endNow() {
        end = UnixEpochInMillis(Millis(System.millis()))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Interval

        if (start != other.start) return false
        if (end != other.end) return false

        return true
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + end.hashCode()
        return result
    }

    override fun toString(): String {
        return "[start=$start, end=$end]"
    }

    companion object {

        fun now(): Interval {
            val now = UnixEpochInMillis(Millis(System.millis()))
            return Interval(start = now, end = now)
        }
    }
}

/**
 * Typically denotes an activity - any arbitrary work, which is starting at time T1 and ending at
 * time T2. Such pattern can use this.
 */
open class ActivityInterval<T>(
    val activity: T,
    start: UnixEpochInMillis,
    end: UnixEpochInMillis
) : Interval(start = start, end = end) {

    fun copy(
        activity: T = this.activity,
        start: UnixEpochInMillis = this.start,
        end: UnixEpochInMillis = this.end
    ): ActivityInterval<T> {

        return ActivityInterval(
            activity = activity,
            start = start,
            end = end
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        if (!super.equals(other)) return false

        other as ActivityInterval<*>

        if (activity != other.activity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (activity?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "[activity=$activity, start = $start, end = $end]"
    }
}