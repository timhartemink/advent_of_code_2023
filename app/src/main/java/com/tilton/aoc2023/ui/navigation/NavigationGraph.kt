package com.tilton.aoc2023.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tilton.aoc2023.ui.screens.leaderboard.LeaderboardRoute
import com.tilton.aoc2023.ui.screens.solutions.SolutionsRoute

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController, startDestination = BottomNavItem.Solutions.route) {
        composable(BottomNavItem.Solutions.route) {
            SolutionsRoute(modifier)
        }
        composable(BottomNavItem.Leaderboard.route) {
            LeaderboardRoute(modifier)
        }
    }
}
