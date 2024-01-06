import java.nio.file.Files
import kotlin.streams.toList

class Day02 : Day() {

    private fun Boolean.toInt() = if (this) 1 else 0

    fun testCheckSum() {
        val reduce = Files.lines(getTestInputPath())
                .map { lineToFlags(it) }
                .filter { it[0] || it[1] }
                .map { intArrayOf(it[0].toInt(), it[1].toInt()) }
                .reduce(intArrayOf(0, 0))
                    { a, b -> intArrayOf(a[0] + b[0], a[1] + b[1]) }
        val answer = reduce[0] * reduce[1]
        check(answer == 5500)
    }

    private fun lineToFlags(line: String): BooleanArray {
        val countByCharacter = line.split("")
                .groupingBy { it -> it }
                .eachCount()
                .values
        return booleanArrayOf(countByCharacter.contains(3), countByCharacter.contains(2))
    }

    fun testWordSimilarity() {
        val words = Files.lines(getTestInputPath()).toList()

        for (word1 in words) {
            for (word2 in words) {
                if (difference(word1, word2) == 1) {
                    val answer = word1.split("")
                            .filter { word2.contains(it) }
                            .joinToString("")
                    check(answer == "qyzphxoiseldjrntfygvdmanu")
                    return
                }
            }
        }
    }

    private fun difference(word1: String, word2: String): Int {
        var diff = 0
        val chars1 = word1.toCharArray()
        val chars2 = word2.toCharArray()
        for (i in chars1.indices) {
            for (j in chars2.indices) {
                if (i == j && chars1[i] != chars2[j]) {
                    diff++
                }
            }
        }
        return diff
    }

}
