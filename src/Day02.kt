import kotlin.math.max

fun main() {

    data class Game(val id: Int, val sets: List<Map<String, Int>>)

    fun Map<String, Int>.isPossible(givenSet: Map<String, Int>): Boolean {
        return all { entry -> givenSet[entry.key]!! >= entry.value }
    }

    fun List<Map<String, Int>>.minimumSet(): Map<String, Int> {
        return fold(mapOf()) { acc, map ->
            (acc.keys + map.keys).associateWith { key ->
                max(acc[key] ?: Int.MIN_VALUE, map[key] ?: Int.MIN_VALUE)
            }
        }
    }

    fun Map<String, Int>.power(): Int {
        return values.fold(1) { acc: Int, v: Int -> acc * v }
    }

    fun String.toSets(): List<Map<String, Int>> {
        val sets = split(";")
        return sets.map { set ->
            val cubes = set.split(", ").map { it.trim() }
            cubes.associate {
                it.substringAfter(" ") to it.substringBefore(" ").toInt()
            }
        }
    }

    fun String.toGame() = Game(
        id = substringBetween("Game ", ":").toInt(),
        sets = substringAfter(":").toSets()
    )

    fun part1(input: List<String>, givenSet: Map<String, Int>): Int {
        return input.map { s -> s.toGame() }
            .filter { game -> game.sets.all { set -> set.isPossible(givenSet) } }
            .sumOf { game -> game.id }
    }

    fun part2(input: List<String>): Int {
        return input.map { s -> s.toGame() }
            .map { game -> game.sets.minimumSet() }
            .sumOf { set -> set.power() }
    }

    val givenSet = mapOf("red" to 12, "green" to 13, "blue" to 14)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    assertEquals(part1(testInput, givenSet), 8)
    assertEquals(part2(testInput), 2286)

    val input = readInput("Day02")
    part1(input, givenSet).println() // 2283
    part2(input).println() // 78669
}
