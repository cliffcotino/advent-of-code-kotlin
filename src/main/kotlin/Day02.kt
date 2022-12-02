import Day02.Outcome.*

class Day02 : Day() {

    enum class Hand(val score: Int) {
        Rock(1) {
            override fun winsFrom(): Hand = Scissor
            override fun losesTo(): Hand = Paper
        },
        Paper(2) {
            override fun winsFrom(): Hand = Rock
            override fun losesTo(): Hand = Scissor
        },
        Scissor(3) {
            override fun winsFrom(): Hand = Paper
            override fun losesTo(): Hand = Rock
        };

        abstract fun winsFrom(): Hand
        abstract fun losesTo(): Hand

        companion object {
            fun from(value: String): Hand =
                when (value) {
                    "A", "X" -> Rock
                    "B", "Y" -> Paper
                    "C", "Z" -> Scissor
                    else -> throw IllegalArgumentException("Unsupported $value")
                }
        }
    }

    enum class Outcome(val score: Int) {
        Win(6),
        Loss(0),
        Draw(3);

        companion object {
            fun from(value: String): Outcome =
                when (value) {
                    "X" -> Loss
                    "Y" -> Draw
                    "Z" -> Win
                    else -> throw IllegalArgumentException("Unsupported $value")
                }
        }
    }

    data class Play(val them: Hand, val me: Hand) {
        fun score(): Int {
            val base = me.score
            val outcome = when {
                them.losesTo() == me -> Win
                them == me -> Draw
                else -> Loss
            }
            return base + outcome.score
        }
    }

    fun test1(file: String): Int =
        readLines(file)
            .map { it.split(" ") }
            .map {
                val them = Hand.from(it[0])
                val me = Hand.from(it[1])
                Play(them = them, me = me)
            }
            .sum()

    private fun List<Play>.sum() =
        fold(0) { acc, play -> acc + play.score() }

    fun test2(file: String): Int =
        readLines(file)
            .map { it.split(" ") }
            .map {
                val them = Hand.from(it[0])
                val me = when (Outcome.from(it[1])) {
                    Win -> them.losesTo()
                    Loss -> them.winsFrom()
                    Draw -> them
                }
                Play(them = them, me = me)
            }
            .sum()

}