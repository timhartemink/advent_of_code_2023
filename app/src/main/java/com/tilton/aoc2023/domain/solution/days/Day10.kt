package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.domain.solution.Solution
import kotlin.math.min

class Day10(override val input: List<String>) : Solution {
    override fun part1(): Long {
        val map = Array(input.size) { Array(input[0].length) { 'x' } }
        var startPosition = -1 to -1

        input.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { characterIndex, character ->
                if (character == 'S') {
                    startPosition = lineIndex to characterIndex
                }
                map[lineIndex][characterIndex] = character
            }
        }
        val startDirections = getStartingDirections(map, startPosition)
        val result = traceStepsOfLoop(map, startPosition, startDirections)

        return result.first / 2L
    }

    override fun part2(): Long {
        val map = Array(input.size) { Array(input[0].length) { 'x' } }
        var startPosition = -1 to -1

        input.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { characterIndex, character ->
                if (character == 'S') {
                    startPosition = lineIndex to characterIndex
                }
                map[lineIndex][characterIndex] = character
            }
        }
        val startDirections = getStartingDirections(map, startPosition)
        val result = traceStepsOfLoop(map, startPosition, startDirections)

        result.second.forEach {
            println(it.joinToString(""))
        }
        return result.second.sumOf { row -> row.filter { it == 'I' }.size }.toLong()
    }

    private fun getStartingDirections(
        map: Array<Array<Char>>,
        startPosition: Pair<Int, Int>
    ): List<Pair<Direction, Pair<Int, Int>>> {
        val startPositions = mutableListOf<Pair<Direction, Pair<Int, Int>>>()
        val lineIndex = startPosition.first
        val characterIndex = startPosition.second

        if (lineIndex - 1 >= 0) {
            if (map[lineIndex - 1][characterIndex] == '|') {
                startPositions.add(Direction.SOUTH_NORTH to (lineIndex - 1 to characterIndex))
            } else if (map[lineIndex - 1][characterIndex] == '7') {
                startPositions.add(Direction.SOUTH_WEST to (lineIndex - 1 to characterIndex))
            } else if (map[lineIndex - 1][characterIndex] == 'F') {
                startPositions.add(Direction.SOUTH_EAST to (lineIndex - 1 to characterIndex))
            }
        }

        if (characterIndex + 1 < map.size) {
            if (map[lineIndex][characterIndex + 1] == '-') {
                startPositions.add(Direction.WEST_EAST to (lineIndex to characterIndex + 1))
            } else if (map[lineIndex][characterIndex + 1] == 'J') {
                startPositions.add(Direction.WEST_NORTH to (lineIndex to characterIndex + 1))
            } else if (map[lineIndex][characterIndex + 1] == '7') {
                startPositions.add(Direction.WEST_SOUTH to (lineIndex to characterIndex + 1))
            }
        }

        if (lineIndex + 1 < map.size) {
            if (map[lineIndex + 1][characterIndex] == '|') {
                startPositions.add(Direction.NORTH_SOUTH to (lineIndex + 1 to characterIndex))
            } else if (map[lineIndex + 1][characterIndex] == 'L') {
                startPositions.add(Direction.NORTH_EAST to (lineIndex + 1 to characterIndex))
            } else if (map[lineIndex + 1][characterIndex] == 'J') {
                startPositions.add(Direction.NORTH_WEST to (lineIndex + 1 to characterIndex))
            }
        }

        if (characterIndex - 1 >= 0) {
            if (map[lineIndex][characterIndex - 1] == '-') {
                startPositions.add(Direction.EAST_WEST to (lineIndex to characterIndex - 1))
            } else if (map[lineIndex][characterIndex - 1] == 'L') {
                startPositions.add(Direction.EAST_NORTH to (lineIndex to characterIndex - 1))
            } else if (map[lineIndex][characterIndex - 1] == 'F') {
                startPositions.add(Direction.EAST_SOUTH to (lineIndex to characterIndex - 1))
            }
        }

        return startPositions
    }

    private fun traceStepsOfLoop(
        map: Array<Array<Char>>,
        startPosition: Pair<Int, Int>,
        startDirections: List<Pair<Direction, Pair<Int, Int>>>
    ): Pair<Int, List<List<Char>>> {
        var steps = 1
        val loop = Array(map.size) { Array(map[0].size) { Direction.INVALID to '0' } }
        loop[startPosition.first][startPosition.second] = Direction.INVALID to 'S'

        for (i in 0..startDirections.size) {
            steps = 1
            var isOnStart = false
            var position = startDirections[i]

            do {
                loop[position.second.first][position.second.second] =
                    position.first to getCharForDirection(position.first)
                steps++
                position = getNextPosition(map, position)

                if (position.second == startPosition) {
                    isOnStart = true
                    break
                }
            } while (position.first != Direction.INVALID)

            // If we are back on start we traced a loop
            if (isOnStart) {
                break
            }
        }

        return steps to markInsideArea(loop)
    }

    private fun markInsideArea(loop: Array<Array<Pair<Direction, Char>>>): List<List<Char>> {
        loop.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, column ->
                getInsidePosition(column.first to (rowIndex to columnIndex))?.let {
                    try {
                        if (loop[it.first][it.second].second == '0') {
                            loop[it.first][it.second] = Direction.INVALID to 'I'
                        }
                    } catch (ignored: Exception) {
                    }
                }
            }
        }

        for (i in loop.indices) {
            for (j in loop[i].indices) {
                val entry = loop[i][j]
                if (entry.second == 'I') {
                    val endIndex = loop[i]
                        .sliceArray(min((j + 1), loop[i].size - 1)..<loop[i].size)
                        .indexOfFirst { it.second != '0' }
                    var index = j
                    while (index < endIndex + j) {
                        index++
                        loop[i][index] = loop[i][index].first to 'I'
                    }
                }
            }
        }

        return loop.map { row -> row.map { it.second } }
    }

    private fun getInsidePosition(position: Pair<Direction, Pair<Int, Int>>): Pair<Int, Int>? {
        return when (position.first) {
            Direction.NORTH_SOUTH, Direction.EAST_SOUTH ->
                position.second.first to position.second.second - 1

            Direction.SOUTH_NORTH, Direction.SOUTH_WEST, Direction.WEST_NORTH ->
                position.second.first to position.second.second + 1

            Direction.EAST_WEST -> position.second.first - 1 to position.second.second
            Direction.WEST_EAST -> position.second.first + 1 to position.second.second
            else -> null
        }
    }

    private fun getCharForDirection(direction: Direction): Char {
        return when (direction) {
            Direction.NORTH_SOUTH, Direction.SOUTH_NORTH -> '║'
            Direction.EAST_WEST, Direction.WEST_EAST -> '═'
            Direction.NORTH_EAST, Direction.EAST_NORTH -> '╚'
            Direction.NORTH_WEST, Direction.WEST_NORTH -> '╝'
            Direction.SOUTH_WEST, Direction.WEST_SOUTH -> '╗'
            Direction.SOUTH_EAST, Direction.EAST_SOUTH -> '╔'
            Direction.INVALID -> '0'
        }
    }

    private fun getNextPosition(
        map: Array<Array<Char>>,
        position: Pair<Direction, Pair<Int, Int>>
    ): Pair<Direction, Pair<Int, Int>> {
        val currentCoordinates = position.second
        return when (position.first) {
            Direction.NORTH_SOUTH -> {
                val coordinates = currentCoordinates.first + 1 to currentCoordinates.second
                val direction = try {
                    when (map[coordinates.first][coordinates.second]) {
                        '|' -> Direction.NORTH_SOUTH
                        'L' -> Direction.NORTH_EAST
                        'J' -> Direction.NORTH_WEST
                        else -> Direction.INVALID
                    }
                } catch (ignored: Exception) {
                    Direction.INVALID
                }
                direction to coordinates
            }

            Direction.SOUTH_NORTH -> {
                val coordinates = currentCoordinates.first - 1 to currentCoordinates.second
                val direction = try {
                    when (map[coordinates.first][coordinates.second]) {
                        '|' -> Direction.SOUTH_NORTH
                        '7' -> Direction.SOUTH_WEST
                        'F' -> Direction.SOUTH_EAST
                        else -> Direction.INVALID
                    }
                } catch (ignored: Exception) {
                    Direction.INVALID
                }
                direction to coordinates
            }

            Direction.EAST_WEST -> {
                val coordinates = currentCoordinates.first to currentCoordinates.second - 1
                val direction = try {
                    when (map[coordinates.first][coordinates.second]) {
                        '-' -> Direction.EAST_WEST
                        'L' -> Direction.EAST_NORTH
                        'F' -> Direction.EAST_SOUTH
                        else -> Direction.INVALID
                    }
                } catch (ignored: Exception) {
                    Direction.INVALID
                }
                direction to coordinates
            }

            Direction.WEST_EAST -> {
                val coordinates = currentCoordinates.first to currentCoordinates.second + 1
                val direction = try {
                    when (map[coordinates.first][coordinates.second]) {
                        '-' -> Direction.WEST_EAST
                        '7' -> Direction.WEST_SOUTH
                        'J' -> Direction.WEST_NORTH
                        else -> Direction.INVALID
                    }
                } catch (ignored: Exception) {
                    Direction.INVALID
                }
                direction to coordinates
            }

            Direction.NORTH_EAST -> {
                val coordinates = currentCoordinates.first to currentCoordinates.second + 1
                val direction = try {
                    when (map[coordinates.first][coordinates.second]) {
                        '-' -> Direction.WEST_EAST
                        '7' -> Direction.WEST_SOUTH
                        'J' -> Direction.WEST_NORTH
                        else -> Direction.INVALID
                    }
                } catch (ignored: Exception) {
                    Direction.INVALID
                }
                direction to coordinates
            }

            Direction.EAST_NORTH -> {
                val coordinates = currentCoordinates.first - 1 to currentCoordinates.second
                val direction = try {
                    when (map[coordinates.first][coordinates.second]) {
                        '|' -> Direction.SOUTH_NORTH
                        '7' -> Direction.SOUTH_WEST
                        'F' -> Direction.SOUTH_EAST
                        else -> Direction.INVALID
                    }
                } catch (ignored: Exception) {
                    Direction.INVALID
                }
                direction to coordinates
            }

            Direction.NORTH_WEST -> {
                val coordinates = currentCoordinates.first to currentCoordinates.second - 1
                val direction = try {
                    when (map[coordinates.first][coordinates.second]) {
                        '-' -> Direction.EAST_WEST
                        'L' -> Direction.EAST_NORTH
                        'F' -> Direction.EAST_SOUTH
                        else -> Direction.INVALID
                    }
                } catch (ignored: Exception) {
                    Direction.INVALID
                }
                direction to coordinates
            }

            Direction.WEST_NORTH -> {
                val coordinates = currentCoordinates.first - 1 to currentCoordinates.second
                val direction = try {
                    when (map[coordinates.first][coordinates.second]) {
                        '|' -> Direction.SOUTH_NORTH
                        '7' -> Direction.SOUTH_WEST
                        'F' -> Direction.SOUTH_EAST
                        else -> Direction.INVALID
                    }
                } catch (ignored: Exception) {
                    Direction.INVALID
                }
                direction to coordinates
            }

            Direction.SOUTH_WEST -> {
                val coordinates = currentCoordinates.first to currentCoordinates.second - 1
                val direction = try {
                    when (map[coordinates.first][coordinates.second]) {
                        '-' -> Direction.EAST_WEST
                        'F' -> Direction.EAST_SOUTH
                        'L' -> Direction.EAST_NORTH
                        else -> Direction.INVALID
                    }
                } catch (ignored: Exception) {
                    Direction.INVALID
                }
                direction to coordinates
            }

            Direction.WEST_SOUTH -> {
                val coordinates = currentCoordinates.first + 1 to currentCoordinates.second
                val direction = try {
                    when (map[coordinates.first][coordinates.second]) {
                        '|' -> Direction.NORTH_SOUTH
                        'L' -> Direction.NORTH_EAST
                        'J' -> Direction.NORTH_WEST
                        else -> Direction.INVALID
                    }
                } catch (ignored: Exception) {
                    Direction.INVALID
                }
                direction to coordinates
            }

            Direction.SOUTH_EAST -> {
                val coordinates = currentCoordinates.first to currentCoordinates.second + 1
                val direction = try {
                    when (map[coordinates.first][coordinates.second]) {
                        '-' -> Direction.WEST_EAST
                        '7' -> Direction.WEST_SOUTH
                        'J' -> Direction.WEST_NORTH
                        else -> Direction.INVALID
                    }
                } catch (ignored: Exception) {
                    Direction.INVALID
                }
                direction to coordinates
            }

            Direction.EAST_SOUTH -> {
                val coordinates = currentCoordinates.first + 1 to currentCoordinates.second
                val direction = try {
                    when (map[coordinates.first][coordinates.second]) {
                        '|' -> Direction.NORTH_SOUTH
                        'L' -> Direction.NORTH_EAST
                        'J' -> Direction.NORTH_WEST
                        else -> Direction.INVALID
                    }
                } catch (ignored: Exception) {
                    Direction.INVALID
                }
                direction to coordinates
            }

            Direction.INVALID -> Direction.INVALID to (-1 to -1)
        }
    }

    enum class Direction {
        NORTH_SOUTH,
        SOUTH_NORTH,
        EAST_WEST,
        WEST_EAST,
        NORTH_EAST,
        EAST_NORTH,
        NORTH_WEST,
        WEST_NORTH,
        SOUTH_WEST,
        WEST_SOUTH,
        SOUTH_EAST,
        EAST_SOUTH,
        INVALID
    }
}
