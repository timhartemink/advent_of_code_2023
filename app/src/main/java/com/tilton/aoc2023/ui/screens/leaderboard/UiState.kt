package com.tilton.aoc2023.ui.screens.leaderboard

import com.tilton.aoc2023.domain.model.Leaderboard

sealed interface UiState {
    data object Loading : UiState
    data class Loaded(val leaderboard: Leaderboard) : UiState
}
