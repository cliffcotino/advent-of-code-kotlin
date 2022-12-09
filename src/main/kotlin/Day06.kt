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
        return value.windowed(uniqueLength)
            .mapIndexedNotNull { i, part ->
                if (part.toSet().size == uniqueLength) {
                    // all unique characters
                    i + uniqueLength
                } else null
            }.first()
    }

    fun test2(file: String): Int {
        val line = readLines(file)[0]
        return test2direct(line)
    }

}
