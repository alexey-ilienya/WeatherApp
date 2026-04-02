package ru.teacherarmy.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.teacherarmy.calendar.composables.LocalScaffoldPaddingValues
import ru.teacherarmy.calendar.extensions.clickable
import ru.teacherarmy.chart.composables.PieChart
import ru.teacherarmy.presentation.R
import ru.teacherarmy.presentation.data.testChartData

@Composable
fun ChartPage(close: () -> Unit = {}) {
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
        PieChart(
            modifier =
                Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally),
            data = testChartData,
            selectedIndex = selection.intValue,
            onAnimationEnd = {
                selection.intValue++
            },
        )
    }
}
