import java.lang.Integer.max
import java.lang.Integer.min

class Day04 : Day() {

    data class Section(val start: Int, val end: Int) {
        fun contains(other: Section): Boolean =
            start <= other.start && end >= other.end

        fun overlap(other: Section): Int {
            return max(0, min(end, other.end) - max(start, other.start) + 1)
        }

        companion object {
            fun from(value: String): Section {
                val split = value.split("-")
                return Section(split[0].toInt(), split[1].toInt())
            }
        }
    }

    data class Pair(val section1: Section, val section2: Section) {
        fun hasFullOverlap(): Boolean =
            section1.contains(section2) || section2.contains(section1)

        fun overlap(): Int =
            section1.overlap(section2)
    }

    private fun String.toPair(): Pair {
        val split = split(",")
        return Pair(Section.from(split[0]), Section.from(split[1]))
    }

    fun test1(file: String): Int =
        readLines(file)
            .map { it.toPair() }
            .count { pair -> pair.hasFullOverlap() }

    fun test2(file: String): Int =
        readLines(file)
            .map { it.toPair() }
            .count { pair -> pair.overlap() > 0 }

}
