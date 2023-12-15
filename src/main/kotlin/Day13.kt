import kotlin.math.min

fun main() {

    val areEqual = { a: String, b: String -> a == b }

    val differenceIsOne = { a: String, b: String ->
        a.zip(b).count { p -> p.first != p.second } == 1
    }

    fun part1(input: List<String>): Int {
        return input
            .groupConsecutiveBy { line -> line.isNotEmpty() }
            .map { list -> ReflectionPattern(rows = list) }
            .sumOf { pattern ->
                println(pattern.rows.joinToString("\n"))
                val mirrorCol = pattern.columns.mirror(areEqual)
                val mirrorRow = pattern.rows.mirror(areEqual)
                println("mirrorRow/mirrorCol: $mirrorRow/$mirrorCol")
                (mirrorCol) + (mirrorRow * 100)
            }
    }

    fun part2(input: List<String>): Int {
        return input
            .groupConsecutiveBy { line -> line.isNotEmpty() }
            .map { list -> ReflectionPattern(rows = list) }
            .sumOf { pattern ->
                val mirrorCol = pattern.columns.mirror(differenceIsOne)
                val mirrorRow = pattern.rows.mirror(differenceIsOne)
                println("mirrorRow/mirrorCol: $mirrorRow/$mirrorCol")
                println(pattern.rows.joinToString("\n"))
                (mirrorCol) + (mirrorRow * 100)
            }
    }

    // test if implementation meets criteria from the description, like:
    assertEquals(part1(readInput("Day13_test")), 405)
    assertEquals(part1(readInput("Day13_test2")), 5) // cols
    assertEquals(part1(readInput("Day13_test3")), 400) // rows

    assertEquals(part2(readInput("Day13_test")), 400)

    val input = readInput("Day13")
    assertEquals(part1(input), 29130) // 29130
    part2(input).println() // ?
}

private fun List<String>.mirror(check: (String, String) -> Boolean): Int {
    return (1 until size).firstOrNull { mirror ->
        val before = sub(0, mirror).joinToString("")
        val after = sub(mirror).joinToString("") { l -> l.reversed() }.reversed()
        val maxLength = min(before.length, after.length)
        // println("mirror $mirror -- '${before}' and '${after}'")
        check.invoke(before.takeLast(maxLength), after) ||
            check.invoke(after.takeLast(maxLength), before)
    } ?: 0
}

private fun List<String>.sub(from: Int, to: Int = Int.MAX_VALUE): List<String> {
    return drop(from).take(to)
}

private data class ReflectionPattern(val rows: List<String>) {

    val columns: List<String> by lazy {
        (0 until rows[0].length).map { c ->
            List(rows.size) { rows[it][c] }.joinToString("")
        }
    }
}
