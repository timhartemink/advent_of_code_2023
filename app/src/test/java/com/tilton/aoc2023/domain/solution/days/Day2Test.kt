package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.TestInputLoader
import org.junit.Test

class Day2Test {
    private val input = TestInputLoader.loadTestInput(2)

    @Test
    fun part1() {
        val result = Day2(input).part1()

        assert(result == 8L)
    }

    @Test
    fun part2() {
        val result = Day2(input).part2()

        assert(result == 2286L)
    }
}
