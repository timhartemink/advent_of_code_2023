package com.tilton.aoc2023.domain.solution

import android.content.res.AssetManager
import android.util.Log
import com.tilton.aoc2023.BuildConfig
import com.tilton.aoc2023.domain.solution.days.Day1
import com.tilton.aoc2023.domain.solution.days.Day10
import com.tilton.aoc2023.domain.solution.days.Day14
import com.tilton.aoc2023.domain.solution.days.Day2
import com.tilton.aoc2023.domain.solution.days.Day3
import com.tilton.aoc2023.domain.solution.days.Day4
import com.tilton.aoc2023.domain.solution.days.Day5
import com.tilton.aoc2023.domain.solution.days.Day6
import com.tilton.aoc2023.domain.solution.days.Day7
import com.tilton.aoc2023.domain.solution.days.Day8
import com.tilton.aoc2023.domain.solution.days.Day9
import com.tilton.aoc2023.domain.solution.model.Answer
import com.tilton.aoc2023.util.AssetReader
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SolutionRepository(
    private val assetManager: AssetManager,
    private val dispatcher: CoroutineDispatcher
) {
    @Inject
    constructor(assetManager: AssetManager) : this(assetManager, Dispatchers.IO)

    suspend fun getAnswers(): List<Answer> = withContext(dispatcher) {
        getSolutions().asyncAll { index, solution ->
            val day = index + 1
            val part1 = solution.part1()
            val part2 = solution.part2()

            if (BuildConfig.SHOULD_LOG_SOLUTIONS) {
                Log.d("AOC_2023", "#-----------#\nDay $day\npart 1 = ${part1}\npart 2 = $part2\n#-----------#")
            }

            Answer(day, solution.part1(), solution.part2())
        }
    }

    private fun getSolutions(): List<Solution> {
        return buildList {
            for (i in 1..24) {
                getSolutionForDay(i)?.let {
                    add(it)
                }
            }
        }
    }

    private fun getSolutionForDay(day: Int): Solution? {
        return AssetReader.getInputAsList(assetManager, day).getOrNull()?.let {
            when (day) {
                1 -> Day1(it)
                2 -> Day2(it)
                3 -> Day3(it)
                4 -> Day4(it)
                5 -> Day5(it)
                6 -> Day6(it)
                7 -> Day7(it)
                8 -> Day8(it)
                9 -> Day9(it)
                10 -> Day10(it)
                14 -> Day14(it)
                else -> null
            }
        }

    }

    private suspend fun <T, V> List<T>.asyncAll(coroutine: suspend (Int, T) -> V): List<V> = coroutineScope {
        this@asyncAll.mapIndexed { index, item -> async { coroutine(index, item) } }.awaitAll()
    }
}
