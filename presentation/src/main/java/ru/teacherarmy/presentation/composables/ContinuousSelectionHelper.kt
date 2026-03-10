package ru.teacherarmy.presentation.composables

import ru.teacherarmy.calendar.model.DateSelection
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val rangeFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")

fun dateRangeDisplayText(
    startDate: LocalDate,
    endDate: LocalDate,
): String = "Selected: ${rangeFormatter.format(startDate)} - ${rangeFormatter.format(endDate)}"

object ContinuousSelectionHelper {
    fun getSelection(
        clickedDate: LocalDate,
        dateSelection: DateSelection,
    ): DateSelection {
        val (selectionStartDate, selectionEndDate) = dateSelection
        return if (selectionStartDate != null) {
            if (clickedDate < selectionStartDate || selectionEndDate != null) {
                DateSelection(startDate = clickedDate, endDate = null)
            } else if (clickedDate != selectionStartDate) {
                DateSelection(startDate = selectionStartDate, endDate = clickedDate)
            } else {
                DateSelection(startDate = clickedDate, endDate = null)
            }
        } else {
            DateSelection(startDate = clickedDate, endDate = null)
        }
    }
}
