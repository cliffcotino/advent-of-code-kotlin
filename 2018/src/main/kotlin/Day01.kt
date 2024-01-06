import java.nio.file.Files
import kotlin.streams.toList

class Day01 : Day() {

    fun testSum() {
        val currentSum = Files.lines(getTestInputPath())
                .mapToInt { lineToInt(it) }
                .sum()
        check(currentSum == 484)
    }

    private fun lineToInt(line: String): Int {
        val numbers = if (line.startsWith("+"))
            line.substring(1) else line
        return numbers.toInt()
    }

    fun testOccursTwice() {
        val lines = Files.lines(getTestInputPath()).toList()

        val seen = HashMap<Int, Boolean>()
        var currentSum = 0
        while (true) {
            for (line in lines) {
                val lineAsInt = lineToInt(line)
                currentSum += lineAsInt
                if (seen.containsKey(currentSum)) {
                    check(currentSum == 367)
                    return
                }
                seen[currentSum] = true
            }
        }
    }
}
