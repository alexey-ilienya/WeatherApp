package ru.teacherarmy.calendar.model

import ru.teacherarmy.calendar.extensions.atStartOfMonth
import ru.teacherarmy.calendar.extensions.nextMonth
import ru.teacherarmy.calendar.extensions.previousMonth
import ru.teacherarmy.calendar.extensions.yearMonth
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.LazyThreadSafetyMode.NONE

data class DateSelection(val startDate: LocalDate? = null, val endDate: LocalDate? = null) {
    val daysBetween by lazy(NONE) {
        if (startDate == null || endDate == null) {
            null
        } else {
            ChronoUnit.DAYS.between(startDate, endDate)
        }
    }
}

fun isInDateBetweenSelection(
    inDate: LocalDate,
    startDate: LocalDate,
    endDate: LocalDate,
): Boolean {
    if (startDate.yearMonth == endDate.yearMonth) return false
    if (inDate.yearMonth == startDate.yearMonth) return true
    val firstDateInThisMonth = inDate.yearMonth.nextMonth.atStartOfMonth()
    return firstDateInThisMonth in startDate..endDate && startDate != firstDateInThisMonth
}

fun isOutDateBetweenSelection(
    outDate: LocalDate,
    startDate: LocalDate,
    endDate: LocalDate,
): Boolean {
    if (startDate.yearMonth == endDate.yearMonth) return false
    if (outDate.yearMonth == endDate.yearMonth) return true
    val lastDateInThisMonth = outDate.yearMonth.previousMonth.atEndOfMonth()
    return lastDateInThisMonth in startDate..endDate && endDate != lastDateInThisMonth
}
