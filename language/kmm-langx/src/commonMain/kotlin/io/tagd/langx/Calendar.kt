package io.tagd.langx

expect class Calendar private constructor() {

    var time: Time

    var timezone: Timezone

    var locale: Locale

    fun set(field: Field, value: Int)

    fun get(field: Field): Int

    fun isSet(field: Field): Boolean

    fun format(pattern: String): String

    fun timezoneTime(): Time

    fun relative(accuracy: RelativeAccuracy = RelativeAccuracy.SECONDS): String

    /**
     * ERA
     * YEAR
     * MONTH
     * WEEK_OF_YEAR
     * WEEK_OF_MONTH
     * DAY_OF_MONTH
     * DAY_OF_YEAR
     * DAY_OF_WEEK
     * DAY_OF_WEEK_IN_MONTH
     * AM_PM
     * HOUR
     * HOUR_OF_DAY
     * MINUTE
     * SECOND
     * MILLISECOND
     * ZONE_OFFSET
     * DST_OFFSET
     */
    enum class Field {
        ERA,
        YEAR,
        MONTH,
        WEEK_OF_YEAR,
        WEEK_OF_MONTH,
        DAY_OF_MONTH,
        DAY_OF_YEAR,
        DAY_OF_WEEK,
        DAY_OF_WEEK_IN_MONTH,
        AM_PM,
        HOUR,
        HOUR_OF_DAY,
        MINUTE,
        SECOND,
        MILLISECOND,
        ZONE_OFFSET,
        DST_OFFSET;

        fun value(): Int
    }

    object Month {
        val JANUARY: Int
        val FEBRUARY: Int
        val MARCH: Int
        val APRIL: Int
        val MAY: Int
        val JUNE: Int
        val JULY: Int
        val AUGUST: Int
        val SEPTEMBER: Int
        val OCTOBER: Int
        val NOVEMBER: Int
        val DECEMBER: Int
    }

    enum class RelativeAccuracy {
        SECONDS,
        MINUTES,
        HOURS,
        DAYS;
    }

    companion object {

        fun instance(): Calendar

        fun instance(timeInMs: Long): Calendar

        fun instance(time: Time): Calendar

        fun instance(date: Date): Calendar

        fun instance(timezone: Timezone): Calendar

        fun instance(locale: Locale): Calendar

        fun instance(dateLabel: String, pattern: String): Calendar?

        fun instance(timezone: Timezone, locale: Locale): Calendar

        fun instance(date: Date, timezone: Timezone, locale: Locale): Calendar

        fun instance(time: Long, timezone: Timezone, locale: Locale): Calendar
    }
}