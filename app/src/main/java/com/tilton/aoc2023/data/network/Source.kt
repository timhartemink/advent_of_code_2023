package com.tilton.aoc2023.data.network

import com.tilton.aoc2023.data.network.model.Leaderboard
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.cookie
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Source(
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher
) {

    @Inject
    constructor(httpClient: HttpClient) : this(httpClient, Dispatchers.IO)

    suspend fun getLeaderboard(): Leaderboard = withContext(dispatcher) {
        httpClient.get("https://adventofcode.com/2023/leaderboard/private/view/1026803.json") {
            cookie(
                "session",
                "53616c7465645f5f9913c62c2e6cd36260f5fe7ceca6438d29635e36ebdaa35429f704b0deb6a5371499b6d319d8fdee216dd30f619da81922076182b0983916"
            )
        }.call.response.body()
    }
}
