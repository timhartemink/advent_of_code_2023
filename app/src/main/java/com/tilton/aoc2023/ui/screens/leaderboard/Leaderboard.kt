package com.tilton.aoc2023.ui.screens.leaderboard

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
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
                    item {
                        Text(
                            text = "Year: ${uiState.leaderboard.event}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    itemsIndexed(uiState.leaderboard.members) { index, member ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Color.Black),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = "${index + 1}")
                            Text(text = member.name)
                            Text(text = "${member.score}")
                        }
                    }
                }
            }
        }
    }
}
