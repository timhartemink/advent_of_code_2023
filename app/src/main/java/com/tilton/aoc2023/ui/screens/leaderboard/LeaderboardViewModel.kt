package com.tilton.aoc2023.ui.screens.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tilton.aoc2023.domain.leaderboard.LeaderboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(leaderboardRepository: LeaderboardRepository) : ViewModel() {
    val uiState = flow {
        emit(UiState.Loaded(leaderboardRepository.getLeaderboard(System.currentTimeMillis())))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState.Loading)
}
