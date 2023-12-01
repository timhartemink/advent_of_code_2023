package com.tilton.aoc2023.util

import android.content.res.AssetManager

object AssetReader {
    fun getInputAsList(assets: AssetManager, day: Int): List<String> {
        val input = assets.open("$AOC_INPUT_PREFIX$day$AOC_INPUT_FILE_TYPE")
        return input.bufferedReader().use { it.readLines() }
    }

    private const val AOC_INPUT_PREFIX = "aoc_2023_input_day_"
    private const val AOC_INPUT_FILE_TYPE = ".txt"
}
