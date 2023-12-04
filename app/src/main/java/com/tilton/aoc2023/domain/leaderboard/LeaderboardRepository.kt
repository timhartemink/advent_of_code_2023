package com.tilton.aoc2023.domain.leaderboard

import com.tilton.aoc2023.domain.model.Leaderboard
import com.tilton.aoc2023.domain.model.Member
import javax.inject.Inject
import com.tilton.aoc2023.data.database.Source as DatabaseSource
import com.tilton.aoc2023.data.network.Source as NetworkSource

class LeaderboardRepository @Inject constructor(
    private val networkSource: NetworkSource,
    private val databaseSource: DatabaseSource
) {
    suspend fun getLeaderboard(currentTimeInMillis: Long): Leaderboard {
        return Leaderboard("2023", listOf(Member(1, "Tim", 999999)))
    }
}
