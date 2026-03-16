package ru.teacherarmy.homework1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.teacherarmy.homework1.ui.theme.weatherAppTheme
import ru.teacherarmy.presentation.composables.bottomNavigationBar
import ru.teacherarmy.presentation.navigation.NavScreen
import ru.teacherarmy.presentation.navigation.appNavigation

@AndroidEntryPoint
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            weatherAppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Scaffold(
                        bottomBar = {
                            if (navBackStackEntry?.destination?.route != NavScreen.Splash.route) {
                                bottomNavigationBar(navController = navController)
                            }
                        },
                        content = { _ ->
                            appNavigation(navController = navController)
                        },
                    )
                }
            }
        }
    }
}
