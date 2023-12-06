package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.TestInputLoader
import org.junit.Test

class Day5Test {
    private val input = TestInputLoader.loadTestInput(5)

    @Test
    fun part1() {
        val result = Day5(input).part1()

        assert(result == 35L)
    }

    @Test
    fun part2() {
        val result = Day5(input).part2()

        assert(result == 46L)
    }
}
