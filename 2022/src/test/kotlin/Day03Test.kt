
import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test : DayTemplate() {

    data class Rucksack(val first: String, val second: String) {

        val both: String
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
            .map { group -> group.map { it.both }.overlap() }
            .sumOf { priorityOf(it) }

    private fun List<String>.overlap(): Char {
        val overlap = map { it.toSet() }
            .fold(setOf<Char>()) { acc, rucksack -> if (acc.isEmpty()) rucksack else acc.intersect(rucksack) }
        check(overlap.size == 1)
        return overlap.first()
    }

    @Test
    fun `test1 sample`() {
        val actual = test1("sample")
        assertEquals(157, actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = test2("sample")
        assertEquals(70, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = test1("input")
        assertEquals(8185, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = test2("input")
        assertEquals(2817, actual)
    }
}
