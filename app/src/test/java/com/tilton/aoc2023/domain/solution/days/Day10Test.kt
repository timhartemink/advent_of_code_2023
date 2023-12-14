package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.TestInputLoader
import org.junit.Test

class Day10Test {
    private val input = TestInputLoader.loadTestInput(10)

    @Test
    fun part1() {
        val result = Day10(input).part1()

        assert(result == 80L)
    }

    @Test
    fun part2() {
        val result = Day10(input).part2()

        println("TESTY - Result: $result")
        assert(result == 10L)
    }
}
