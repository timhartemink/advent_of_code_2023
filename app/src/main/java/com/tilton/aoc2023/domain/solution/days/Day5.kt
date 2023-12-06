package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.domain.solution.Solution
import com.tilton.aoc2023.util.getNumbers
import kotlin.math.max
import kotlin.math.min

class Day5(override val input: List<String>) : Solution {
    override fun part1(): Long {
        val seedList = mutableListOf<Seed>()
        val sanitizedInput = input.filter { it.isNotEmpty() }
        seedList.addAll(
            sanitizedInput
                .subList(0, findEndIndex(0, sanitizedInput))[0]
                .getNumbers()
                .map { Seed(it) }
        )
        var startIndex = 1
        while (startIndex < sanitizedInput.size - 1) {
            val endIndex = findEndIndex(startIndex, sanitizedInput)
            val chunk = sanitizedInput.subList(startIndex, endIndex)
            val maps = chunk.subList(1, chunk.size).map { line ->
                val numbers = line.getNumbers()
                AlmanacMap(numbers[1], numbers[1] + numbers[2], numbers[0] - numbers[1])
            }
            seedList.forEach { seed ->
                seed.value = getMappedValue(maps, seed.value)
            }
            startIndex += chunk.size
        }

        return seedList.minBy { it.value }.value
    }

    // Implemented the overlapping range intervals method to speed up the app loading
    // See commit[d8d8e30ff62d9bbdd406eeda74249e55a95b4087] for the original (sloooooooow) implementation
    override fun part2(): Long {
        val sanitizedInput = input.filter { it.isNotEmpty() }
        val seedRanges = sanitizedInput.subList(0, findEndIndex(0, sanitizedInput))[0]
            .getNumbers().chunked(2).map { it[0] to it[0] + it[1] }.toMutableList()
        val almanacMaps = mutableListOf<List<AlmanacMap>>()
        var startIndex = 1
        while (startIndex < sanitizedInput.size - 1) {
            val endIndex = findEndIndex(startIndex, sanitizedInput)
            val chunk = sanitizedInput.subList(startIndex, endIndex)

            almanacMaps.add(
                chunk.subList(1, chunk.size).map {
                    val numbers = it.getNumbers()
                    AlmanacMap(numbers[1], numbers[1] + numbers[2], numbers[0] - numbers[1])
                }
            )

            startIndex += chunk.size
        }

        val resultIds = mutableListOf<Long>()
        seedRanges.forEach { seedRange ->
            var ranges = mutableListOf(seedRange)
            almanacMaps.forEach { maps ->
                val modifiedRanges = mutableListOf<Pair<Long, Long>>()
                maps.forEach { map ->
                    val notModifiedRanges = mutableListOf<Pair<Long, Long>>()
                    while (ranges.isNotEmpty()) {
                        val range = ranges.last()
                        ranges.remove(range)

                        val before = range.first to min(range.second, map.startIncl)
                        val after = max(map.endExcl, range.first) to range.second
                        val inter = max(range.first, map.startIncl) to min(range.second, map.endExcl)

                        if (before.second > before.first)
                            notModifiedRanges.add(before)
                        if (after.second > after.first)
                            notModifiedRanges.add(after)
                        if (inter.second > inter.first)
                            modifiedRanges.add(inter.first + map.diff to inter.second + map.diff)
                    }
                    ranges = notModifiedRanges
                }
                ranges = (ranges + modifiedRanges).toMutableList()
            }
            resultIds.add(ranges.minOf { it.first })
        }

        return resultIds.min()
    }

    private fun findEndIndex(startIndex: Int, input: List<String>): Int {
        return input.indexOfFirst {
            it.contains(":") && input.indexOf(it) > startIndex
        }.let { if (it < 0) input.size else it }
    }

    private fun getMappedValue(
        maps: List<AlmanacMap>,
        value: Long
    ): Long {
        var mappedValue = -1L
        run mapping@{
            maps.forEach { map ->
                if (value >= map.startIncl && value < map.endExcl) {
                    mappedValue = value + map.diff
                    return@mapping
                } else {
                    mappedValue = value
                }
            }
        }

        return mappedValue
    }

    private data class AlmanacMap(val startIncl: Long, val endExcl: Long, val diff: Long)
    private data class Seed(var value: Long)
}
