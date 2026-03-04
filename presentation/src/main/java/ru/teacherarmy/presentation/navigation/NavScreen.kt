package ru.teacherarmy.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import ru.teacherarmy.presentation.R

sealed class NavScreen(val name : String, val route: String ){
    object  Home : NavScreen(name ="Home", route="home")
    object  Locations : NavScreen(name="Locations", route="location")
    object  Search : NavScreen(name = "Search", route = "search")
}

enum class BottomNavItem(
    val route: String,
    val labelResourceId: Int,
    val icon: ImageVector,
    val contentDescription: String
) {
    HORIZONTAL("horizontalCalendar", R.string.labelHorizontal, Icons.Default.CalendarMonth, "horizontal"),
    VERTICAL("verticalCalendar", R.string.labelVertical, Icons.AutoMirrored.Filled.EventNote, "vertical")
}
