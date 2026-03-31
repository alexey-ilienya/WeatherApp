package ru.teacherarmy.chart.model

import androidx.compose.ui.graphics.Color

data class Pie(
    val data: Double,
    val color: Color,
    val selectedScale: Float = 1.25f,
)
