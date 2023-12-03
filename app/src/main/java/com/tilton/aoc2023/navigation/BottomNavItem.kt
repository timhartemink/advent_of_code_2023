package com.tilton.aoc2023.navigation

import com.tilton.aoc2023.R

sealed class BottomNavItem(val title: String, val icon: Int, val route: String) {
    data object Solutions : BottomNavItem("Solutions", R.drawable.nav_icon_solutions, "solutions")
    data object Leaderboard : BottomNavItem("Leaderboard", R.drawable.nav_icon_leaderboard, "leaderboard")
}
