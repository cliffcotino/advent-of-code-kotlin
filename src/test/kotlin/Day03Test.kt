
import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {

    private val day = Day03()

    @Test
    fun `test1 sample`() {
        val actual = day.test1("sample")
        assertEquals(157, actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = day.test2("sample")
        assertEquals(70, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = day.test1("input")
        assertEquals(8185, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = day.test2("input")
        assertEquals(2817, actual)
    }
}