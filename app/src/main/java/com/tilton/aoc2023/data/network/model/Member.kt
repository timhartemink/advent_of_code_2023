package com.tilton.aoc2023.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val id: Int,
    val name: String,
    val stars: Int,
    @SerialName("local_score")
    val score: Int
)
