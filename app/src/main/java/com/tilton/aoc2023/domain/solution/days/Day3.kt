package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.domain.solution.Solution

class Day3(override val input: List<String>) : Solution {
    override fun part1(): Long {
        val (numbers, symbols) = getNumbersAndSymbols(input) { character ->
            character != '.'
        }

        return numbers.filter { number ->
            val row = number.row
            symbols.any {
                (it.row == row ||
                        it.row - 1 == row ||
                        it.row + 1 == row) &&
                        (number.range.contains(it.position) ||
                                number.range.first - 1 == it.position ||
                                number.range.last + 1 == it.position)
            }
        }.sumOf { it.value }.toLong()
    }

    override fun part2(): Long {
        val (numbers, symbols) = getNumbersAndSymbols(input) { character ->
            character == '*'
        }

        return symbols.sumOf { symbol ->
            val row = symbol.row
            val position = symbol.position
            val filteredNumbers = numbers
                .filter { number ->
                    (number.row == row || number.row - 1 == row || number.row + 1 == row) &&
                            (number.range.contains(position) ||
                                    number.range.first - 1 == position ||
                                    number.range.last + 1 == position)
                }

            if (filteredNumbers.size == 2) {
                filteredNumbers[0].value * filteredNumbers[1].value
            } else {
                0
            }
        }.toLong()
    }

    private fun getNumbersAndSymbols(
        input: List<String>,
        isSymbolCheck: (Char) -> Boolean
    ): Pair<List<Number>, List<Symbol>> {
        val numbers = mutableListOf<Number>()
        val symbols = mutableListOf<Symbol>()
        var cachedNumber = ""
        val indexRange = mutableListOf<Int>()

        input.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { charIndex, character ->
                if (character.isDigit()) {
                    indexRange.add(charIndex)
                    cachedNumber += character
                } else {
                    if (storeCachedNumberIfNecessary(numbers, cachedNumber, lineIndex, indexRange)) {
                        cachedNumber = ""
                    }

                    if (isSymbolCheck(character)) {
                        symbols.add(Symbol(lineIndex, charIndex))
                    }
                }
            }

            // Numbers can be add the end of a line so we might need to store a cached number
            if (storeCachedNumberIfNecessary(numbers, cachedNumber, lineIndex, indexRange)) {
                cachedNumber = ""
            }
        }

        return numbers to symbols
    }

    private fun storeCachedNumberIfNecessary(
        numbers: MutableList<Number>,
        cachedNumber: String,
        lineIndex: Int,
        indexRange: MutableList<Int>
    ): Boolean {
        return if (cachedNumber.isNotEmpty()) {
            numbers.add(
                Number(
                    lineIndex,
                    IntRange(indexRange[0], indexRange[0] + indexRange.size - 1),
                    cachedNumber.toInt()
                )
            )
            indexRange.clear()
            true
        } else {
            false
        }
    }

    private data class Number(val row: Int, val range: IntRange, val value: Int)
    private data class Symbol(val row: Int, val position: Int)
}
