fun main() {

    fun part1(input: List<String>): Int {
        val symbols: List<Symbol> = input.toSymbols()
        val numbers: List<Number> = input.toNumbers()
        return numbers.filter { number -> number.isAdjacentToAnySymbol(symbols) }
            .sumOf { number -> number.asInt }
    }

    fun part2(input: List<String>): Int {
        val numbers: List<Number> = input.toNumbers()
        val gears: List<Gear> = input.toSymbols()
            .filter { s -> s.value == '*' }
            .mapNotNull { s ->
                val adjacentParts: List<Number> = s.findAdjacentNumbers(numbers)
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

private fun Symbol.findAdjacentNumbers(numbers: List<Number>): List<Number> {
    return numbers.filter { number -> number.neighbours().any { symbol -> symbol == position } }
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

private fun List<String>.toNumbers(): List<Number> {
    return flatMapIndexed { y, s ->
        s.mapToNumbers(y)
    }.toList()
}

private fun String.mapToNumbers(y: Int): List<Number> {
    var i = 0
    val numbers = mutableListOf<Number>()
    while (i < length) {
        if (get(i).isDigit()) {
            val digits = substring(i).takeWhile { c -> c.isDigit() }
            numbers.add(Number(value = digits, Position(i, y)))
            i += digits.length
        } else {
            i++
        }
    }
    return numbers
}

private fun Number.isAdjacentToAnySymbol(symbols: List<Symbol>): Boolean {
    val neighbours = neighbours()
    return neighbours.any { neighbour -> symbols.any { symbol -> symbol.position == neighbour }  }
}

private data class Position(val x: Int, val y: Int) {
    fun neighbours(): List<Position> = listOf(
        Position(x - 1, y - 1), Position(x, y - 1), Position(x + 1, y - 1),
        Position(x - 1, y), /*this*/ Position(x + 1, y),
        Position(x - 1, y + 1), Position(x, y + 1), Position(x + 1, y + 1),
    )
}

private data class Symbol(val value: Char, val position: Position)

private data class Gear(val part1: Int, val part2: Int) {
    val gearRatio: Int
        get() = part1 * part2
}

private data class Number(val value: String, val position: Position) {
    val asInt: Int
        get() = value.toInt()

    fun ownPositions(): Set<Position> {
        return value.indices.map { i -> Position(x = position.x + i, y = position.y) }.toSet()
    }

    fun neighbours(): Set<Position> {
        return ownPositions().flatMap { it.neighbours() }.toSet()
    }
}
