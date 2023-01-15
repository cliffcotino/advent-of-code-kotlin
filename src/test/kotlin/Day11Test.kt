
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {

    private val day = Day11()

    @Test
    fun `test1 sample`() {
        val actual = day.test1("sample")
        assertEquals(10_605, actual)
    }

    @Test
    fun `test2 smaller sample`() {
        val actual = day.test2("sample", rounds = 20)
        assertEquals(10_197, actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = day.test2("sample")
        assertEquals(2_713_310_158L, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = day.test1("input")
        assertEquals(51075, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = day.test2("input")
        assertEquals(11_741_456_163, actual)
    }
}