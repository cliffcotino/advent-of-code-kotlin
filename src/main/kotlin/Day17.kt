
fun main() {

    fun part1(input: List<String>): Long {
        return input.size.toLong()
    }

    fun part2(input: List<String>): Long {
        return input.size.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    assertEquals(part1(testInput), 0)

    val input = readInput("Day17")
    part1(input).println()
    part2(input).println()
}
