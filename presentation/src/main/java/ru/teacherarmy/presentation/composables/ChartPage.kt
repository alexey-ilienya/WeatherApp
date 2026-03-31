package ru.teacherarmy.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.teacherarmy.calendar.composables.LocalScaffoldPaddingValues
import ru.teacherarmy.calendar.extensions.clickable
import ru.teacherarmy.chart.composables.pieChart
import ru.teacherarmy.chart.model.Pie
import ru.teacherarmy.presentation.R

@Composable
fun chartPage(close: () -> Unit = {}) {
    val selection = remember { mutableIntStateOf(0) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(LocalScaffoldPaddingValues.current),
    ) {
        Icon(
            modifier =
                Modifier
                    .height(48.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .clickable(
                        enabled = true,
                        showRipple = true,
                        onClick = close,
                    ).padding(12.dp),
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = "Close",
        )
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
            selectedIndex = selection.intValue,
            onAnimationEnd = {
                selection.intValue++
            },
        )
    }
}
