
import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Test {

    private val day = Day02()

    @Test
    fun `test1 sample`() {
        val actual = day.test1("sample")
        assertEquals(1, actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = day.test2("sample")
        assertEquals(1, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = day.test1()
        assertEquals(1, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = day.test2()
        assertEquals(1, actual)
    }
}