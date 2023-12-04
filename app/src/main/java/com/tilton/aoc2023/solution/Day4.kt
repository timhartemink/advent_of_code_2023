package com.tilton.aoc2023.solution

import kotlin.math.pow

class Day4 {
    fun part1(input: List<String>): Int {
        val valuePerGame = mutableListOf<Int>()

        input.forEach { line ->
            val winningNumbersLine = line.substring(line.indexOf(":") + 1, line.indexOf("|"))
            val cardNumbersLine = line.substring(line.indexOf("|") + 1)
            val winningNumbers = getNumbers(winningNumbersLine).toSet()
            val cardNumbers = getNumbers(cardNumbersLine).toSet()

            val amountOfWinningNumbersInCard = cardNumbers.size - (cardNumbers - winningNumbers).size
            valuePerGame.add(
                (2.toDouble().pow(amountOfWinningNumbersInCard - 1)).toInt()
            )
        }

        return valuePerGame.sum()
    }

    private fun getNumbers(line: String): List<Int> {
        val numbers = mutableListOf<Int>()
        var cachedNumber = ""
        line.forEach { character ->
            if (character.isDigit()) {
                cachedNumber += character
            } else {
                if (storeCachedNumberIfNecessary(numbers, cachedNumber)) {
                    cachedNumber = ""
                }
            }
        }

        if (storeCachedNumberIfNecessary(numbers, cachedNumber)) {
            cachedNumber = ""
        }

        return numbers
    }

    private fun storeCachedNumberIfNecessary(
        numbers: MutableList<Int>,
        cachedNumber: String,
    ): Boolean {
        return if (cachedNumber.isNotEmpty()) {
            numbers.add(cachedNumber.toInt())
            true
        } else {
            false
        }
    }

    fun part2(input: List<String>): Int {
        return -1
    }
}
