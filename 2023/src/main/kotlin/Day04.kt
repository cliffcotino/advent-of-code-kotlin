import kotlin.math.pow

fun main() {

    fun String.toCardGame(): CardGame {
        return CardGame(
            id = substringBetween("Card ", ":").trim().toInt(),
            winningNumbers = substringAfter(":").substringBefore("|").splitToInts(),
            ownNumbers = substringAfter("|").splitToInts()
        )
    }

    fun part1(input: List<String>): Int {
        return input
            .map { s -> s.toCardGame() }
            .sumOf { it.cardsCorrectPoints() }
    }

    fun part2(input: List<String>): Int {
        val cardGames = input.map { s -> s.toCardGame() }
        val runningTally = MutableList (cardGames.size) { 1 }
        for (c in cardGames.indices) {
            val cardGame = cardGames[c]
            val cardsCorrect = cardGame.cardsCorrect()
            repeat(runningTally[c]) {
                repeat(cardsCorrect.size) { i ->
                    runningTally[c + i + 1] = runningTally[c + i + 1] + 1
                }
            }
        }
        return runningTally.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    assertEquals(part1(testInput), 13)
    assertEquals(part2(testInput), 30)

    val input = readInput("Day04")
    part1(input).println() // 23847
    part2(input).println() // 8570000
}

private data class CardGame(val id: Int, val winningNumbers: List<Int>, val ownNumbers: List<Int>) {

    fun cardsCorrect(): List<Int> = ownNumbers.filter { it in winningNumbers }

    fun cardsCorrectPoints(): Int {
        val correct = cardsCorrect()
        return if (correct.isEmpty()) {
            0
        } else {
            2.0.pow(correct.size.toDouble() - 1).toInt()
        }
    }
}
