package com.tilton.aoc2023

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.tilton.aoc2023.screen.Solutions
import com.tilton.aoc2023.solution.Day1
import com.tilton.aoc2023.solution.Day2
import com.tilton.aoc2023.theme.AOC2023Theme
import com.tilton.aoc2023.util.AssetReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AOC2023Theme {
                Solutions(::getAnswer)
            }
        }
    }
    private fun getAnswer(day: Int): Pair<String, String>? {
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

            else -> null
        }
    }
}

@Composable
fun Answer(value: String, modifier: Modifier = Modifier) {
    Text(
        text = value,
        fontSize = 18.sp,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AOC2023Theme {
        Answer("42")
    }
}
