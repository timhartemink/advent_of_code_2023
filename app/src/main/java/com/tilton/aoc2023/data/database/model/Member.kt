package com.tilton.aoc2023.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Member(
    @PrimaryKey val id: Int,
    val name: String,
    val score: Int,
    val stars: Int,
)
