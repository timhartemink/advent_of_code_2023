package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.TestInputLoader
import org.junit.Test

class Day3Test {
    private val input = TestInputLoader.loadTestInput(3)

    @Test
    fun part1() {
        val result = Day3(input).part1()

        assert(result == 4361L)
    }

    @Test
    fun part2() {
        val result = Day3(input).part2()

        assert(result == 467835L)
    }
}
