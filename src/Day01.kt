
fun main() {

    fun String.findDigits(progression: IntProgression): Int {
        val chars = toCharArray()
        progression.forEach { i ->
            if (chars[i].isDigit()) {
                return chars[i].digitToInt()
            }
            val match = Numbers.entries
                .firstOrNull { number -> substring(i).startsWith(number.name, ignoreCase = true) }

            if (match != null) {
                return match.ordinal + 1
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
    part1(input).println()
    part2(input).println()
}

private enum class Numbers {
    One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten
}
