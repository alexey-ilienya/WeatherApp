package ru.teacherarmy.calendar.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import ru.teacherarmy.calendar.composables.CalendarLayoutInfo
import ru.teacherarmy.calendar.composables.CalendarState
import ru.teacherarmy.calendar.model.CalendarDay
import ru.teacherarmy.calendar.model.CalendarMonth
import ru.teacherarmy.calendar.model.DayPosition
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

@JvmOverloads
public fun daysOfWeek(firstDayOfWeek: DayOfWeek = firstDayOfWeekFromLocale()): List<DayOfWeek> {
    val pivot = 7 - firstDayOfWeek.ordinal
    val daysOfWeek = DayOfWeek.entries
    // Order `daysOfWeek` array so that firstDayOfWeek is at the start position.
    return daysOfWeek.takeLast(pivot) + daysOfWeek.dropLast(pivot)
}

/**
 * Returns the first day of the week from the provided locale.
 */
@JvmOverloads
fun firstDayOfWeekFromLocale(locale: Locale = Locale.getDefault()): DayOfWeek = WeekFields.of(locale).firstDayOfWeek

/**
 * Returns a [LocalDate] at the start of the month.
 *
 * Complements [YearMonth.atEndOfMonth].
 */
fun YearMonth.atStartOfMonth(): LocalDate = this.atDay(1)

val LocalDate.yearMonth: YearMonth
    get() = YearMonth.of(year, month)

val YearMonth.nextMonth: YearMonth
    get() = this.plusMonths(1)

val YearMonth.previousMonth: YearMonth
    get() = this.minusMonths(1)

// E.g DayOfWeek.SATURDAY.daysUntil(DayOfWeek.TUESDAY) = 3
public fun DayOfWeek.daysUntil(other: DayOfWeek): Int = (7 + (other.ordinal - ordinal)) % 7

// Find the actual month on the calendar where this date is shown.
public val CalendarDay.positionYearMonth: YearMonth
    get() =
        when (position) {
            DayPosition.InDate -> date.yearMonth.nextMonth
            DayPosition.MonthDate -> date.yearMonth
            DayPosition.OutDate -> date.yearMonth.previousMonth
        }

public inline fun <T> Iterable<T>.indexOfFirstOrNull(predicate: (T) -> Boolean): Int? {
    val result = indexOfFirst(predicate)
    return if (result == -1) null else result
}

public fun <T : Comparable<T>> checkRange(
    start: T,
    end: T,
) {
    check(end >= start) {
        "start: $start is greater than end: $end"
    }
}

@Composable
operator fun PaddingValues.plus(other: PaddingValues): PaddingValues {
    val ld = LocalLayoutDirection.current
    return PaddingValues(
        start = calculateStartPadding(ld) + other.calculateStartPadding(ld),
        top = calculateTopPadding() + other.calculateTopPadding(),
        end = calculateEndPadding(ld) + other.calculateEndPadding(ld),
        bottom = calculateBottomPadding() + other.calculateBottomPadding(),
    )
}

fun Modifier.clickable(
    enabled: Boolean = true,
    showRipple: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier =
    composed {
        clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = if (showRipple) LocalIndication.current else null,
            enabled = enabled,
            onClickLabel = onClickLabel,
            role = role,
            onClick = onClick,
        )
    }

@Composable
fun statusBarColorUpdateEffect(color: Color) {
    if (LocalInspectionMode.current) return // findActivity() will not work in preview.
    val activity = LocalContext.current.findActivity()
    val lifecycleOwner = LocalLifecycleOwner.current
    val observer =
        remember {
            StatusBarColorLifecycleObserver(activity, color.toArgb())
        }
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(observer)
    }
}

@Composable
fun navigationIcon(onBackClick: () -> Unit) {
    IconButton(onClick = onBackClick) {
        Icon(
            tint = Color.White,
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
        )
    }
}

/**
 * Alternative way to find the first fully visible month in the layout.
 *
 * @see [rememberFirstVisibleMonthAfterScroll]
 * @see [rememberFirstMostVisibleMonth]
 */
@Composable
fun rememberFirstCompletelyVisibleMonth(state: CalendarState): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    // Only take non-null values as null will be produced when the
    // list is mid-scroll as no index will be completely visible.
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.completelyVisibleMonths.firstOrNull() }
            .filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }
    return visibleMonth.value
}

/**
 * Returns the first visible month in a paged calendar **after** scrolling stops.
 *
 * @see [rememberFirstCompletelyVisibleMonth]
 * @see [rememberFirstMostVisibleMonth]
 */
@Composable
fun rememberFirstVisibleMonthAfterScroll(state: CalendarState): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.isScrollInProgress }
            .filter { scrolling -> !scrolling }
            .collect { visibleMonth.value = state.firstVisibleMonth }
    }
    return visibleMonth.value
}

/**
 * Find the first month on the calendar visible up to the given [viewportPercent] size.
 *
 * @see [rememberFirstCompletelyVisibleMonth]
 * @see [rememberFirstVisibleMonthAfterScroll]
 */
@Composable
fun rememberFirstMostVisibleMonth(
    state: CalendarState,
    viewportPercent: Float = 50f,
): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.firstMostVisibleMonth(viewportPercent) }
            .filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }
    return visibleMonth.value
}

private val CalendarLayoutInfo.completelyVisibleMonths: List<CalendarMonth>
    get() {
        val visibleItemsInfo = this.visibleMonthsInfo.toMutableList()
        return if (visibleItemsInfo.isEmpty()) {
            emptyList()
        } else {
            val lastItem = visibleItemsInfo.last()
            val viewportSize = this.viewportEndOffset + this.viewportStartOffset
            if (lastItem.offset + lastItem.size > viewportSize) {
                visibleItemsInfo.removeAt(visibleItemsInfo.lastIndex)
            }
            val firstItem = visibleItemsInfo.firstOrNull()
            if (firstItem != null && firstItem.offset < this.viewportStartOffset) {
                visibleItemsInfo.removeAt(0)
            }
            visibleItemsInfo.map { it.month }
        }
    }

private fun CalendarLayoutInfo.firstMostVisibleMonth(viewportPercent: Float = 50f): CalendarMonth? =
    if (visibleMonthsInfo.isEmpty()) {
        null
    } else {
        val viewportSize = (viewportEndOffset + viewportStartOffset) * viewportPercent / 100f
        visibleMonthsInfo
            .firstOrNull { itemInfo ->
                if (itemInfo.offset < 0) {
                    itemInfo.offset + itemInfo.size >= viewportSize
                } else {
                    itemInfo.size - itemInfo.offset >= viewportSize
                }
            }?.month
    }

suspend fun LazyListState.animateScrollAndCenterItem(index: Int) {
    suspend fun animateScrollIfVisible(): Boolean {
        val layoutInfo = layoutInfo
        val containerSize = layoutInfo.viewportSize.width - layoutInfo.beforeContentPadding - layoutInfo.afterContentPadding
        val target = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index } ?: return false
        val targetOffset = containerSize / 2f - target.size / 2f
        animateScrollBy(target.offset - targetOffset)
        return true
    }
    if (!animateScrollIfVisible()) {
        val visibleItemsInfo = layoutInfo.visibleItemsInfo
        val currentIndex = visibleItemsInfo.getOrNull(visibleItemsInfo.size / 2)?.index ?: -1
        scrollToItem(
            if (index > currentIndex) {
                (index - visibleItemsInfo.size + 1)
            } else {
                index
            }.coerceIn(0, layoutInfo.totalItemsCount),
        )
        animateScrollIfVisible()
    }
}

fun YearMonth.displayText(short: Boolean = false): String = "${this.month.displayText(short = short)} ${this.year}"

fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale.ENGLISH)
}

fun DayOfWeek.displayText(
    uppercase: Boolean = false,
    narrow: Boolean = false,
): String {
    val style = if (narrow) TextStyle.NARROW else TextStyle.SHORT
    return getDisplayName(style, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}
