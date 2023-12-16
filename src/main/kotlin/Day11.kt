import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {

    fun part1(input: List<String>): Int {
        val emptyRows = input.findEmptyRows()
        val emptyColumns = input.findEmptyColumns()
        val expandedGalaxy = input.expand(emptyRows = emptyRows, emptyColumns = emptyColumns)
        val galaxyPositions = expandedGalaxy.findGalaxyPositions()
        return galaxyPositions.sumOf { it.distanceToOtherGalaxies(galaxyPositions) } / 2
    }

    fun part2(input: List<String>, scale: Int): Int {
        val emptyRows = input.findEmptyRows()
        val emptyColumns = input.findEmptyColumns()
        val galaxyPositions = input.findGalaxyPositions()
        return galaxyPositions.sumOf { it.distanceToOtherGalaxies(galaxyPositions, scale, emptyRows, emptyColumns) } / 2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    assertEquals(part1(testInput), 374)
    assertEquals(part2(testInput, 10), 1030)
    assertEquals(part2(testInput, 100), 8410)

    val input = readInput("Day11")
    part1(input).println() // 9957702
    part2(input, 1_000_000).println() // ?
}

private fun List<String>.expand(emptyRows: List<Int>, emptyColumns: List<Int>): List<String> {
    val expandedGalaxy = toMutableList()
    var rowsAdded = 0
    emptyRows.forEach { i ->
        val emptyRow = expandedGalaxy[emptyRows[0]]
        expandedGalaxy.add(i + rowsAdded, emptyRow)
        rowsAdded++
    }
    return expandedGalaxy.map {
        val row = it.toMutableList()
        var columnsAdded = 0
        emptyColumns.forEach { i ->
            row.add(i + columnsAdded, '.')
            columnsAdded++
        }
        row.joinToString("")
    }
}

private data class GalaxyPosition(val char: Char, val x: Int, val y: Int)

private fun List<String>.findGalaxyPositions(): List<GalaxyPosition> {
    return (indices).flatMap { r ->
        this[r]
            .toList()
            .mapIndexed { index, char -> GalaxyPosition(char = char, x = index, y = r) }
            .filter { p -> p.char == '#' }
    }
}

private fun GalaxyPosition.distanceToOtherGalaxies(galaxyPositions: List<GalaxyPosition>): Int {
    return galaxyPositions.sumOf { other ->
        if (other == this) {
            0
        } else {
            abs(x - other.x) + abs(y - other.y)
        }
    }
}

private fun GalaxyPosition.distanceToOtherGalaxies(galaxyPositions: List<GalaxyPosition>,
                                                   scale: Int, emptyRows: List<Int>, emptyColumns: List<Int>): Int {
    return galaxyPositions.sumOf { other ->
        if (other == this) {
            0
        } else {
            val intersectionsX = emptyRows.intersections(x, other.x)
            val distanceX = abs(x - other.x) + (intersectionsX * scale)

            val intersectionsY = emptyColumns.intersections(y, other.y)
            val distanceY = abs(y - other.y) + (intersectionsY * scale)

            distanceX + distanceY
        }
    }
}

private fun List<Int>.intersections(v1: Int, v2: Int): Int {
    val lower = min(v1, v2)
    val upper = max(v1, v2)
    val intersections = count { v -> v >= lower && v <= upper }
    return intersections
}

private fun List<String>.findEmptyRows() =
    mapIndexed { index, row -> Pair(index, row) }
        .filterEmpty()

private fun List<String>.findEmptyColumns() =
    (0 until this[0].length)
        .mapIndexed { index, col -> Pair(index, getColumn(this, col)) }
        .filterEmpty()

private fun List<Pair<Int, String>>.filterEmpty() =
    filter { p -> isSpaceOnly(p.second) }
        .map { p -> p.first }

private fun isSpaceOnly(value: String) = value.count { it == '.' } == value.length

private fun getColumn(rows: List<String>, column: Int) =
    List(rows.size) { rows[it][column] }.joinToString("")
