package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.domain.solution.Solution
import com.tilton.aoc2023.util.getNumbers

class Day6(override val input: List<String>) : Solution {
    override fun part1(): Long {
        return getWinningStrategiesPerRace(input)
    }

    override fun part2(): Long {
        val sanitizedInput = input.map { it.replace(" ", "") }
        return getWinningStrategiesPerRace(sanitizedInput)
    }

    private fun getWinningStrategiesPerRace(input: List<String>): Long {
        val raceDurations = input[0].getNumbers()
        val raceRecords = input[1].getNumbers()

        val winningStrategiesPerRace = raceDurations.mapIndexed { index, duration ->
            val record = raceRecords[index]
            var firstWinningStrategy = 0L
            for (i in 0..duration) {
                val distance = i * (duration - i)
                if (distance > record) {
                    firstWinningStrategy = i
                    break
                }
            }
            var lastWinningStrategy = duration
            for (i in duration downTo 0) {
                val distance = i * (duration - i)
                if (distance > record) {
                    lastWinningStrategy = i
                    break
                }
            }
            (lastWinningStrategy - firstWinningStrategy) + 1
        }

        return winningStrategiesPerRace.reduce { acc, l -> acc * l }
    }
}
