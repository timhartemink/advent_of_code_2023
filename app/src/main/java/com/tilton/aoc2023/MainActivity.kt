package com.tilton.aoc2023

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.tilton.aoc2023.ui.navigation.BottomNavigationBar
import com.tilton.aoc2023.ui.navigation.NavigationGraph
import com.tilton.aoc2023.ui.theme.AOC2023Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AOC2023Theme {
                MainScreenView()
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        NavigationGraph(
            modifier = Modifier.consumeWindowInsets(it),
            navController = navController
        )
    }
}
