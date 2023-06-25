package com.anonos

import org.junit.jupiter.api.Test
import java.time.*
import java.time.DayOfWeek.TUESDAY
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.test.assertEquals

class ParsingTest {
    // Fri Aug 17 2018 00:47:27 GMT-0600 (Mountain Daylight Time)
    @Test
    fun usDateTimeWithGenericTimezoneName() {
        val formatter = DateTimeFormatter.ofPattern("E MMM d yyyy HH:mm:ss 'GMT'X", Locale.US)
        val date = "Tue Aug 16 2016 00:47:27 GMT-0600 (Mountain Daylight Time)".subSequence(0, 33)
        val zonedDateTime = ZonedDateTime.parse(date, formatter)

        assertEquals(TUESDAY, zonedDateTime.dayOfWeek)
        assertEquals(Month.AUGUST, zonedDateTime.month)
        assertEquals(16, zonedDateTime.dayOfMonth)
        assertEquals(2016, zonedDateTime.year)
        assertEquals(0, zonedDateTime.hour)
        assertEquals(47, zonedDateTime.minute)
        assertEquals(27, zonedDateTime.second)
        assertEquals(ZoneOffset.ofHoursMinutes(-6, 0), zonedDateTime.offset)
    }

    @Test
    fun usLocalDateTime() {
        val formatter = DateTimeFormatter.ofPattern("E MMM d yyyy HH:mm:ss", Locale.US)

        val dateTime = "Tue Aug 16 2016 00:47:27"
        val localDateTime = LocalDateTime.parse(dateTime, formatter)

        assertEquals(dateTime, formatter.format(localDateTime))
    }

    @Test
    fun usLocalDate() {
        val formatter = DateTimeFormatter.ofPattern("E MMM d yyyy", Locale.US)

        val date = "Tue Aug 16 2016"
        val localDate = LocalDate.parse(date, formatter)

        assertEquals(date, formatter.format(localDate))
    }
}
