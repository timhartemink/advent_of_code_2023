package com.tilton.aoc2023.ui.screens.leaderboard

import com.tilton.aoc2023.domain.leaderboard.model.Leaderboard

sealed interface UiState {
    data object Loading : UiState
    data object Error : UiState
    data class Loaded(val leaderboard: Leaderboard) : UiState
}
