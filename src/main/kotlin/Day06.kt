import java.lang.IllegalArgumentException

class Day06 : Day() {

    fun test1direct(value: String): Int =
        findStartOf(value, 4)

    fun test1(file: String): Int {
        val line = readLines(file)[0]
        return test1direct(line)
    }

    fun test2direct(value: String): Int =
        findStartOf(value, 14)

    private fun findStartOf(value: String, uniqueLength: Int): Int {
        var start = uniqueLength - 1
        while (start < value.length) {
            val until = start + 1
            val word = value.substring(start - (uniqueLength - 1), until)
            if (word.toSet().size == uniqueLength) {
                // all unique characters
                return until
            }
            start++
        }
        throw IllegalArgumentException("Could not find marker")
    }

    fun test2(file: String): Int {
        val line = readLines(file)[0]
        return test2direct(line)
    }

}
