package io.tagd.langx

actual class Calendar {
    actual var time: Time
        get() = TODO("Not yet implemented")
        set(value) {}
    actual var timezone: Timezone
        get() = TODO("Not yet implemented")
        set(value) {}
    actual var locale: Locale
        get() = TODO("Not yet implemented")
        set(value) {}

    actual fun set(field: Calendar.Field, value: Int) {
    }

    actual fun get(field: Calendar.Field): Int {
        TODO("Not yet implemented")
    }

    actual fun isSet(field: Calendar.Field): Boolean {
        TODO("Not yet implemented")
    }

    actual fun format(pattern: String): String {
        TODO("Not yet implemented")
    }

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
    actual enum class Field {
        ERA, YEAR, MONTH, WEEK_OF_YEAR, WEEK_OF_MONTH, DAY_OF_MONTH, DAY_OF_YEAR, DAY_OF_WEEK, DAY_OF_WEEK_IN_MONTH, AM_PM, HOUR, HOUR_OF_DAY, MINUTE, SECOND, MILLISECOND, ZONE_OFFSET, DST_OFFSET;

        actual fun value(): Int {
            TODO("Not yet implemented")
        }

    }

    actual object Month {
        actual val JANUARY: Int
            get() = TODO("Not yet implemented")
        actual val FEBRUARY: Int
            get() = TODO("Not yet implemented")
        actual val MARCH: Int
            get() = TODO("Not yet implemented")
        actual val APRIL: Int
            get() = TODO("Not yet implemented")
        actual val MAY: Int
            get() = TODO("Not yet implemented")
        actual val JUNE: Int
            get() = TODO("Not yet implemented")
        actual val JULY: Int
            get() = TODO("Not yet implemented")
        actual val AUGUST: Int
            get() = TODO("Not yet implemented")
        actual val SEPTEMBER: Int
            get() = TODO("Not yet implemented")
        actual val OCTOBER: Int
            get() = TODO("Not yet implemented")
        actual val NOVEMBER: Int
            get() = TODO("Not yet implemented")
        actual val DECEMBER: Int
            get() = TODO("Not yet implemented")
    }

    actual companion object {
        actual fun instance(): Calendar {
            TODO("Not yet implemented")
        }

        actual fun instance(timeInMs: Long): Calendar {
            TODO("Not yet implemented")
        }

        actual fun instance(time: Time): Calendar {
            TODO("Not yet implemented")
        }

        actual fun instance(date: Date): Calendar {
            TODO("Not yet implemented")
        }

        actual fun instance(timezone: Timezone): Calendar {
            TODO("Not yet implemented")
        }

        actual fun instance(locale: Locale): Calendar {
            TODO("Not yet implemented")
        }

        actual fun instance(
            timezone: Timezone,
            locale: Locale
        ): Calendar {
            TODO("Not yet implemented")
        }

        actual fun instance(
            date: Date,
            timezone: Timezone,
            locale: Locale
        ): Calendar {
            TODO("Not yet implemented")
        }

        actual fun instance(
            time: Long,
            timezone: Timezone,
            locale: Locale
        ): Calendar {
            TODO("Not yet implemented")
        }

    }
}