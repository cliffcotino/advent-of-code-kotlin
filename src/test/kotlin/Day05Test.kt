
import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import kotlin.test.assertEquals

@Disabled
class Day05Test {

    private val day = Day05()

    @Test
    fun `test1 sample`() {
        val actual = day.test1("sample")
        assertEquals("CMZ", actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = day.test2("sample")
        assertEquals("MCD", actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = day.test1("input")
        assertEquals("WCZTHTMPS", actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = day.test2("input")
        assertEquals("BLSGJSDTS", actual)
    }
}