package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.domain.solution.Solution

class Day1(override val input: List<String>) : Solution {
    override fun part1(): Long {
        val total = input.map { line ->
            val firstDigit = line[line.indexOfFirst { it.isDigit() }]
            val lastDigit = line[line.indexOfLast { it.isDigit() }]

            "$firstDigit$lastDigit".toInt()
        }

        return total.sum().toLong()
    }

    override fun part2(): Long {
        val total = input.map { line ->
            val firstDigit = getFirstDigitFromLine(line)
            val lastDigit = getLastDigitFromLine(line)

            "$firstDigit$lastDigit".toInt()
        }

        return total.sum().toLong()
    }

    private fun getFirstDigitFromLine(line: String): Int {
        val firstWrittenIndex = line.indexOfAny(WRITTEN_DIGITS)
        val firstNumberIndex = line.indexOfFirst { it.isDigit() }

        return if (firstNumberIndex in 0..< firstWrittenIndex || firstWrittenIndex < 0) {
            line[firstNumberIndex].digitToInt()
        } else {
            getWrittenDigitFromLine(line, firstWrittenIndex, line::indexOf)
        }
    }

    private fun getLastDigitFromLine(line: String): Int {
        val lastWrittenIndex = line.lastIndexOfAny(WRITTEN_DIGITS)
        val lastNumberIndex = line.indexOfLast { it.isDigit() }

        return if (lastNumberIndex > lastWrittenIndex) {
            line[lastNumberIndex].digitToInt()
        } else {
            getWrittenDigitFromLine(line, lastWrittenIndex, line::lastIndexOf)
        }
    }

    private fun getWrittenDigitFromLine(line: String, digitIndex: Int, indexOfFinder: (String) -> Int): Int {
        var digit = -1
        WRITTEN_DIGITS.forEachIndexed { entry, writtenDigit ->
            if (line.contains(writtenDigit)) {
                val index = indexOfFinder(writtenDigit)
                if (digitIndex == index) {
                    digit = entry
                    return@forEachIndexed
                }
            }
        }
        return digit
    }

    companion object {
        private val WRITTEN_DIGITS = listOf(
            "zero",
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine"
        )
    }
}
