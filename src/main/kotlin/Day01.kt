
class Day01 : Day() {

    fun test1(file: String = "input"): Int {
        val calories = groupsOfCalories(file, 1)
        return calories.sum()
    }

    fun test2(file: String = "input"): Int {
        val calories = groupsOfCalories(file, 3)
        return calories.sum()
    }

    private fun groupsOfCalories(file: String, take: Int): List<Int> {
        data class Acc(val sum: Int = 0, val list: List<Int> = listOf())

        val (sum, list) = readLines(file)
            .fold(Acc()) { acc, line ->
                if (line.isNotEmpty()) {
                    val current = line.toInt()
                    Acc(sum = acc.sum + current, list = acc.list)
                } else {
                    Acc(sum = 0, list = acc.list + acc.sum)
                }
            }
        // we need to also consider the lastly encountered group
        return (list + sum).sortedDescending().take(take)
    }
}
