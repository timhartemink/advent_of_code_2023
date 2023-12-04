package com.tilton.aoc2023.ui.screens.solutions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SolutionsRoute(
    modifier: Modifier = Modifier,
    viewModel: SolutionsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    Solutions(uiState.value, modifier)
}

@Composable
fun Solutions(
    uiState: UiState,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        when (uiState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Green
                    )
                }
            }

            is UiState.Loaded -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.answers) { answer ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = 8.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                val amountOfStars = remember {
                                    if (answer.part1 != null) {
                                        if (answer.part2 != null) {
                                            2
                                        } else {
                                            1
                                        }
                                    } else {
                                        0
                                    }
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Day ${answer.day}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                        for (i in 0 until amountOfStars) {
                                            Icon(
                                                imageVector = Icons.Filled.Star,
                                                contentDescription = "star",
                                                tint = Color.Green
                                            )
                                        }
                                    }
                                }
                                Divider(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    thickness = 2.dp,
                                    color = Color.DarkGray
                                )
                                Text(text = "Part 1: ${answer.part1 ?: "No solution yet"}")
                                Text(text = "Part 2: ${answer.part2 ?: "No solution yet"}")
                            }
                        }
                    }
                }
            }
        }
    }
}
