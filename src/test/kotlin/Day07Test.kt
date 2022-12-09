
import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import kotlin.test.assertEquals

@Disabled
class Day07Test {

    private val day = Day07()

    @Test
    fun `test1 sample`() {
        val actual = day.test1("sample")
        assertEquals(95437, actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = day.test2("sample")
        assertEquals(24933642, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = day.test1("input")
        assertEquals(1315285, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = day.test2("input")
        assertEquals(9847279, actual)
    }
}