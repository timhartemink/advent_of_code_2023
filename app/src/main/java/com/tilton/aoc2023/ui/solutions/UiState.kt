package com.tilton.aoc2023.ui.solutions

import com.tilton.aoc2023.domain.model.Answer

sealed interface UiState {
    data object Loading : UiState
    data class Loaded(val answers: List<Answer>) : UiState
}
