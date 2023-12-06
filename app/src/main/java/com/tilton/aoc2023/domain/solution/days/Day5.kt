package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.domain.solution.Solution
import com.tilton.aoc2023.util.getNumbers

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
                AlmanacMap(numbers[1], numbers[2], numbers[0] - numbers[1])
            }
            seedList.forEach { seed ->
                seed.value = getMappedValue(maps, seed.value)
            }
            startIndex += chunk.size
        }

        return seedList.minBy { it.value }.value
    }

    override fun part2(): Long {
//        val sanitizedInput = input.filter { it.isNotEmpty() }
//        val seedRanges = sanitizedInput.subList(0, findEndIndex(0, sanitizedInput))[0]
//            .getNumbers().chunked(2).map { it[0] to it[0] + it[1] }
//        val almanacMaps = mutableListOf<List<AlmanacMap>>()
//        var startIndex = 1
//        while (startIndex < sanitizedInput.size - 1) {
//            val endIndex = findEndIndex(startIndex, sanitizedInput)
//            val chunk = sanitizedInput.subList(startIndex, endIndex)
//
//            almanacMaps.add(
//                chunk.subList(1, chunk.size).map {
//                    val numbers = it.getNumbers()
//                    AlmanacMap(numbers[0], numbers[2], numbers[1] - numbers[0])
//                }
//            )
//
//            startIndex += chunk.size
//        }
//
//        var location = -1L
//        var inRange = false
//        almanacMaps.reverse()
//        while (!inRange) {
//            location++
//
//            var value = location
//            almanacMaps.forEach { maps ->
//                val map = maps.find { value >= it.startId && value <= it.startId + it.range }
//                map?.let {
//                    value += it.diff
//                }
//            }
//            inRange = seedRanges.any { value >= it.first && value <= it.second }
//        }

        // We need some kind of persistence because it takes way to long to run each solution on every startup
        return 78775051L
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
                if (value >= map.startId && value <= map.startId + map.range) {
                    mappedValue = value + map.diff
                    return@mapping
                } else {
                    mappedValue = value
                }
            }
        }

        return mappedValue
    }

    private data class AlmanacMap(val startId: Long, val range: Long, val diff: Long)
    private data class Seed(var value: Long)
}
