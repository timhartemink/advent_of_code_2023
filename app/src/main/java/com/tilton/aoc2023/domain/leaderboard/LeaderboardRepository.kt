package com.tilton.aoc2023.domain.leaderboard

import com.tilton.aoc2023.domain.model.Leaderboard
import javax.inject.Inject
import com.tilton.aoc2023.data.database.Source as DatabaseSource
import com.tilton.aoc2023.data.database.model.Member as DatabaseMember
import com.tilton.aoc2023.data.network.Source as NetworkSource
import com.tilton.aoc2023.domain.model.Member as DomainMember

class LeaderboardRepository @Inject constructor(
    private val networkSource: NetworkSource,
    private val databaseSource: DatabaseSource
) {
    suspend fun getLeaderboard(currentTimeInMillis: Long) = runCatching {
        val lastApiFetch = databaseSource.getLastFetchTime()

        val (year, members) = if (lastApiFetch + FETCH_DELAY_IN_MILLIS < currentTimeInMillis) {
            val result = networkSource.getLeaderboard()
            databaseSource.storeLastFetchTime(currentTimeInMillis)
            databaseSource.putEvent(result.event)
            databaseSource.storeMembers(result.members.map { DatabaseMember(it.id, it.name, it.score, it.stars) })
            result.event to result.members.map { DomainMember(it.id, it.name, it.stars, it.score) }
        } else {
            val event = databaseSource.getEvent() ?: "Unknown"
            event to databaseSource.getMembers().map { DomainMember(it.id, it.name, it.stars, it.score) }
        }

        Leaderboard(year, members.sortedByDescending { it.score })
    }

    companion object {
        private const val FETCH_DELAY_IN_MILLIS = 60 * 15 * 1000L
    }
}
