package ru.teacherarmy.chart.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.teacherarmy.chart.model.INTERVAL
import ru.teacherarmy.chart.model.NUM_REPEATS
import ru.teacherarmy.chart.model.Pie

@Composable
fun pieChart(
    modifier: Modifier = Modifier,
    data: List<Pie>,
    selectedIndex: Int,
    onAnimationEnd: (() -> Unit)? = null,
) {
    val total = data.sumOf { it.data }
    val scope = rememberCoroutineScope()
    val scale: Animatable<Float, AnimationVector1D> = remember { Animatable(1.0f) }

    LaunchedEffect(selectedIndex) {
        if (selectedIndex >= 0 && selectedIndex < data.size) {
            scope.launch {
                scale.snapTo(1.0f)
                scale.animateTo(
                    targetValue = data[selectedIndex].selectedScale,
                    animationSpec =
                        repeatable(
                            NUM_REPEATS - 1,
                            animation = tween(INTERVAL),
                            repeatMode = RepeatMode.Reverse,
                        ),
                )
                scale.animateTo(
                    targetValue = 1.0f,
                    animationSpec = tween(INTERVAL),
                )
                onAnimationEnd?.invoke()
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Canvas(modifier = modifier) {
            val radius: Float = minOf(size.width, size.height) / 3
            var startAngle = 0.0f
            for (pieIndex in data.indices) {
                val degree = ((360 * data[pieIndex].data) / total).toFloat()
                val arcRadius =
                    radius *
                        if (pieIndex == selectedIndex) {
                            scale.value
                        } else {
                            1.0f
                        }

                drawArc(
                    color = data[pieIndex].color,
                    startAngle = startAngle,
                    sweepAngle = degree,
                    useCenter = true,
                    topLeft = Offset(center.x - arcRadius, center.y - arcRadius),
                    size = Size(arcRadius * 2, arcRadius * 2),
                )

                startAngle += degree
            }
        }
    }
}

@Preview
@Composable
fun pieChartPreview() {
    pieChart(
        modifier = Modifier.size(300.dp),
        data =
            listOf(
                Pie(
                    data = 2.5,
                    color = Color(0xFFa51347),
                    selectedScale = 1.3f,
                ),
                Pie(
                    data = 4.0,
                    color = Color(0xFF16bf37),
                    selectedScale = 1.4f,
                ),
                Pie(
                    data = 1.8,
                    color = Color(0xFF0615c3),
                    selectedScale = 1.2f,
                ),
                Pie(
                    data = 3.5,
                    color = Color(0xFFaab870),
                    selectedScale = 1.1f,
                ),
                Pie(
                    data = 2.0,
                    color = Color(0xFF28d6b2),
                    selectedScale = 1.5f,
                ),
            ),
        selectedIndex = 2,
    )
}
