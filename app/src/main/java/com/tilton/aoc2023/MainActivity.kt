package com.tilton.aoc2023

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tilton.aoc2023.solution.Day1
import com.tilton.aoc2023.theme.AOC2023Theme
import com.tilton.aoc2023.util.AssetReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AOC2023Theme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column(
                        Modifier.align(Alignment.TopStart)
                    ) {
                        for (i in 1..25) {
                            getAnswer(i)?.let { answers ->
                                Text(
                                    text = "Day $i",
                                    fontSize = 24.sp
                                )
                                Row(
                                    Modifier.wrapContentSize(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "The solution of part 1 is: ")
                                    Answer(answers.first)
                                }
                                Row(
                                    Modifier.wrapContentSize(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "The solution of part 2 is: ")
                                    Answer(answers.second)
                                }
                            } ?: Text("No solutions yet for day $i")

                            Spacer(modifier = Modifier.height(6.dp))
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color.Gray))
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                }
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
