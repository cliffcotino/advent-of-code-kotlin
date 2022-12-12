
import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {

    private val day = Day10()

    @Test
    fun `test1 sample`() {
        val actual = day.test1("sample")
        assertEquals(13140, actual)
    }

    @Test
    fun `test1 sample-larger`() {
        val actual = day.test1("sample-larger")
        assertEquals(13140, actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = day.test2("sample")
        assertEquals(-1, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = day.test1("input")
        assertEquals(-1, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = day.test2("input")
        assertEquals(-1, actual)
    }
}