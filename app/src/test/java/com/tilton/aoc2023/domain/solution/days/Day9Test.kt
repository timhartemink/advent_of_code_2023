package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.TestInputLoader
import org.junit.Test

class Day9Test {
    val input = TestInputLoader.loadTestInput(9)

    @Test
    fun part1() {
        val result = Day9(input).part1()

        assert(result == 114L)
    }

    @Test
    fun part2() {
        val result = Day9(input).part2()

        assert(result == 2L)
    }
}
