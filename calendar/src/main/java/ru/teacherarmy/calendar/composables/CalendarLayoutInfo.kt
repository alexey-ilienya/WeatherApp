package ru.teacherarmy.calendar.composables

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import ru.teacherarmy.calendar.model.CalendarMonth

class CalendarLayoutInfo(info: LazyListLayoutInfo, private val month: (Int) -> CalendarMonth) :
    LazyListLayoutInfo by info {
    /**
     * The list of [CalendarItemInfo] representing all the currently visible months.
     */
    val visibleMonthsInfo: List<CalendarItemInfo>
        get() = visibleItemsInfo.map {
            CalendarItemInfo(it, month(it.index))
        }
}

/**
 * Contains useful information about an individual [CalendarMonth] on the calendar.
 *
 * @param month The month in the list.
 *
 * @see CalendarLayoutInfo
 * @see LazyListItemInfo
 */
class CalendarItemInfo(info: LazyListItemInfo, val month: CalendarMonth) : LazyListItemInfo by info