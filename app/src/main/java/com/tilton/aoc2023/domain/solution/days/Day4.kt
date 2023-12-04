package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.domain.solution.Solution
import kotlin.math.pow

class Day4(override val input: List<String>) : Solution {
    override fun part1(): Int {
        return input.sumOf { line ->
            val winningNumbersLine = line.substring(line.indexOf(":") + 1, line.indexOf("|"))
            val cardNumbersLine = line.substring(line.indexOf("|") + 1)
            val winningNumbers = getNumbers(winningNumbersLine).toSet()
            val cardNumbers = getNumbers(cardNumbersLine).toSet()

            val amountOfWinningNumbersInCard = cardNumbers.size - (cardNumbers - winningNumbers).size
            (2.toDouble().pow(amountOfWinningNumbersInCard - 1)).toInt()
        }
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

    override fun part2(): Int {
        val copiesOfCard = mutableMapOf<Int, Int>()
        input.forEach { line ->
            val id = "(\\d+)".toRegex().find(line)?.value?.toInt() ?: error("Could not find card ID")
            val winningNumbersLine = line.substring(line.indexOf(":") + 1, line.indexOf("|"))
            val cardNumbersLine = line.substring(line.indexOf("|") + 1)
            val winningNumbers = getNumbers(winningNumbersLine).toSet()
            val cardNumbers = getNumbers(cardNumbersLine).toSet()

            copiesOfCard.putOrAddValue(id, 1)
            val amountOfCardsForId = copiesOfCard[id] ?: 1
            val amountOfWinningNumbersInCard = (cardNumbers.size - (cardNumbers - winningNumbers).size)
            for (i in id + 1..id + amountOfWinningNumbersInCard) {
                copiesOfCard.putOrAddValue(i, amountOfCardsForId)
            }
        }

        return copiesOfCard.map { it.value }.sum()
    }

    private fun MutableMap<Int, Int>.putOrAddValue(key: Int, amount: Int) {
        this[key]?.let {
            this[key] = it + amount
        } ?: this.put(key, amount)
    }
}
