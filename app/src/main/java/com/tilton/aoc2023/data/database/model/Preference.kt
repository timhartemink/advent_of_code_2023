package com.tilton.aoc2023.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Preference(
    @PrimaryKey val key: String,
    val value: String
)
