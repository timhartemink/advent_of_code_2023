package com.tilton.aoc2023.solution

class Day1 {
    operator fun invoke(input: List<String>): String {
        val total = input.map { line ->
            val firstDigit = line[line.indexOfFirst { it.isDigit() }]
            val lastDigit = line[line.indexOfLast { it.isDigit() }]

            "$firstDigit$lastDigit".toInt()
        }

        return total.sum().toString()
    }
}
