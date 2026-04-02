package ru.teacherarmy.presentation.data

import androidx.compose.ui.graphics.Color
import ru.teacherarmy.chart.model.Pie

val testChartData =
    listOf(
        Pie(
            data = 2.5,
            selectedScale = 1.3f,
        ),
        Pie(
            data = 4.0,
            selectedScale = 1.4f,
        ),
        Pie(
            data = 1.8,
            selectedScale = 1.2f,
        ),
        Pie(
            data = 3.5,
            selectedScale = 1.1f,
        ),
        Pie(
            data = 2.0,
            selectedScale = 1.5f,
        ),
    ).apply {
        var hue = 360f / size
        var sat = SATURATION_START
        var lightness = LIGHTNESS_START
        forEach { pie ->
            pie.color = Color.hsl(hue = hue, saturation = sat, lightness = lightness)
            hue += 360f / size
            sat += SATURATION_STEP
            lightness += LIGHTNESS_STEP
        }
    }

const val SATURATION_START = 0.5f
const val SATURATION_STEP = 0.1f
const val LIGHTNESS_START = 0.8f
const val LIGHTNESS_STEP = -0.06f
