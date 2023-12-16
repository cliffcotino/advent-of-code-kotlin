import kotlin.math.max

fun main() {

    fun getLasers(lasers: MutableSet<Laser>): Laser {
        val laser = lasers.last()
        lasers.remove(laser)
        return laser
    }

    fun totalSeen(seen: List<MutableList<Boolean>>) =
        seen.sumOf { row -> row.count { it } }

    fun solveForGrid(grid: List<List<Char>>, startingLaser: Laser): Int {
        val lasers = HashSet<Laser>()
        lasers.add(startingLaser)
        val seen = grid.map { line -> line.map { false }.toMutableList() }
        val seenLasers = grid.map { line -> line.map { hashSetOf<Char>() } }
        while (lasers.isNotEmpty()) {
            val laser = getLasers(lasers)
            laser.updateSeen(seen)
            laser.updateSeenLasers(seenLasers)
            val tile = grid[laser.position.y][laser.position.x]
            val newLasers = laser.encounter(tile)
            val inBoundsLasers = newLasers.filterNot { it.isOutOfBounds(grid) || it.isSeenAtLocation(seenLasers) }
            lasers.addAll(inBoundsLasers)
    //            println("$laser encountered $tile --> new $newLasers")
    //            seen.joinToString("\n")
    //                 { line -> line.joinToString("") { if (it) "#" else "." } }
    //                .println()
    //            println()
        }
        return totalSeen(seen)
    }

    fun part1(input: List<String>): Int {
        val grid = input.map { line -> line.toList() }
        val startingLaser = LaserRight(position = GridPosition(x = 0, y = 0))
        return solveForGrid(grid, startingLaser)
    }

    fun part2(input: List<String>): Int {
        val grid = input.map { line -> line.toList() }
        val columns = grid[0].size

        var maximum = 0
        // from left of the grid
        repeat(grid.size) {
            val startingLaser = LaserRight(position = GridPosition(x = 0, y = it))
            maximum = max(maximum, solveForGrid(grid, startingLaser))
        }
        // from right of the grid
        repeat(grid.size) {
            val startingLaser = LaserLeft(position = GridPosition(x = columns - 1, y = it))
            maximum = max(maximum, solveForGrid(grid, startingLaser))
        }
        // from top of the grid
        repeat(columns) {
            val startingLaser = LaserDown(position = GridPosition(x = it, y = 0))
            maximum = max(maximum, solveForGrid(grid, startingLaser))
        }
        // from bottom of the grid
        repeat(columns) {
            val startingLaser = LaserUp(position = GridPosition(x = it, y = grid.size - 1))
            maximum = max(maximum, solveForGrid(grid, startingLaser))
        }

        return maximum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    assertEquals(part1(testInput), 46)
    assertEquals(part2(testInput), 51)

    val input = readInput("Day16")
    part1(input).println() // 7415
    part2(input).println() // 7943
}

private fun Laser.isSeenAtLocation(seenLasers: List<List<java.util.HashSet<Char>>>): Boolean {
    return seenLasers[position.y][position.x].contains(direction)
}

private fun Laser.isOutOfBounds(grid: List<List<Char>>): Boolean {
    val x = position.x
    val y = position.y
    return (x < 0 || x >= grid[0].size) || (y < 0 || y >= grid.size)
}

private fun Laser.updateSeen(seen: List<MutableList<Boolean>>) {
    seen[position.y][position.x] = true
}

private fun Laser.updateSeenLasers(seen: List<List<HashSet<Char>>>) {
    seen[position.y][position.x].add(direction)
}

private data class GridPosition(val x: Int, val y: Int) {
    fun left(): GridPosition = copy(x = x - 1)

    fun right(): GridPosition = copy(x = x + 1)

    fun up(): GridPosition = copy(y = y - 1)

    fun down(): GridPosition = copy(y = y + 1)
}

private sealed interface Laser {
    val position: GridPosition
    val direction: Char
    fun encounter(tile: Char): List<Laser>
}

private data class LaserRight(override val position: GridPosition) : Laser {
    override val direction: Char = '>'
    override fun encounter(tile: Char): List<Laser> =
        when (tile) {
            '.' -> listOf(copy(position = position.right()))
            '/' -> listOf(LaserUp(position = position.up()))
            '\\' -> listOf(LaserDown(position = position.down()))
            '|' -> listOf(LaserUp(position = position.up()), LaserDown(position = position.down()))
            '-' -> listOf(copy(position = position.right()))
            else -> throw IllegalArgumentException()
        }
    override fun toString(): String = "$direction (${position.x}, ${position.y})"
}

private data class LaserLeft(override val position: GridPosition) : Laser {
    override val direction: Char = '<'
    override fun encounter(tile: Char): List<Laser> =
        when (tile) {
            '.' -> listOf(copy(position = position.left()))
            '/' -> listOf(LaserDown(position = position.down()))
            '\\' -> listOf(LaserUp(position = position.up()))
            '|' -> listOf(LaserUp(position = position.up()), LaserDown(position = position.down()))
            '-' -> listOf(copy(position = position.left()))
            else -> throw IllegalArgumentException()
        }

    override fun toString(): String = "$direction (${position.x}, ${position.y})"
}

private data class LaserUp(override val position: GridPosition) : Laser {
    override val direction: Char = '^'
    override fun encounter(tile: Char): List<Laser> =
        when (tile) {
            '.' -> listOf(copy(position = position.up()))
            '/' -> listOf(LaserRight(position = position.right()))
            '\\' -> listOf(LaserLeft(position = position.left()))
            '|' -> listOf(copy(position = position.up()))
            '-' ->
                listOf(
                    LaserLeft(position = position.left()),
                    LaserRight(position = position.right())
                )
            else -> throw IllegalArgumentException()
        }
    override fun toString(): String = "$direction (${position.x}, ${position.y})"
}

private data class LaserDown(override val position: GridPosition) : Laser {
    override val direction: Char = 'v'
    override fun encounter(tile: Char): List<Laser> =
        when (tile) {
            '.' -> listOf(copy(position = position.down()))
            '/' -> listOf(LaserLeft(position = position.left()))
            '\\' -> listOf(LaserRight(position = position.right()))
            '|' -> listOf(copy(position = position.down()))
            '-' ->
                listOf(
                    LaserLeft(position = position.left()),
                    LaserRight(position = position.right())
                )
            else -> throw IllegalArgumentException()
        }

    override fun toString(): String = "$direction (${position.x}, ${position.y})"
}
