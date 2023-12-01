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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                        Modifier
                            .align(Alignment.TopStart)
                            .padding(24.dp)
                    ) {
                        var expanded by remember { mutableStateOf(false) }
                        var selectedDay: Int? by remember { mutableStateOf(null) }
                        val days = (1..24).toList()
                        Box {
                            Button(onClick = { expanded = !expanded }) {
                                Text("Select day")
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = null,
                                )
                            }
                            DropdownMenu(
                                modifier = Modifier.heightIn(max = 500.dp),
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                            ) {
                                days.forEach { label ->
                                    DropdownMenuItem(
                                        text = { Text(text = label.toString()) },
                                        onClick = {
                                            selectedDay = label
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        selectedDay?.let {
                            getAnswer(it)?.let { answers ->
                                Text(
                                    text = "Day $it",
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
                            } ?: Text("No solutions yet for day $selectedDay")
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
