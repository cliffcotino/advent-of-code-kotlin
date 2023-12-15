fun main() {

    fun part1(input: List<String>): Int {
        val columns = input.toColumns()
        return columns
            .map { column -> column.tiltNorth() }
            .sumOf { column -> column.totalLoadForColumn() }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    assertEquals(part1(testInput), 136)
    // assertEquals(part2(testInput), 136)

    val input = readInput("Day14")
    part1(input).println() // 105982
    // part2(input).println()
}

private fun List<String>.toColumns(): List<String> {
    return indices.map { c -> List(size) { this[it][c] }.joinToString("") }
}

private fun String.tiltNorth(): String {

    val mutableChars = this.toMutableList()
    var lastObstacle = -1
    for (i in indices) {
        val current = get(i)
        if (current == 'O') {
            lastObstacle += 1
            mutableChars[i] = '.'
            mutableChars[lastObstacle] = 'O'
        } else if (current == '#') {
            lastObstacle = i
        }
    }
    return mutableChars.joinToString("")
}

private fun String.totalLoadForColumn(): Int {
    return toCharArray()
        .mapIndexed { i, c ->
            if (c == 'O') {
                length - i
            } else {
                0
            }
        }
        .sum()
}
