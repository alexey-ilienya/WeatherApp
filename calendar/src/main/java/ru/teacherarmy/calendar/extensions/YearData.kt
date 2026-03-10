package ru.teacherarmy.calendar.extensions

import ru.teacherarmy.calendar.model.CalendarYear
import ru.teacherarmy.calendar.model.OutDateStyle
import java.time.DayOfWeek
import java.time.Month
import java.time.Year
import java.time.temporal.ChronoUnit

fun getCalendarYearData(
    startYear: Year,
    offset: Int,
    firstDayOfWeek: DayOfWeek,
    outDateStyle: OutDateStyle,
): CalendarYear {
    val year = startYear.plusYears(offset.toLong())
    val months =
        List(Month.entries.size) { index ->
            getCalendarMonthData(
                startMonth = year.atMonth(Month.JANUARY),
                offset = index,
                firstDayOfWeek = firstDayOfWeek,
                outDateStyle = outDateStyle,
            ).calendarMonth
        }
    return CalendarYear(year, months)
}

fun getYearIndex(
    startYear: Year,
    targetYear: Year,
): Int = ChronoUnit.YEARS.between(startYear, targetYear).toInt()

fun getYearIndicesCount(
    startYear: Year,
    endYear: Year,
): Int = getYearIndex(startYear, endYear) + 1
