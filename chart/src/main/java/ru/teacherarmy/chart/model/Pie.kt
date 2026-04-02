package ru.teacherarmy.chart.model

import androidx.compose.ui.graphics.Color

data class Pie(
    val data: Double,
    var color: Color = Color.Black,
    val selectedScale: Float = 1.25f,
)
