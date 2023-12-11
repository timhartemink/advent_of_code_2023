package com.tilton.aoc2023.domain.solution.days

import com.tilton.aoc2023.domain.solution.Solution
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlin.math.max

class Day8(override val input: List<String>) : Solution {
    private val regEx = """\(([^()]*)\)""".toRegex()
    private val sanitizedInput = input.filter { it.isNotEmpty() }

    override fun part1(): Long {
        val instructions = sanitizedInput[0]
        val nodes = sanitizedInput.subList(1, sanitizedInput.size).map { line ->
            val destinationNodes = regEx.findAll(line).map { it.groupValues[1] }.toList()
            line.substringBefore(" ") to
                    destinationNodes[0].split(",").map { it.replace(" ", "") }
        }
        return getSteps(nodes, "AAA", instructions) {
            it == "ZZZ"
        }
    }

    override fun part2(): Long {
        val instructions = sanitizedInput[0]
        val nodes = sanitizedInput.subList(1, sanitizedInput.size).map { line ->
            val destinationNodes = regEx.findAll(line).map { it.groupValues[1] }.toList()
            line.substringBefore(" ") to
                    destinationNodes[0].split(",").map { it.replace(" ", "") }
        }

        val startNodes = nodes.filter { it.first.endsWith("A") }.map { it.copy() }

        val stepsPerNode = runBlocking {
            startNodes.asyncAll { currentNode ->
                getSteps(nodes, currentNode.first, instructions) {
                    it.endsWith("Z")
                }
            }
        }

        return getLCM(stepsPerNode)
    }

    private suspend fun <T, V> List<T>.asyncAll(coroutine: suspend (T) -> V): List<V> = coroutineScope {
        this@asyncAll.map { async { coroutine(it) } }.awaitAll()
    }

    private fun getLCM(nodes: List<Long>): Long {
        var result = nodes[0]
        for (i in 1 until nodes.size) {
            result = getLCM(result, nodes[i])
        }

        return result
    }

    private fun getLCM(left: Long, right: Long): Long {
        var gcd = 1
        var i = 1
        while (i <= left && i <= right) {
            if (left % i == 0L && right % i == 0L)
                gcd = i
            i++
        }

        return (left * right / gcd)
    }

    private fun getSteps(
        nodes: List<Pair<String, List<String>>>,
        currentNodeKey: String,
        instructions: String,
        loopCondition: (String) -> Boolean,
    ): Long {
        var currentNode = nodes.find { it.first == currentNodeKey } ?: error("Could not find AA node")
        var steps = 0L
        var nodeIndex = max(nodes.indexOf(currentNode) - 1, 0)
        var nextInstructionIndex = 0
        while (!loopCondition(currentNode.first)) {
            var found = false
            val firstOrSecondElement = when (instructions[nextInstructionIndex]) {
                'L' -> 0
                'R' -> 1
                else -> error("Instructions should only be L and R")
            }
            while (!found) {
                val node = nodes[nodeIndex]
                if (currentNode.second[firstOrSecondElement] == node.first) {
                    currentNode = node
                    found = true
                }
                nodeIndex = if (nodeIndex == nodes.size - 1) 0 else nodeIndex + 1
            }
            nextInstructionIndex = if (nextInstructionIndex == instructions.length - 1) 0 else nextInstructionIndex + 1
            steps++
        }
        return steps
    }
}
