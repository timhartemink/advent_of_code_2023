package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.TestInputLoader
import org.junit.Test

class Day8Test {
    @Test
    fun part1() {
        val input = TestInputLoader.loadTestInput(8, 1)
        val result = Day8(input).part1()

        assert(result == 6L)
    }

    @Test
    fun part2() {
        val input = TestInputLoader.loadTestInput(8, 2)
        val result = Day8(input).part2()

        println("TESTY - Result: $result")
        assert(result == 6L)
    }
}
