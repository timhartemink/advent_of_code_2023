package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.domain.solution.Solution
import com.tilton.aoc2023.util.getNumbers
import kotlin.math.pow

class Day4(override val input: List<String>) : Solution {
    override fun part1(): Int {
        return input.sumOf { line ->
            val winningNumbersLine = line.substring(line.indexOf(":") + 1, line.indexOf("|"))
            val cardNumbersLine = line.substring(line.indexOf("|") + 1)
            val winningNumbers = winningNumbersLine.getNumbers().toSet()
            val cardNumbers = cardNumbersLine.getNumbers().toSet()

            val amountOfWinningNumbersInCard = cardNumbers.size - (cardNumbers - winningNumbers).size
            (2.toDouble().pow(amountOfWinningNumbersInCard - 1)).toInt()
        }
    }

    override fun part2(): Int {
        val copiesOfCard = mutableMapOf<Int, Int>()
        input.forEach { line ->
            val id = "(\\d+)".toRegex().find(line)?.value?.toInt() ?: error("Could not find card ID")
            val winningNumbersLine = line.substring(line.indexOf(":") + 1, line.indexOf("|"))
            val cardNumbersLine = line.substring(line.indexOf("|") + 1)
            val winningNumbers = winningNumbersLine.getNumbers().toSet()
            val cardNumbers = cardNumbersLine.getNumbers().toSet()

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
