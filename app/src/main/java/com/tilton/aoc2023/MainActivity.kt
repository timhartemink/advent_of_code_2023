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
import com.tilton.aoc2023.navigation.BottomNavigationBar
import com.tilton.aoc2023.navigation.NavigationGraph
import com.tilton.aoc2023.solution.Day1
import com.tilton.aoc2023.solution.Day2
import com.tilton.aoc2023.solution.Day3
import com.tilton.aoc2023.solution.Day4
import com.tilton.aoc2023.theme.AOC2023Theme
import com.tilton.aoc2023.util.AssetReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AOC2023Theme {
                MainScreenView(::getAnswer)
            }
        }
    }

    private fun getAnswer(day: Int): Pair<Int, Int>? {
        return when (day) {
            1 -> {
                val day1 = Day1()
                val input = AssetReader.getInputAsList(assets, day)
                day1.part1(input) to day1.part2(input)
            }

            2 -> {
                val day2 = Day2()
                val input = AssetReader.getInputAsList(assets, day)
                day2.part1(input) to day2.part2(input)
            }

            3 -> {
                val day3 = Day3()
                val input = AssetReader.getInputAsList(assets, day)
                day3.part1(input) to day3.part2(input)
            }

            4 -> {
                val day4 = Day4()
                val input = AssetReader.getInputAsList(assets, day)
                day4.part1(input) to day4.part2(input)
            }

            else -> null
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreenView(getAnswer: (Int) -> Pair<Int, Int>?) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        NavigationGraph(
            modifier = Modifier.consumeWindowInsets(it),
            getAnswer = getAnswer,
            navController = navController
        )
    }
}
