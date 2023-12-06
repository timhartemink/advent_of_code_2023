package com.tilton.aoc2023.ui.screens.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LeaderboardRoute(modifier: Modifier = Modifier, viewModel: LeaderboardViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Leaderboard(uiState.value, modifier)
}

@Composable
fun Leaderboard(uiState: UiState, modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(Color.White)) {
        when (uiState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Green
                    )
                }
            }

            is UiState.Error -> {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Error state",
                    tint = Color.Red
                )
            }

            is UiState.Loaded -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 16.dp),
                        text = "Year: ${uiState.leaderboard.event}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(modifier = Modifier.weight(0.2f), text = "Rank")
                        Text(modifier = Modifier.weight(0.4f), text = "Name")
                        Icon(
                            modifier = Modifier
                                .weight(0.2f)
                                .offset(x = (-12).dp),
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Star count",
                            tint = Color.Green
                        )
                        Text(modifier = Modifier.weight(0.2f), text = "Score")
                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        thickness = 2.dp,
                        color = Color.Black
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(uiState.leaderboard.members) { index, member ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(modifier = Modifier.weight(0.2f), text = "${index + 1}")
                                Text(modifier = Modifier.weight(0.4f), text = member.name)
                                Text(modifier = Modifier.weight(0.2f), text = "${member.stars}")
                                Text(modifier = Modifier.weight(0.2f), text = "${member.score}")
                            }
                        }
                    }
                }
            }
        }
    }
}
