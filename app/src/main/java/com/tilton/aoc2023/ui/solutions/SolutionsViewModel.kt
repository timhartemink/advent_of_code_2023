package com.tilton.aoc2023.ui.solutions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tilton.aoc2023.domain.solution.SolutionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SolutionsViewModel @Inject constructor(private val solutionRepository: SolutionRepository) : ViewModel() {
    val uiState: StateFlow<UiState> = flow<UiState> {
        emit(UiState.Loaded(solutionRepository.getAnswers()))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState.Loading)
}
