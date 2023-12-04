
import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test : DayTemplate() {

    fun test1direct(value: String): Int =
        findStartOf(value, 4)

    fun test1(file: String): Int {
        val line = readLines(file)[0]
        return test1direct(line)
    }

    fun test2direct(value: String): Int =
        findStartOf(value, 14)

    private fun findStartOf(value: String, uniqueLength: Int): Int {
        return value.windowed(uniqueLength)
            .mapIndexedNotNull { i, part ->
                if (part.toSet().size == uniqueLength) {
                    // all unique characters
                    i + uniqueLength
                } else null
            }.first()
    }

    fun test2(file: String): Int {
        val line = readLines(file)[0]
        return test2direct(line)
    }

    @Test
    fun `test1 sample`() {
        val actual = test1("sample")
        assertEquals(7, actual)
    }

    @Test
    fun `test1 sample from string`() {
        assertEquals(7, test1direct("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(5, test1direct("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(6, test1direct("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(10, test1direct("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(11, test1direct("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }

    @Test
    fun `test2 sample`() {
        val actual = test2("sample")
        assertEquals(19, actual)
    }

    @Test
    fun `test2 sample from string`() {
        assertEquals(19, test2direct("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(23, test2direct("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(23, test2direct("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(29, test2direct("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(26, test2direct("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }

    @Test
    fun `test1 actual`() {
        val actual = test1("input")
        assertEquals(1080, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = test2("input")
        assertEquals(3645, actual)
    }
}
