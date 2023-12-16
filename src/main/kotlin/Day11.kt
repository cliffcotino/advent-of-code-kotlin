import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {

    fun part1(input: List<String>): Long {
        val emptyRows = input.findEmptyRows()
        val emptyColumns = input.findEmptyColumns()
        val galaxyPositions = input.findGalaxyPositions()
        return distanceBetweenGalaxies(galaxyPositions, 2, emptyRows, emptyColumns)
    }

    fun part2(input: List<String>, scaleFactor: Int): Long {
        val emptyRows = input.findEmptyRows()
        val emptyColumns = input.findEmptyColumns()
        val galaxyPositions = input.findGalaxyPositions()
        return distanceBetweenGalaxies(galaxyPositions, scaleFactor, emptyRows, emptyColumns)
    }

    part2("""
        ..#..
        .....
        ..#..
    """.trimIndent().lines(), 1000_000).println()

    part2("""
        .#.#.
        .....
    """.trimIndent().lines(), 2).println()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    assertEquals(part1(testInput), 374)
    assertEquals(part2(testInput, 10), 1030)
    assertEquals(part2(testInput, 100), 8410)

    val input = readInput("Day11")
    part1(input).println() // 9957702
    part2(input, 1_000_000).println() // ?
    // wrong: 1139825014, 1140337246
}

private data class GalaxyPosition(val x: Int, val y: Int, val char: Char)

private fun List<String>.findGalaxyPositions(): List<GalaxyPosition> {
    return (indices).flatMap { row ->
        this[row]
            .toList()
            .mapIndexed { index, char -> GalaxyPosition(char = char, x = index, y = row) }
            .filter { p -> p.char == '#' }
    }
}

private fun distanceBetweenGalaxies(
    galaxyPositions: List<GalaxyPosition>,
    scaleFactor: Int,
    emptyRows: List<Int>,
    emptyColumns: List<Int>
): Long {
    val pairs = findUniquePairs(galaxyPositions)
    return pairs.sumOf { pair ->
        val g1 = pair.first
        val g2 = pair.second
        val intersectionsX = emptyColumns.intersections(g1.x, g2.x)
        val distanceX = abs(g1.x - g2.x).toLong() + (intersectionsX * (scaleFactor - 1))

        val intersectionsY = emptyRows.intersections(g1.y, g2.y)
        val distanceY = abs(g1.y - g2.y).toLong() + (intersectionsY * (scaleFactor - 1))

        distanceX + distanceY
    }
}

private fun findUniquePairs(galaxyPositions: List<GalaxyPosition>): List<Pair<GalaxyPosition, GalaxyPosition>> {
    return galaxyPositions
        .flatMap { g1 -> galaxyPositions.map { g2 -> g1 to g2 } }
        .filter { pair -> pair.first != pair.second }
        .distinctBy { pair -> listOf(pair.first, pair.second).sortedBy { g -> "x=${g.x}, y=${g.y}" } }
}

private fun List<Int>.intersections(v1: Int, v2: Int): Int {
    val lower = min(v1, v2)
    val upper = max(v1, v2)
    return count { v -> v in lower..upper }
}

private fun List<String>.findEmptyRows() =
    mapIndexed { index, row -> Pair(index, row) }.filterEmpty()

private fun List<String>.findEmptyColumns() =
    (0 until this[0].length)
        .mapIndexed { index, col -> Pair(index, getColumn(this, col)) }
        .filterEmpty()

private fun List<Pair<Int, String>>.filterEmpty() =
    filter { p -> isEmptySpace(p.second) }.map { p -> p.first }

private fun isEmptySpace(value: String) = value.count { it == '.' } == value.length

private fun getColumn(rows: List<String>, column: Int) =
    List(rows.size) { rows[it][column] }.joinToString("")
