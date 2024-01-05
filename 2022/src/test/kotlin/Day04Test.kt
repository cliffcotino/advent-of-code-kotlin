
import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Test : DayTemplate() {

    data class Section(val start: Int, val end: Int) {
        fun contains(other: Section): Boolean =
            start <= other.start && end >= other.end

        fun overlap(other: Section): Int {
            return Integer.max(0, Integer.min(end, other.end) - Integer.max(start, other.start) + 1)
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

    @Test
    fun `test1 sample`() {
        val actual = test1("sample")
        assertEquals(2, actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = test2("sample")
        assertEquals(4, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = test1("input")
        assertEquals(500, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = test2("input")
        assertEquals(815, actual)
    }
}
