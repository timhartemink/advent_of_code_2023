package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.domain.solution.Solution
import com.tilton.aoc2023.util.getNumbers

class Day9(override val input: List<String>) : Solution {
    override fun part1(): Long {
        return input
            .map { it.getNumbers() }
            .sumOf { sequence ->
                val diffList = mutableListOf<List<Long>>()
                diffList.add(sequence)
                while (!diffList.last().all { it == 0L }) {
                    val diffs = getDiffsForSequence(diffList.last())
                    diffList.add(diffs)
                }
                val value = calculateExtrapolatedValues(diffList)
                value
            }
    }

    override fun part2(): Long {
        return input
            .map { it.getNumbers() }
            .sumOf { sequence ->
                val diffList = mutableListOf<List<Long>>()
                diffList.add(sequence)
                while (!diffList.last().all { it == 0L }) {
                    val diffs = getDiffsForSequence(diffList.last())
                    diffList.add(diffs)
                }
                val value = calculateExtrapolatedValuesBackwards(diffList)
                value
            }
    }

    private fun getDiffsForSequence(sequence: List<Long>): List<Long> {
        val diffs = mutableListOf<Long>()
        for (i in 1 until sequence.size) {
            val left = sequence[i - 1]
            val right = sequence[i]
            diffs.add(right - left)
        }
        return diffs
    }

    private fun calculateExtrapolatedValues(diffList: List<List<Long>>): Long {
        var extrapolatedValue = 0L
        for (i in diffList.size - 1 downTo 0) {
            if (diffList[i].isNotEmpty()) {
                val lastValue = diffList[i].last()
                extrapolatedValue += lastValue
            }
        }
        return extrapolatedValue
    }

    private fun calculateExtrapolatedValuesBackwards(diffList: List<List<Long>>): Long {
        var extrapolatedValue = 0L
        for (i in diffList.size - 1 downTo 0) {
            if (diffList[i].isNotEmpty()) {
                val firstValue = diffList[i].first()
                extrapolatedValue = firstValue - extrapolatedValue
            }
        }
        return extrapolatedValue
    }
}
