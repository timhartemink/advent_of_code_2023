package com.tilton.aoc2023.ui.screens.solutions

import com.tilton.aoc2023.domain.solution.model.Answer

sealed interface UiState {
    data object Loading : UiState
    data class Loaded(val answers: List<Answer>) : UiState
}
