package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.TestInputLoader
import org.junit.Test

class Day4Test {
    private val input = TestInputLoader.loadTestInput(4)

    @Test
    fun part1() {
        val result = Day4(input).part1()

        assert(result == 13)
    }

    @Test
    fun part2() {
        val result = Day4(input).part2()

        assert(result == 30)
    }
}
