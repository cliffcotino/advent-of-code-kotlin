fun main() {

    fun part1(input: List<String>): Int {
        val parts = input.toParts()
        val symbols = input.toSymbols()
        return parts.filter { number -> number.isAdjacentToAnySymbol(symbols) }
            .sumOf { number -> number.asInt }
    }

    fun part2(input: List<String>): Int {
        val parts = input.toParts()
        val gears = input.toSymbols()
            .filter { s -> s.value == '*' }
            .mapNotNull { s ->
                val adjacentParts = s.findAdjacentParts(parts)
                if (adjacentParts.size == 2) {
                    Gear(adjacentParts[0].asInt, adjacentParts[1].asInt)
                } else {
                    null
                }
            }

        return gears.sumOf { it.gearRatio }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    assertEquals(part1(testInput), 4361)
    assertEquals(part2(testInput), 467835)

    val input = readInput("Day03")
    part1(input).println() // 519444
    part2(input).println() // 74528807
}

private fun Symbol.findAdjacentParts(parts: List<Part>): List<Part> {
    return parts.filter { part -> part.neighbours.any { symbol -> symbol == position } }
}

private fun List<String>.toSymbols(): List<Symbol> {
    return flatMapIndexed { y, s ->
        s.toCharArray()
            .mapIndexed { x: Int, c: Char ->
                if (c != '.' && !c.isDigit()) {
                    Symbol(c, Position(x, y))
                } else {
                    null
                }
            }
            .filterNotNull()
    }.toList()
}

private fun List<String>.toParts(): List<Part> {
    return flatMapIndexed { y, s ->
        s.mapToParts(y)
    }.toList()
}

private fun String.mapToParts(y: Int): List<Part> {
    val regex = Regex("\\d+")
    val matchResults = regex.findAll(this)
    return matchResults.flatMap { matchResult ->
        matchResult.groups.map { group ->
            Part(value = group!!.value, Position(x = group.range.first, y = y))
        }
    }.toList()
}

private fun Part.isAdjacentToAnySymbol(symbols: List<Symbol>): Boolean {
    val neighbours = neighbours
    return neighbours.any { neighbour -> symbols.any { symbol -> symbol.position == neighbour }  }
}

private data class Position(val x: Int, val y: Int) {
    val neighbours: List<Position> by lazy {
        listOf(
            Position(x - 1, y - 1), Position(x, y - 1), Position(x + 1, y - 1),
            Position(x - 1, y), /*this*/ Position(x + 1, y),
            Position(x - 1, y + 1), Position(x, y + 1), Position(x + 1, y + 1),
        )
    }
}

private data class Symbol(val value: Char, val position: Position)

private data class Gear(val part1: Int, val part2: Int) {
    val gearRatio: Int
        get() = part1 * part2
}

private data class Part(val value: String, val position: Position) {
    val asInt: Int
        get() = value.toInt()

    val ownPositions: Set<Position> by lazy {
        value.indices.map { i -> Position(x = position.x + i, y = position.y) }.toSet()
    }

    val neighbours: Set<Position> by lazy {
        ownPositions.flatMap { it.neighbours }.toSet()
    }
}
