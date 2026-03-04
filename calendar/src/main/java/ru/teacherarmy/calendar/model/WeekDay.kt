package ru.teacherarmy.calendar.model

import androidx.compose.runtime.Immutable
import java.io.Serializable
import java.time.LocalDate

@Immutable
data class WeekDay(val date: LocalDate, val position: WeekDayPosition) : Serializable
