package com.tilton.aoc2023.util

fun String.getNumbers(): List<Long> {
    val numbers = mutableListOf<Long>()
    var cachedNumber = ""
    forEach { character ->
        if (character.isDigit() || character == '-' || (character >= 48.toChar() && character <= 57.toChar())) {
            cachedNumber += character
        } else {
            if (storeCachedNumberIfNecessary(numbers, cachedNumber)) {
                cachedNumber = ""
            }
        }
    }

    if (storeCachedNumberIfNecessary(numbers, cachedNumber)) {
        cachedNumber = ""
    }

    return numbers
}

private fun storeCachedNumberIfNecessary(
    numbers: MutableList<Long>,
    cachedNumber: String,
): Boolean {
    return if (cachedNumber.isNotEmpty()) {
        numbers.add(cachedNumber.toLong())
        true
    } else {
        false
    }
}
