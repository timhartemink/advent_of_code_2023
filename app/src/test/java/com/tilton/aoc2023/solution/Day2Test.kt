package com.tilton.aoc2023.solution

import com.tilton.aoc2023.TestInputLoader
import org.junit.Test

class Day2Test {
    @Test
    fun part1() {
        val input = TestInputLoader.loadTestInput(2)
        val result = Day2().part1(input).toInt()
        assert(result == 8)
    }

    @Test
    fun part2() {
        val input = TestInputLoader.loadTestInput(2)
        val result = Day2().part2(input).toInt()
        assert(result == 2286)
    }
}
