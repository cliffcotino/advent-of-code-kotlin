
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.test.Test
import kotlin.test.assertEquals

class Day08Test : DayTemplate() {

    class Forest(val trees: MutableList<IntArray> = mutableListOf()
    ) {
        fun addTrees(row: IntArray) {
            trees.add(row)
        }

        fun isTreeVisible(row: Int, column: Int): Boolean {
            val treeHeight = trees[row][column]

            val fromLeft =
                (column - 1 downTo 0) // (0 until column)
                    .none { obscuredBy(row, it, treeHeight) }
            val fromRight =
                (column + 1 until trees[row].size)
                    .none { obscuredBy(row, it, treeHeight) }
            val fromTop =
                (row - 1 downTo 0) // (0 until row)
                    .none { obscuredBy(it, column, treeHeight) }
            val fromBottom =
                (row + 1 until trees.size)
                    .none { obscuredBy(it, column, treeHeight) }

            return fromLeft || fromRight || fromTop || fromBottom
        }

        private fun obscuredBy(row: Int, column: Int, height: Int) = height <= trees[row][column]

        fun scenicScore(row: Int, column: Int): Int {
            val treeHeight = trees[row][column]
            val blocked = Array(4) { AtomicBoolean(false) }
            val fromLeft =
                (column - 1 downTo 0) // (0 until column)
                    .takeWhile { notBlockedYet(blocked[0]) { !obscuredBy(row, it, treeHeight) } }
            val fromRight =
                (column + 1 until trees[row].size)
                    .takeWhile { notBlockedYet(blocked[1]) { !obscuredBy(row, it, treeHeight) } }
            val fromTop =
                (row - 1 downTo 0) // (0 until row)
                    .takeWhile { notBlockedYet(blocked[2]) { !obscuredBy(it, column, treeHeight) } }
            val fromBottom =
                (row + 1 until trees.size)
                    .takeWhile { notBlockedYet(blocked[3]) { !obscuredBy(it, column, treeHeight) } }

            return fromLeft.count() * fromRight.count() * fromTop.count() * fromBottom.count()
        }

        private fun notBlockedYet(wasBlocked: AtomicBoolean, predicate: () -> Boolean): Boolean {
            if (wasBlocked.get()) {
                return false
            }
            val blockedNow = predicate.invoke()
            if (!blockedNow) {
                wasBlocked.set(true)
            }
            return true
        }
    }

    fun test1(file: String): Int {
        val forest = parseForest(file)

        return (0 until forest.trees.size)
            .sumOf { row ->
                (0 until forest.trees[row].size)
                    .count { column -> forest.isTreeVisible(row, column) }
            }
    }

    private fun parseForest(file: String): Forest {
        val forest = Forest()
        readLines(file)
            .forEach { line ->
                val trees = line.split("")
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
                    .toIntArray()
                forest.addTrees(trees)
            }
        return forest
    }

    fun test2(file: String): Int {
        val forest = parseForest(file)

        val scenicScores = (0 until forest.trees.size)
            .flatMap { row ->
                (0 until forest.trees[row].size)
                    .map { column -> forest.scenicScore(row, column) }
            }
            .sortedDescending()
        return scenicScores.first()
    }

    @Test
    fun `test1 sample`() {
        val actual = test1("sample")
        assertEquals(21, actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = test2("sample")
        assertEquals(8, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = test1("input")
        assertEquals(1843, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = test2("input")
        assertEquals(180000, actual)
    }
}
