package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.TestInputLoader
import org.junit.Test

class Day1Test {
    @Test
    fun part1() {
        val input = listOf("1abc2", "pqr3stu8vwx", "a1b2c3d4e5f", "treb7uchet")
        val result = Day1(input).part1()

        assert(result == 142)
    }

    @Test
    fun part2() {
        val input = TestInputLoader.loadTestInput(1)
        val result = Day1(input).part2()

        assert(result == 281)
    }
}
