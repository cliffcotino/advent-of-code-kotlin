
import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test : DayTemplate() {

    fun test1(file: String = "input"): Int {
        val calories = groupsOfCalories(file, 1)
        return calories.sum()
    }

    fun test2(file: String = "input"): Int {
        val calories = groupsOfCalories(file, 3)
        return calories.sum()
    }

    private fun groupsOfCalories(file: String, take: Int): List<Int> {
        data class Accumulator(val sum: Int = 0, val list: List<Int> = listOf())

        val (sum, list) = readLines(file)
            .fold(Accumulator()) { acc, line ->
                if (line.isNotEmpty()) {
                    val current = line.toInt()
                    Accumulator(sum = acc.sum + current, list = acc.list)
                } else {
                    Accumulator(sum = 0, list = acc.list + acc.sum)
                }
            }
        // we need to also consider the lastly encountered group
        return (list + sum).sortedDescending().take(take)
    }

    @Test
    fun `test1 sample`() {
        val actual = test1("sample")
        assertEquals(24000, actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = test2("sample")
        assertEquals(45000, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = test1()
        assertEquals(69626, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = test2()
        assertEquals(206780, actual)
    }
}
