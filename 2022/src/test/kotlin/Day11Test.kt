
import mu.KotlinLogging
import java.util.concurrent.atomic.LongAdder
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test : DayTemplate() {

    companion object {
        val logger = KotlinLogging.logger {}
    }

    class Monkeys(private val monkeys: List<Monkey>) {

        fun getMonkeyBusiness(): Long {
            val sortedMonkeys = monkeys.sortedByDescending { monkey -> monkey.inspected }
            val busyMonkey1 = sortedMonkeys[0]
            val busyMonkey2 = sortedMonkeys[1]
            logger.info("Busy Monkey 1: ${busyMonkey1.monkeyNumber} - ${busyMonkey1.inspected}")
            logger.info("Busy Monkey 2: ${busyMonkey2.monkeyNumber} - ${busyMonkey2.inspected}")
            return busyMonkey1.inspected.toLong() * busyMonkey2.inspected.toLong()
        }

        fun playRound(crazyWorryLevels: Boolean = false) {
            val worryDivider = monkeys.fold(1) { acc, monkey -> acc * monkey.divisibleBy }
            monkeys.forEach { monkey ->
                logger.info("Turn for monkey ${monkey.monkeyNumber}")

                val afterInspection = { worryLevel: Long ->
                    if (crazyWorryLevels) {
                        worryLevel % worryDivider
                    } else {
                        worryLevel / 3
                    }
                }

                val passToMonkey = { other: String, item: Long ->
                    val otherMonkey = monkeys.first { it.monkeyNumber == other }
                    otherMonkey.items.add(item)
                    Unit
                }

                monkey.takeTurn(afterInspection, passToMonkey)
                logger.info("")
            }
        }
    }

    data class Monkey(
        val monkeyNumber: String,
        val items: MutableList<Long>,
        val operation: Operation,
        val divisibleBy: Int,
        val passToMonkeyIfTrue: String,
        val passToMonkeyIfFalse: String
    ) {

        private val itemsInspected = LongAdder()
        val inspected: Int
            get() = itemsInspected.toInt()
        fun takeTurn(doAfterInspection: (Long) -> Long,
                     passToMonkey: (String, Long) -> Unit) {
            items.forEach { item ->
                logger.info("Inspecting item $item")

                val worryLevel = operation.execute(item)
                logger.info("Worry level $worryLevel")

                val afterInspection = doAfterInspection(worryLevel)
                logger.info("After inspection $afterInspection")

                val otherMonkey = if (afterInspection % divisibleBy == 0L) {
                    logger.info("Divisible by $divisibleBy - passing to monkey $passToMonkeyIfTrue")
                    passToMonkeyIfTrue
                } else {
                    logger.info("Not divisible by $divisibleBy - passing to monkey $passToMonkeyIfFalse")
                    passToMonkeyIfFalse
                }

                logger.info("Passing item $afterInspection to monkey $otherMonkey")
                passToMonkey.invoke(otherMonkey, afterInspection)

                itemsInspected.add(1)
            }
            items.clear()
        }
    }

    sealed interface Operation {

        fun execute(value: Long): Long

        data class Add(private val base: Int) : Operation {
            override fun execute(value: Long): Long = base + value
        }

        data class Multiply(private val base: Int) : Operation {
            override fun execute(value: Long): Long = base * value
        }
        object Square : Operation {
            override fun execute(value: Long): Long = value * value

            override fun toString(): String {
                return "Square"
            }
        }
    }

    private fun Iterator<String>.parse() =
        next().trim().substringAfter(": ")

    private fun String.toOperation(): Operation =
        when {
            this == "old * old" -> Operation.Square
            this.startsWith("old + ") -> Operation.Add(this.substringAfter("+").trim().toInt())
            this.startsWith("old * ") -> Operation.Multiply(this.substringAfter("*").trim().toInt())
            else -> throw IllegalArgumentException("Unsupported operation to parse '$this'")
        }

    fun test1(file: String): Long {
        val monkeys = Monkeys(parseMonkeys(file))

        repeat(20) { round ->
            logger.info("Playing round $round")
            monkeys.playRound(crazyWorryLevels = false)
            logger.info("")
        }

        return monkeys.getMonkeyBusiness()
    }

    private fun parseMonkeys(file: String): MutableList<Monkey> {
        val parsed = mutableListOf<Monkey>()
        val lines = readLines(file).iterator()
        while (lines.hasNext()) {
            val number = lines.parse()
            val startingItems = lines.parse()
            val operation = lines.parse()
            val test = lines.parse()
            val ifTrue = lines.parse()
            val ifFalse = lines.parse()
            val monkey = Monkey(
                number.substring(number.indexOf(' '), number.indexOf(':')).trim(),
                startingItems.split(", ").map { it.trim().toLong() }.toMutableList(),
                operation.substring(operation.indexOf("=") + 1).trim().toOperation(),
                test.substringAfter("divisible by").trim().toInt(),
                ifTrue.substringAfter("monkey").trim(),
                ifFalse.substringAfter("monkey").trim()
            )
            logger.info("Adding Monkey $monkey")
            parsed.add(monkey)
            if (lines.hasNext()) {
                lines.next()
            } else {
                logger.info("No next monkey")
                break
            }
        }
        return parsed
    }

    fun test2(file: String, rounds: Int = 10_000): Long {

        val monkeys = Monkeys(parseMonkeys(file))

        repeat(rounds) { round ->
            logger.info("Playing round $round")
            monkeys.playRound(crazyWorryLevels = true)
            logger.info("")
        }

        return monkeys.getMonkeyBusiness()
    }

    @Test
    fun `test1 sample`() {
        val actual = test1("sample")
        assertEquals(10_605, actual)
    }

    @Test
    fun `test2 smaller sample`() {
        val actual = test2("sample", rounds = 20)
        assertEquals(10_197, actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = test2("sample")
        assertEquals(2_713_310_158L, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = test1("input")
        assertEquals(51075, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = test2("input")
        assertEquals(11_741_456_163, actual)
    }
}
