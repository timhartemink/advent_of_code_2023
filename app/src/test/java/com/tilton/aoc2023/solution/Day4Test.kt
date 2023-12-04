package com.tilton.aoc2023.solution

import com.tilton.aoc2023.TestInputLoader
import org.junit.Test

class Day4Test {
    @Test
    fun part1() {
        val input = TestInputLoader.loadTestInput(4)
        val result = Day4().part1(input)

        assert(result == 13)
    }

    @Test
    fun part2() {
        val input = TestInputLoader.loadTestInput(4)
        val result = Day4().part2(input)

        assert(result == 30)
    }
}
