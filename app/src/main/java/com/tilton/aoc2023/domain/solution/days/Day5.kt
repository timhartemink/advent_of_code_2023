package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.domain.solution.Solution
import com.tilton.aoc2023.util.getNumbers

class Day5(override val input: List<String>) : Solution {
    override fun part1(): Int {
        val seedList = mutableListOf<SeedAlmanac>()
        val sanitizedInput = input.filter { it.isNotEmpty() }
        seedList.addAll(
            sanitizedInput
                .subList(0, findEndIndex(0, sanitizedInput))[0]
                .getNumbers()
                .map { SeedAlmanac(it) }
        )
        var startIndex = 1
        while (startIndex < sanitizedInput.size - 1) {
            val endIndex = findEndIndex(startIndex, sanitizedInput)
            val chunk = sanitizedInput.subList(startIndex, endIndex)
            val firstLine = chunk[0]
            val colonIndex = firstLine.indexOf(":")

            when (AlmanacType.fromString(firstLine.substring(0, colonIndex))) {
                AlmanacType.SEED_TO_SOIL -> {
                    val maps = getMapsFromChunk(chunk)
                    seedList.forEach { seed ->
                        seed.soil = getAlmanacValue(maps, seed.id)
                    }
                }

                AlmanacType.SOIL_TO_FERTILIZER -> {
                    val maps = getMapsFromChunk(chunk)
                    seedList.forEach { seed ->
                        seed.fertilizer = getAlmanacValue(maps, seed.soil)
                    }
                }

                AlmanacType.FERTILIZER_TO_WATER -> {
                    val maps = getMapsFromChunk(chunk)
                    seedList.forEach { seed ->
                        seed.water = getAlmanacValue(maps, seed.fertilizer)
                    }
                }

                AlmanacType.WATER_TO_LIGHT -> {
                    val maps = getMapsFromChunk(chunk)
                    seedList.forEach { seed ->
                        seed.light = getAlmanacValue(maps, seed.water)
                    }
                }

                AlmanacType.LIGHT_TO_TEMPERATURE -> {
                    val maps = getMapsFromChunk(chunk)
                    seedList.forEach { seed ->
                        seed.temperature = getAlmanacValue(maps, seed.light)
                    }
                }

                AlmanacType.TEMPERATURE_TO_HUMIDITY -> {
                    val maps = getMapsFromChunk(chunk)
                    seedList.forEach { seed ->
                        seed.humidity = getAlmanacValue(maps, seed.temperature)
                    }
                }

                AlmanacType.HUMIDITY_TO_LOCATION -> {
                    val maps = getMapsFromChunk(chunk)
                    seedList.forEach { seed ->
                        seed.location = getAlmanacValue(maps, seed.humidity)
                    }
                }
            }
            startIndex += chunk.size
        }

        return seedList.minBy { it.location }.location.toInt()
    }

    private fun findEndIndex(startIndex: Int, input: List<String>): Int {
        return input.indexOfFirst {
            it.contains(":") && input.indexOf(it) > startIndex
        }.let { if (it < 0) input.size else it }
    }

    private fun getMapsFromChunk(
        chunk: List<String>
    ): List<AlmanacMap> {
        return chunk.subList(1, chunk.size).map { line ->
            val data = line.getNumbers()
            AlmanacMap(data[1], data[0], data[2])
        }
    }

    private fun getAlmanacValue(
        maps: List<AlmanacMap>,
        almanacId: Long
    ): Long {
        var value = -1L
        run mapping@{
            maps.forEach { map ->
                if (almanacId >= map.sourceId && almanacId <= map.sourceId + map.range) {
                    value = (map.destinationId - map.sourceId) + almanacId
                    return@mapping
                } else {
                    value = almanacId
                }
            }
        }

        return value
    }

    override fun part2(): Int? = null

    private data class AlmanacMap(val sourceId: Long, val destinationId: Long, val range: Long)

    private data class SeedAlmanac(
        val id: Long,
        var soil: Long = -1,
        var fertilizer: Long = -1,
        var water: Long = -1,
        var light: Long = -1,
        var temperature: Long = -1,
        var humidity: Long = -1,
        var location: Long = -1
    )

    enum class AlmanacType {
        SEED_TO_SOIL,
        SOIL_TO_FERTILIZER,
        FERTILIZER_TO_WATER,
        WATER_TO_LIGHT,
        LIGHT_TO_TEMPERATURE,
        TEMPERATURE_TO_HUMIDITY,
        HUMIDITY_TO_LOCATION;

        companion object {
            fun fromString(line: String): AlmanacType = when (line) {
                "seed-to-soil map" -> SEED_TO_SOIL
                "soil-to-fertilizer map" -> SOIL_TO_FERTILIZER
                "fertilizer-to-water map" -> FERTILIZER_TO_WATER
                "water-to-light map" -> WATER_TO_LIGHT
                "light-to-temperature map" -> LIGHT_TO_TEMPERATURE
                "temperature-to-humidity map" -> TEMPERATURE_TO_HUMIDITY
                "humidity-to-location map" -> HUMIDITY_TO_LOCATION
                else -> error("No AlmanacType maps to $line")
            }
        }
    }
}
