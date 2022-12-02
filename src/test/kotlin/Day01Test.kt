
import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {

    private val day = Day01()

    @Test
    fun `test1 sample`() {
        val actual = day.test1("sample2")
        assertEquals(24000, actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = day.test2("sample")
        assertEquals(45000, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = day.test1()
        assertEquals(69626, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = day.test2()
        assertEquals(206780, actual)
    }
}