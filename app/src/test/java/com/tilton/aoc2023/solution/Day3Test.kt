package com.tilton.aoc2023.solution

import com.tilton.aoc2023.TestInputLoader
import org.junit.Test

class Day3Test {
    @Test
    fun part1() {
        val input = TestInputLoader.loadTestInput(3)
        val result = Day3().part1(input)

        assert(result == 4361)
    }
}
