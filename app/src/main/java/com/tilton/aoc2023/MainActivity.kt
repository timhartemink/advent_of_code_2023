package com.tilton.aoc2023

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                    Answer(
                        1,
                        getAnswer(1),
                        Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
    private fun getAnswer(day: Int): String {
        return when (day) {
            1 -> Day1().invoke(AssetReader.getInputAsList(assets, 1))
            else -> "There is no answer for day $day"
        }
    }
}

@Composable
fun Answer(day: Int, value: String, modifier: Modifier = Modifier) {
    Text(
        text = "The answer to day $day is: $value",
        fontSize = 24.sp,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AOC2023Theme {
        Answer(1, "42")
    }
}
