
import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test {

    private val day = Day09()

    @Test
    fun `test1 sample`() {
        val actual = day.test1("sample")
        assertEquals(13, actual)
    }

    @Test
    fun `test2 sample larger`() {
        val actual = day.test2("sample-larger")
        assertEquals(36, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = day.test1("input")
        assertEquals(6367, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = day.test2("input")
        assertEquals(-1, actual)
    }
}