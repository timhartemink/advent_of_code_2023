package com.tilton.aoc2023.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tilton.aoc2023.screen.Leaderboard
import com.tilton.aoc2023.screen.Solutions

@Composable
fun NavigationGraph(
    navController: NavHostController,
    getAnswer: (Int) -> Pair<Int, Int>?,
    modifier: Modifier = Modifier
) {
    NavHost(navController, startDestination = BottomNavItem.Solutions.route) {
        composable(BottomNavItem.Solutions.route) {
            Solutions(getAnswer, modifier)
        }
        composable(BottomNavItem.Leaderboard.route) {
            Leaderboard(modifier)
        }
    }
}
