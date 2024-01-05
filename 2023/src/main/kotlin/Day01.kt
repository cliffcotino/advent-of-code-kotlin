
fun main() {

    val mapOfNumbers = mapOf(
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun String.findDigits(progression: IntProgression): Int {
        progression.forEach { i ->
            val match = mapOfNumbers.entries
                .firstOrNull { pair -> substring(i).startsWith(pair.key) }

            if (match != null) {
                return match.value
            }
        }
        throw IllegalArgumentException()
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { s ->
            val first = s.first { it.isDigit() }
            val last = s.last { it.isDigit() }
            "$first$last".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { s ->
            val first = s.findDigits(s.indices)
            val last = s.findDigits(s.length - 1 downTo 0)
            "$first$last".toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    assertEquals(part1(testInput), 142)

    val testInput2 = readInput("Day01_test2")
    assertEquals(part2(testInput2), 281)

    val input = readInput("Day01")
    part1(input).println() // 56108
    part2(input).println() // 55652
}
