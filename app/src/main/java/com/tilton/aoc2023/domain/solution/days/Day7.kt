package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.domain.solution.Solution

class Day7(override val input: List<String>) : Solution {
    override fun part1(): Long {
        val cardValues = listOf("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2").reversed()
        return getTotalWinnings(input, cardValues, false)
    }

    override fun part2(): Long {
        val cardValues = listOf("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2", "J").reversed()
        return getTotalWinnings(input, cardValues, true)
    }

    private fun getTotalWinnings(input: List<String>, cardValues: List<String>, useJoker: Boolean): Long {
        val cardsPerGame = input.map { game ->
            val splits = game.split(" ")
            val cards = splits[0]
            val bid = splits[1].toLong()
            cards.chunked(1).map { it } to bid
        }
        val typedHands = cardsPerGame.map {
            getType(it.first, useJoker) to it.first
        }.sortedByDescending {
            it.first
        }

        var index = 0
        val rankedHands = mutableListOf<List<String>>()
        while (index < typedHands.size) {
            val rankValue = typedHands[index].first
            val chunk = typedHands.filter { it.first == rankValue }
            val result = chunk.sortedWith(
                compareByDescending(
                    { cardValues.indexOf(it.second[0]) },
                    { cardValues.indexOf(it.second[1]) },
                    { cardValues.indexOf(it.second[2]) },
                    { cardValues.indexOf(it.second[3]) },
                    { cardValues.indexOf(it.second[4]) }
                )
            )
            rankedHands.addAll(result.map { it.second })
            index += chunk.size
        }

        return rankedHands.mapIndexed { i, hand ->
            val bid = cardsPerGame.find { it.first == hand }!!.second
            (rankedHands.size - i) * bid
        }.sum()
    }

    private fun getType(cards: List<String>, useJoker: Boolean = false): Type {
        return if (check(cards, 1, 5, useJoker)) {
            Type.FIVE_OF_A_KIND
        } else if (check(cards, 2, 4, useJoker)) {
            Type.FOUR_OF_A_KIND
        } else if (check(cards, 2, 3, useJoker)) {
            Type.FULL_HOUSE
        } else if (check(cards, 3, 3, useJoker)) {
            Type.THREE_OF_A_KIND
        } else if (check(cards, 3, 2, useJoker)) {
            Type.TWO_PAIR
        } else if (check(cards, 4, 2, useJoker)) {
            Type.ONE_PAIR
        } else {
            Type.HIGHEST_CARD
        }
    }

    private fun check(cards: List<String>, groups: Int, maxGroupValue: Int, useJoker: Boolean): Boolean {
        val map = cards.groupBy { it }.mapValues { it.value.size }.toMutableMap()
        if (useJoker) {
            val amountOfJs = map["J"] ?: 0
            if (amountOfJs < 5) {
                val highestEntryIndex = map.filter { it.key != "J" }.maxBy { it.value }
                map[highestEntryIndex.key] = highestEntryIndex.value + amountOfJs
                map.remove("J")
            }
        }
        return map.keys.count() == groups && map.entries.any { set -> set.value == maxGroupValue }
    }

    private fun <T> compareByDescending(vararg selectors: (T) -> Comparable<*>?): Comparator<T> {
        return Comparator { b, a -> compareValuesBy(a, b, *selectors) }
    }

    private enum class Type {
        HIGHEST_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND
    }
}
