package com.tilton.aoc2023.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Leaderboard(
    val year: String,
    val members: List<Member>
)
