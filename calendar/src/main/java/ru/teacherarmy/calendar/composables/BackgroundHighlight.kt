package ru.teacherarmy.calendar.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ru.teacherarmy.calendar.model.CalendarDay
import ru.teacherarmy.calendar.model.DateSelection
import ru.teacherarmy.calendar.model.DayPosition
import ru.teacherarmy.calendar.model.isInDateBetweenSelection
import ru.teacherarmy.calendar.model.isOutDateBetweenSelection
import java.time.LocalDate

private class HalfSizeShape(
    private val clipStart: Boolean,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val half = size.width / 2f
        val offset =
            if (layoutDirection == LayoutDirection.Ltr) {
                if (clipStart) Offset(half, 0f) else Offset.Zero
            } else {
                if (clipStart) Offset.Zero else Offset(half, 0f)
            }
        return Outline.Rectangle(Rect(offset, Size(half, size.height)))
    }
}

/**
 * Modern Airbnb highlight style, as seen in the app.
 * See also [backgroundHighlightLegacy].
 */
fun Modifier.backgroundHighlight(
    day: CalendarDay,
    today: LocalDate,
    selection: DateSelection,
    selectionColor: Color,
    continuousSelectionColor: Color,
    textColor: (Color) -> Unit,
    inactiveTextColorResourceId: Int,
    greyColorResourceId: Int,
): Modifier =
    composed {
        val (startDate, endDate) = selection
        val padding = 4.dp
        when (day.position) {
            DayPosition.MonthDate -> {
                when {
                    day.date.isBefore(today) -> {
                        textColor(colorResource(inactiveTextColorResourceId))
                        this
                    }
                    startDate == day.date && endDate == null -> {
                        textColor(Color.White)
                        padding(padding).background(
                            color = selectionColor,
                            shape = CircleShape,
                        )
                    }
                    day.date == startDate -> {
                        textColor(Color.White)
                        padding(vertical = padding)
                            .background(color = continuousSelectionColor, shape = HalfSizeShape(clipStart = true))
                            .padding(horizontal = padding)
                            .background(color = selectionColor, shape = CircleShape)
                    }
                    startDate != null && endDate != null && (day.date > startDate && day.date < endDate) -> {
                        textColor(colorResource(greyColorResourceId))
                        padding(vertical = padding)
                            .background(color = continuousSelectionColor)
                    }
                    day.date == endDate -> {
                        textColor(Color.White)
                        padding(vertical = padding)
                            .background(color = continuousSelectionColor, shape = HalfSizeShape(clipStart = false))
                            .padding(horizontal = padding)
                            .background(color = selectionColor, shape = CircleShape)
                    }
                    day.date == today -> {
                        textColor(colorResource(greyColorResourceId))
                        padding(padding)
                            .border(
                                width = 1.dp,
                                shape = CircleShape,
                                color = colorResource(inactiveTextColorResourceId),
                            )
                    }
                    else -> {
                        textColor(colorResource(greyColorResourceId))
                        this
                    }
                }
            }
            DayPosition.InDate -> {
                textColor(Color.Transparent)
                if (startDate != null &&
                    endDate != null &&
                    isInDateBetweenSelection(day.date, startDate, endDate)
                ) {
                    padding(vertical = padding)
                        .background(color = continuousSelectionColor)
                } else {
                    this
                }
            }
            DayPosition.OutDate -> {
                textColor(Color.Transparent)
                if (startDate != null &&
                    endDate != null &&
                    isOutDateBetweenSelection(day.date, startDate, endDate)
                ) {
                    padding(vertical = padding)
                        .background(color = continuousSelectionColor)
                } else {
                    this
                }
            }
        }
    }

/**
 * Old Airbnb highlight style.
 * See also [backgroundHighlight].
 */
fun Modifier.backgroundHighlightLegacy(
    day: CalendarDay,
    today: LocalDate,
    selection: DateSelection,
    selectionColor: Color,
    textColor: (Color) -> Unit,
    inactiveTextColorResourceId: Int,
    greyColorResourceId: Int,
): Modifier =
    composed {
        val (startDate, endDate) = selection
        val padding = 4.dp
        when (day.position) {
            DayPosition.MonthDate -> {
                when {
                    day.date.isBefore(today) -> {
                        textColor(colorResource(inactiveTextColorResourceId))
                        this
                    }
                    startDate == day.date && endDate == null -> {
                        textColor(Color.White)
                        padding(padding)
                            .background(color = selectionColor, shape = CircleShape)
                    }
                    day.date == startDate -> {
                        textColor(Color.White)
                        padding(vertical = padding)
                            .background(
                                color = selectionColor,
                                shape =
                                    RoundedCornerShape(
                                        topStartPercent = 50,
                                        bottomStartPercent = 50,
                                    ),
                            )
                    }
                    startDate != null && endDate != null && (day.date > startDate && day.date < endDate) -> {
                        textColor(Color.White)
                        padding(vertical = padding)
                            .background(color = selectionColor)
                    }
                    day.date == endDate -> {
                        textColor(Color.White)
                        padding(vertical = padding)
                            .background(
                                color = selectionColor,
                                shape = RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50),
                            )
                    }
                    day.date == today -> {
                        textColor(colorResource(greyColorResourceId))
                        padding(padding)
                            .border(
                                width = 1.dp,
                                shape = CircleShape,
                                color = colorResource(greyColorResourceId),
                            )
                    }
                    else -> {
                        textColor(colorResource(greyColorResourceId))
                        this
                    }
                }
            }
            DayPosition.InDate -> {
                textColor(Color.Transparent)
                if (startDate != null &&
                    endDate != null &&
                    isInDateBetweenSelection(day.date, startDate, endDate)
                ) {
                    padding(vertical = padding)
                        .background(color = selectionColor)
                } else {
                    this
                }
            }
            DayPosition.OutDate -> {
                textColor(Color.Transparent)
                if (startDate != null &&
                    endDate != null &&
                    isOutDateBetweenSelection(day.date, startDate, endDate)
                ) {
                    padding(vertical = padding)
                        .background(color = selectionColor)
                } else {
                    this
                }
            }
        }
    }
