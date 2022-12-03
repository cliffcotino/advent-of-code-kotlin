class Day03 : Day() {

    data class Rucksack(val first: String, val second: String) {
        fun priority(): Int {
            val intersect = first.toSet().intersect(second.toSet())
            check(intersect.size == 1)

            val overlap = intersect.first()
            return priorityOf(overlap)
        }

        fun all(): Set<Char> =
            (first + second).toSet()
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
            .sumOf { it.priority() }

    private fun String.toRucksack(): Rucksack {
        val first = this.substring(0, length / 2)
        val second = this.substring(length / 2)
        return Rucksack(first, second)
    }

    fun test2(file: String): Int =
        readLines(file)
            .map { it.toRucksack() }
            .chunked(3)
            .sumOf { list -> priorityOf(intersect(list)) }

    private fun intersect(list: List<Rucksack>): Char {
        val overlap = list[0].all().intersect(list[1].all()).intersect(list[2].all())
        check(overlap.size == 1)
        return overlap.first()
    }

}