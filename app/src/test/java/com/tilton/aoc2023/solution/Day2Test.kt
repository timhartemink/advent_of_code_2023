package com.tilton.aoc2023.solution

import com.tilton.aoc2023.TestInputLoader
import com.tilton.aoc2023.domain.solution.days.Day2
import org.junit.Test

class Day2Test {
    private val input = TestInputLoader.loadTestInput(2)

    @Test
    fun part1() {
        val result = Day2(input).part1()

        assert(result == 8)
    }

    @Test
    fun part2() {
        val result = Day2(input).part2()

        assert(result == 2286)
    }
}
