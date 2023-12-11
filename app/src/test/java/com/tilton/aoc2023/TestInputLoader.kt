package com.tilton.aoc2023

object TestInputLoader {
    fun loadTestInput(day: Int, part: Int? = null): List<String> {
        val input =
            ClassLoader.getSystemResourceAsStream("$AOC_TEST_INPUT_PREFIX$day${part?.let { "_part_$it" } ?: ""}$AOC_TEST_INPUT_FILE_TYPE")
        return input.bufferedReader().use { it.readLines() }
    }

    private const val AOC_TEST_INPUT_PREFIX = "aoc_2023_test_input_day_"
    private const val AOC_TEST_INPUT_FILE_TYPE = ".txt"
}
