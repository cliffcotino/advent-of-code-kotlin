
class Day03 : Day() {

    data class Rucksack(val first: String, val second: String) {

        val all: String
            get() = first + second
    }

    companion object {
        private const val priorities = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

        fun priorityOf(char: Char): Int {
            return priorities.indexOf(char) + 1
        }
    }

    fun test1(file: String): Int =
        readLines(file)
            .map { it.toRucksack() }
            .map { listOf(it.first, it.second).overlap() }
            .sumOf { priorityOf(it) }

    private fun String.toRucksack(): Rucksack {
        val first = substring(0, length / 2)
        val second = substring(length / 2)
        return Rucksack(first, second)
    }

    fun test2(file: String): Int =
        readLines(file)
            .map { it.toRucksack() }
            .chunked(3)
            .map { group -> group.map { it.all }.overlap() }
            .sumOf { priorityOf(it) }

    private fun List<String>.overlap(): Char {
        val overlap = map { it.toSet() }
            .fold(setOf<Char>()) { acc, rucksack -> if (acc.isEmpty()) rucksack else acc.intersect(rucksack) }
        check(overlap.size == 1)
        return overlap.first()
    }

}
