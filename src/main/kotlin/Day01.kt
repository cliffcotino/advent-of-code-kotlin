class Day01 : Day() {

    data class Calories(val value: Int) {
        operator fun plus(other: Calories): Calories =
            Calories(value + other.value)
    }

    private val comparator = compareByDescending<Calories> { it.value }

    fun test1(file: String = "input"): Calories {
        val calories = groupsOfCalories(file, 1)
        return calories.sum()
    }

    fun test2(file: String = "input"): Calories {
        val calories = groupsOfCalories(file, 3)
        return calories.sum()
    }

    private fun List<Calories>.sum() =
        fold(Calories(0)) { c1, c2 -> c1 + c2 }

    private fun groupsOfCalories(file: String, take: Int): List<Calories> {
        var last = Calories(0)
        val list = mutableListOf<Calories>()
        readLines(file).forEach { line ->
            last = if (line.isNotEmpty()) {
                last + Calories(line.toInt())
            } else {
                list.add(last)
                Calories(0)
            }
        }
        if (last.value > 0) {
            list.add(last)
        }
        return list.sortedWith(comparator).take(take)
    }
}
