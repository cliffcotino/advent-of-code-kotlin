import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Long {
        val histories = input.map { it.splitToLongs() }
        // histories.println()

        val differences = histories.map { seq -> seq to (listOf(seq) + findDifferences(seq)) }
        differences.println()

        differences.forEach { p ->
            if (p.second.any { it.isEmpty() }) {
                println(p)
            }
        }

        val extrapolations = differences.map { difference -> extrapolate(difference.second) }
        extrapolations.println()

        return extrapolations.sumOf { it.last() }
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    assertEquals(part1(testInput), 114)
//    part1(listOf("-7 6 44 130 309")).println()

    val input = readInput("Day09")
    part1(input).println() // 2.184.463.514 not
//    part2(input).println()
}

private tailrec fun findDifferences(
    seq: List<Long>,
    acc: List<List<Long>> = emptyList()
): List<List<Long>> {
    val history = (1 until seq.size).map { i -> abs(seq[i - 1] - seq[i]) }

    return when {
        history.all { it == 0L } || history.size == 1 -> acc + listOf(history)
        else -> findDifferences(history, acc + listOf(history))
    }
}

private fun extrapolate(sequences: List<List<Long>>): List<Long> {
    val extrapolations = mutableListOf<Long>()
    (sequences.size - 1 downTo 0).forEach { r ->
        val lastOfRow = sequences[r].last()
        val previousExtrapolation = extrapolations.lastOrNull() ?: 0L
        extrapolations.add(lastOfRow + previousExtrapolation)
    }
    return extrapolations
}
