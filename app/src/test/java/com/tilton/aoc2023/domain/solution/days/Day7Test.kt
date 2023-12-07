package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.TestInputLoader
import org.junit.Test

class Day7Test {
    private val input = TestInputLoader.loadTestInput(7)

    @Test
    fun part1() {
        val result = Day7(input).part1()

        assert(result == 6440L)
    }

    @Test
    fun part2() {
        val result = Day7(input).part2()

        assert(result == 5905L)
    }
}
