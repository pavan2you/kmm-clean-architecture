package io.tagd.langx

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

actual class Calendar {

    private var delegate: java.util.Calendar = java.util.Calendar.getInstance()

    actual var time: Time
        get() = Time(delegate.timeInMillis, Time.Unit.MILLI_SECONDS)
        set(value) {
            delegate.timeInMillis = value.toMillis().value
        }

    actual var timezone: Timezone
        get() = Timezone.wrap(delegate.timeZone)
        set(value) {
            delegate.timeZone = value.delegate
        }

    private var _locale: Locale = Locale.default()

    actual var locale: Locale
        get() = _locale
        set(value) {
            _locale = value
        }

    actual fun set(field: Field, value: Int) {
        delegate.set(field.value, value)
    }

    actual fun get(field: Field): Int {
        return delegate.get(field.value)
    }

    actual fun isSet(field: Field): Boolean {
        return delegate.isSet(field.value)
    }

    actual fun format(pattern: String): String {
        val formatter = SimpleDateFormat(pattern, locale.delegate)
        return formatter.format(delegate.time)
    }

    actual fun timezoneTime(): Time {
        //todo must consider dst
        return Time(time.value + timezone.rawOffset.toLong(), Time.Unit.MILLI_SECONDS)
    }

    actual enum class Field(val value: Int) {
        ERA(java.util.Calendar.ERA),

        YEAR(java.util.Calendar.YEAR),

        MONTH(java.util.Calendar.MONTH),

        WEEK_OF_YEAR(java.util.Calendar.WEEK_OF_YEAR),

        WEEK_OF_MONTH(java.util.Calendar.WEEK_OF_MONTH),

        DAY_OF_MONTH(java.util.Calendar.DAY_OF_MONTH),

        DAY_OF_YEAR(java.util.Calendar.DAY_OF_YEAR),

        DAY_OF_WEEK(java.util.Calendar.DAY_OF_WEEK),

        DAY_OF_WEEK_IN_MONTH(java.util.Calendar.DAY_OF_WEEK_IN_MONTH),

        AM_PM(java.util.Calendar.AM_PM),

        HOUR(java.util.Calendar.HOUR),

        HOUR_OF_DAY(java.util.Calendar.HOUR_OF_DAY),

        MINUTE(java.util.Calendar.MINUTE),

        SECOND(java.util.Calendar.SECOND),

        MILLISECOND(java.util.Calendar.MILLISECOND),

        ZONE_OFFSET(java.util.Calendar.ZONE_OFFSET),

        DST_OFFSET(java.util.Calendar.DST_OFFSET);

        actual fun value(): Int {
            return value
        }
    }

    actual object Month {
        actual val JANUARY: Int = java.util.Calendar.JANUARY

        actual val FEBRUARY: Int = java.util.Calendar.FEBRUARY

        actual val MARCH: Int = java.util.Calendar.MARCH

        actual val APRIL: Int = java.util.Calendar.APRIL

        actual val MAY: Int = java.util.Calendar.MAY

        actual val JUNE: Int = java.util.Calendar.JUNE

        actual val JULY: Int = java.util.Calendar.JULY

        actual val AUGUST: Int = java.util.Calendar.AUGUST

        actual val SEPTEMBER: Int = java.util.Calendar.SEPTEMBER

        actual val OCTOBER: Int = java.util.Calendar.OCTOBER

        actual val NOVEMBER: Int = java.util.Calendar.NOVEMBER

        actual val DECEMBER: Int = java.util.Calendar.DECEMBER
    }

    actual companion object {

        actual fun instance(): Calendar {
            return Calendar()
        }

        actual fun instance(timeInMs: Long): Calendar {
            return Calendar().apply {
                this.time = Time(timeInMs, Time.Unit.MILLI_SECONDS)
            }
        }

        actual fun instance(time: Time): Calendar {
            return Calendar().apply {
                this.time = time
            }
        }

        actual fun instance(date: Date): Calendar {
            return Calendar().apply {
                this.time = Time(date.time, Time.Unit.MILLI_SECONDS)
            }
        }

        actual fun instance(timezone: Timezone): Calendar {
            return Calendar().apply {
                this.timezone = timezone
            }
        }

        actual fun instance(locale: Locale): Calendar {
            return Calendar().apply {
                this.locale = locale
            }
        }

        actual fun instance(dateLabel: String, pattern: String): Calendar? {
            return try {
                val sdf = SimpleDateFormat.getDateInstance()
                val date = sdf.parse(dateLabel)
                val timezone = sdf.timeZone
                Calendar().apply {
                    this.time = Time(date!!.time, Time.Unit.MILLI_SECONDS)
                    this.timezone = Timezone.wrap(timezone)
                }
            } catch (e: Exception) {
                null
            }
        }

        actual fun instance(
            timezone: Timezone,
            locale: Locale
        ): Calendar {

            return Calendar().apply {
                this.timezone = timezone
                this.locale = locale
            }
        }

        actual fun instance(
            date: Date,
            timezone: Timezone,
            locale: Locale
        ): Calendar {

            return Calendar().apply {
                this.time = Time(date.time, Time.Unit.MILLI_SECONDS)
                this.timezone = timezone
                this.locale = locale
            }
        }

        actual fun instance(
            time: Long,
            timezone: Timezone,
            locale: Locale
        ): Calendar {

            return Calendar().apply {
                this.time = Time(time, Time.Unit.MILLI_SECONDS)
                this.timezone = timezone
                this.locale = locale
            }
        }
    }
}