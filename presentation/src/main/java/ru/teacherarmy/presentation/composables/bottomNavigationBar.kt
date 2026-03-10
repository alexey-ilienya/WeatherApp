package ru.teacherarmy.presentation.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.teacherarmy.presentation.R
import ru.teacherarmy.presentation.navigation.BottomNavItem

@Composable
fun bottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        containerColor = colorResource(R.color.nav_container_color),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavItem.entries.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
                },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.contentDescription)
                },
                label = {
                    Text(text = stringResource(navItem.labelResourceId))
                },
                colors =
                    NavigationBarItemDefaults.colors(
                        selectedIconColor = colorResource(R.color.selected_icon_color), // Icon color when selected
                        unselectedIconColor = Color.White, // Icon color when not selected
                        selectedTextColor = Color.White, // Label color when selected
                        indicatorColor = colorResource(R.color.nav_container_color), // Highlight color for selected item
                    ),
            )
        }
    }
}
