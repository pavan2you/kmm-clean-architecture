package io.tagd.langx

data class Time(var value: Long = 0L, val unit: Unit) {

    var converted: Float = 0f
        private set

    private val convertersMatrix = hashMapOf<Unit, HashMap<Unit, (Time) -> Time>>()

    init {
        initHoursConverters()
        initMinutesConverters()
        initSecondsConverters()
        initMillisConverters()
        initMicrosConverters()
        initNanosConverters()
    }

    private fun initHoursConverters() {
        val minutesConverters = hashMapOf<Unit, (Time) -> Time>()
        minutesConverters[Unit.WEEKS] = {
            assert(it.unit === Unit.HOURS)

            val millis = it.value * 60 * 60 * 1000L
            millsToDays(millis)
        }

        minutesConverters[Unit.DAYS] = {
            assert(it.unit === Unit.HOURS)

            val millis = it.value * 60 * 60 * 1000L
            millsToDays(millis)
        }

        minutesConverters[Unit.HOURS] = {
            assert(it.unit === Unit.HOURS)

            val millis = it.value * 60 * 60 * 1000L
            millsToMinutes(millis)
        }

        minutesConverters[Unit.MINUTES] = {
            assert(it.unit === Unit.HOURS)

            val millis = it.value * 60 * 60 * 1000L
            millsToMinutes(millis)
        }

        minutesConverters[Unit.SECONDS] = {
            assert(it.unit === Unit.HOURS)

            val millis = it.value * 60 * 60 * 1000L
            millsToSeconds(millis)
        }
        convertersMatrix[Unit.HOURS] = minutesConverters
    }

    private fun initMinutesConverters() {
        val minutesConverters = hashMapOf<Unit, (Time) -> Time>()
        minutesConverters[Unit.WEEKS] = {
            assert(it.unit === Unit.MINUTES)

            val millis = it.value * 60 * 1000L
            millsToDays(millis)
        }

        minutesConverters[Unit.DAYS] = {
            assert(it.unit === Unit.MINUTES)

            val millis = it.value * 60 * 1000L
            millsToDays(millis)
        }

        minutesConverters[Unit.HOURS] = {
            assert(it.unit === Unit.MINUTES)

            val millis = it.value * 60 * 1000L
            millsToMinutes(millis)
        }

        minutesConverters[Unit.MINUTES] = {
            assert(it.unit === Unit.MINUTES)

            val millis = it.value * 60 * 1000L
            millsToMinutes(millis)
        }

        minutesConverters[Unit.SECONDS] = {
            assert(it.unit === Unit.MINUTES)

            val millis = it.value * 60 * 1000L
            millsToSeconds(millis)
        }
        convertersMatrix[Unit.MINUTES] = minutesConverters
    }

    private fun initSecondsConverters() {
        val secondsConverters = hashMapOf<Unit, (Time) -> Time>()
        secondsConverters[Unit.WEEKS] = {
            assert(it.unit === Unit.SECONDS)

            val millis = it.value * 1000L
            millsToDays(millis)
        }

        secondsConverters[Unit.DAYS] = {
            assert(it.unit === Unit.SECONDS)

            val millis = it.value * 1000L
            millsToDays(millis)
        }

        secondsConverters[Unit.HOURS] = {
            assert(it.unit === Unit.SECONDS)

            val millis = it.value * 1000L
            millsToMinutes(millis)
        }

        secondsConverters[Unit.MINUTES] = {
            assert(it.unit === Unit.SECONDS)

            val millis = it.value * 1000L
            millsToMinutes(millis)
        }

        secondsConverters[Unit.SECONDS] = {
            assert(it.unit === Unit.SECONDS)

            val millis = it.value * 1000L
            millsToSeconds(millis)
        }
        convertersMatrix[Unit.SECONDS] = secondsConverters
    }

    private fun initMillisConverters() {
        val millisConverters = hashMapOf<Unit, (Time) -> Time>()
        millisConverters[Unit.WEEKS] = {
            assert(it.unit === Unit.MILLI_SECONDS)

            val millis = it.value
            millsToDays(millis)
        }

        millisConverters[Unit.DAYS] = {
            assert(it.unit === Unit.MILLI_SECONDS)

            val millis = it.value
            millsToDays(millis)
        }

        millisConverters[Unit.HOURS] = {
            assert(it.unit === Unit.MILLI_SECONDS)

            val millis = it.value
            millsToMinutes(millis)
        }

        millisConverters[Unit.MINUTES] = {
            assert(it.unit === Unit.MILLI_SECONDS)

            val millis = it.value
            millsToMinutes(millis)
        }

        millisConverters[Unit.SECONDS] = {
            assert(it.unit === Unit.MILLI_SECONDS)

            val millis = it.value
            millsToSeconds(millis)
        }
        convertersMatrix[Unit.MILLI_SECONDS] = millisConverters
    }

    private fun initMicrosConverters() {
        val microsConverters = hashMapOf<Unit, (Time) -> Time>()
        microsConverters[Unit.WEEKS] = {
            assert(it.unit === Unit.MICRO_SECONDS)

            val millis = it.value / 1000L
            millsToDays(millis)
        }

        microsConverters[Unit.DAYS] = {
            assert(it.unit === Unit.MICRO_SECONDS)

            val millis = it.value / 1000L
            millsToDays(millis)
        }

        microsConverters[Unit.HOURS] = {
            assert(it.unit === Unit.MICRO_SECONDS)

            val millis = it.value / 1000L
            millsToMinutes(millis)
        }

        microsConverters[Unit.MINUTES] = {
            assert(it.unit === Unit.MICRO_SECONDS)

            val millis = it.value / 1000L
            millsToMinutes(millis)
        }

        microsConverters[Unit.SECONDS] = {
            assert(it.unit === Unit.MICRO_SECONDS)

            val millis = it.value / 1000L
            millsToSeconds(millis)
        }
        convertersMatrix[Unit.MICRO_SECONDS] = microsConverters
    }

    private fun initNanosConverters() {
        val nanosConverters = hashMapOf<Unit, (Time) -> Time>()
        nanosConverters[Unit.WEEKS] = {
            assert(it.unit === Unit.NANO_SECONDS)

            val millis = it.value / 1000 * 1000L
            millsToDays(millis)
        }

        nanosConverters[Unit.DAYS] = {
            assert(it.unit === Unit.NANO_SECONDS)

            val millis = it.value / 1000 * 1000L
            millsToDays(millis)
        }

        nanosConverters[Unit.HOURS] = {
            assert(it.unit === Unit.NANO_SECONDS)

            val millis = it.value / 1000 * 1000L
            millsToMinutes(millis)
        }

        nanosConverters[Unit.MINUTES] = {
            assert(it.unit === Unit.NANO_SECONDS)

            val millis = it.value / 1000 * 1000L
            millsToMinutes(millis)
        }

        nanosConverters[Unit.SECONDS] = {
            assert(it.unit === Unit.NANO_SECONDS)

            val millis = it.value / 1000 * 1000L
            millsToSeconds(millis)
        }
        convertersMatrix[Unit.NANO_SECONDS] = nanosConverters
    }

    fun toYears(): Time {
        return convertersMatrix[unit]!![Unit.YEARS]!!.invoke(this)
    }

    fun toMonths(): Time {
        return convertersMatrix[unit]!![Unit.MONTHS]!!.invoke(this)
    }

    fun toWeeks(): Time {
        return convertersMatrix[unit]!![Unit.WEEKS]!!.invoke(this)
    }

    fun toDays(): Time {
        return convertersMatrix[unit]!![Unit.DAYS]!!.invoke(this)
    }

    fun toHours(): Time {
        return convertersMatrix[unit]!![Unit.HOURS]!!.invoke(this)
    }

    fun toMinutes(): Time {
        return convertersMatrix[unit]!![Unit.MINUTES]!!.invoke(this)
    }

    fun toSeconds(): Time {
        return convertersMatrix[unit]!![Unit.SECONDS]!!.invoke(this)
    }

    fun toMillis(): Time {
        return convertersMatrix[unit]!![Unit.MILLI_SECONDS]!!.invoke(this)
    }

    fun toMicros(): Time {
        return convertersMatrix[unit]!![Unit.MICRO_SECONDS]!!.invoke(this)
    }

    fun toNanos(): Time {
        return convertersMatrix[unit]!![Unit.NANO_SECONDS]!!.invoke(this)
    }

    private fun millsToWeeks(millis: Long): Time {
        val seconds = millis / 1000L
        val minutes = seconds / 60L
        val hours = minutes / 60L
        val days = hours / 24L
        val weeks = days / 7f

        return Time(weeks.toLong(), Unit.DAYS).apply {
            converted = weeks
        }
    }

    private fun millsToDays(millis: Long): Time {
        val seconds = millis / 1000L
        val minutes = seconds / 60L
        val hours = minutes / 60L
        val days = hours / 24f

        return Time(days.toLong(), Unit.DAYS).apply {
            converted = days
        }
    }

    private fun millsToHours(millis: Long): Time {
        val seconds = millis / 1000L
        val minutes = seconds / 60L
        val hours = minutes / 60f

        return Time(hours.toLong(), Unit.DAYS).apply {
            converted = hours
        }
    }

    private fun millsToMinutes(millis: Long): Time {
        val seconds = millis / 1000L
        val minutes = seconds / 60f

        return Time(minutes.toLong(), Unit.DAYS).apply {
            converted = minutes
        }
    }

    private fun millsToSeconds(millis: Long): Time {
        val seconds = millis / 1000L

        return Time(seconds, Unit.DAYS).apply {
            converted = seconds.toFloat()
        }
    }

    enum class Unit(val type: Int, val shortName: String) {
        YEARS(1, "Y"),
        MONTHS(2, "M"),
        WEEKS(3, "M"),
        DAYS(4, "d"),
        HOURS(5, "h"),
        MINUTES(6, "m"),
        SECONDS(7, "s"),
        MILLI_SECONDS(8, "ms"),
        MICRO_SECONDS(9, "µs"),
        NANO_SECONDS(10, "ns"),
    }

    override fun toString(): String {
        return "[$value, $unit]"
    }
}

fun Millis.time(): Time {
    return Time(this.millis, Time.Unit.MILLI_SECONDS)
}

fun Micros.time(): Time {
    return Time(this.micros, Time.Unit.MICRO_SECONDS)
}

fun Nanos.time(): Time {
    return Time(this.nanos, Time.Unit.NANO_SECONDS)
}