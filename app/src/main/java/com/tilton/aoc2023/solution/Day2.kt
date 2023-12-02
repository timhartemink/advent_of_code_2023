package com.tilton.aoc2023.solution

class Day2 {
    private val firstNumberRegex = "(\\d+)".toRegex()
    private val lastNumberRegex = "(\\d+)(?!.*\\d)".toRegex()

    fun part1(input: List<String>): String {
        return input.sumOf { line ->
            val id = firstNumberRegex.find(line)?.value?.toInt() ?: error("Could not find game ID")
            val sets = line.substring(line.indexOf(":") + 1).split(";")
            if (sets.all { checkSetIsValid(it) })
                id
            else
                0
        }.toString()
    }

    fun part2(input: List<String>): String {
        return input.sumOf { line ->
            val sets = line.substring(line.indexOf(":") + 1).split(";")
            var maxRed = 0
            var maxGreen = 0
            var maxBlue = 0
            sets.forEach {
                val red = getAmountOfCubes(it, "red")
                if (red > maxRed) maxRed = red

                val green = getAmountOfCubes(it, "green")
                if (green > maxGreen) maxGreen = green

                val blue = getAmountOfCubes(it, "blue")
                if (blue > maxBlue) maxBlue = blue
            }
            maxRed * maxGreen * maxBlue
        }.toString()
    }

    private fun checkSetIsValid(set: String): Boolean {
        val redCubes = getAmountOfCubes(set, "red")
        val greenCubes = getAmountOfCubes(set, "green")
        val blueCubes = getAmountOfCubes(set, "blue")
        return redCubes <= MAX_RED_CUBES && greenCubes <= MAX_GREEN_CUBES && blueCubes <= MAX_BLUE_CUBES
    }

    private fun getAmountOfCubes(set: String, color: String): Int {
        val colorIndex = set.lastIndexOf(color)
        return if (colorIndex >= 0)
            lastNumberRegex.find(set.substring(0, colorIndex - 1))?.value?.toInt() ?: -1
        else
            -1
    }

    companion object {
        private const val MAX_RED_CUBES = 12
        private const val MAX_GREEN_CUBES = 13
        private const val MAX_BLUE_CUBES = 14
    }
}
