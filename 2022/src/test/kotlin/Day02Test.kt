
import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Test : DayTemplate() {

    enum class Hand(val score: Int) {
        Rock(1) {
            override fun losesTo(): Hand = Paper
        },
        Paper(2) {
            override fun losesTo(): Hand = Scissor
        },
        Scissor(3) {
            override fun losesTo(): Hand = Rock
        };

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
        Draw(3),
        Loss(0);

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
                them.losesTo() == me -> Outcome.Win
                them == me -> Outcome.Draw
                else -> Outcome.Loss
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
                Play(them, me)
            }
            .sumOf { it.score() }


    fun test2(file: String): Int =
        readLines(file)
            .map { it.split(" ") }
            .map {
                val them = Hand.from(it[0])
                val me = when (Outcome.from(it[1])) {
                    Outcome.Win -> them.losesTo()
                    Outcome.Loss -> them.losesTo().losesTo()
                    Outcome.Draw -> them
                }
                Play(them, me)
            }
            .sumOf { it.score() }

    @Test
    fun `test1 sample`() {
        val actual = test1("sample")
        assertEquals(15, actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = test2("sample")
        assertEquals(12, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = test1("input")
        assertEquals(14375, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = test2("input")
        assertEquals(10274, actual)
    }
}
