
import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {

    private val day = Day06()

    @Test
    fun `test1 sample`() {
        val actual = day.test1("sample")
        assertEquals(7, actual)
    }

    @Test
    fun `test1 sample from string`() {
        assertEquals(7, day.test1direct("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(5, day.test1direct("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(6, day.test1direct("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(10, day.test1direct("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(11, day.test1direct("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }

    @Test
    fun `test2 sample`() {
        val actual = day.test2("sample")
        assertEquals(19, actual)
    }

    @Test
    fun `test2 sample from string`() {
        assertEquals(19, day.test2direct("mjqjpqmgbljsphdztnvjfqwrcgsmlb"))
        assertEquals(23, day.test2direct("bvwbjplbgvbhsrlpgdmjqwftvncz"))
        assertEquals(23, day.test2direct("nppdvjthqldpwncqszvftbrmjlhg"))
        assertEquals(29, day.test2direct("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"))
        assertEquals(26, day.test2direct("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"))
    }

    @Test
    fun `test1 actual`() {
        val actual = day.test1("input")
        assertEquals(1080, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = day.test2("input")
        assertEquals(3645, actual)
    }
}