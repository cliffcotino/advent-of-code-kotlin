
import org.junit.jupiter.api.Disabled
import java.lang.IllegalArgumentException
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test : DayTemplate() {

    sealed class Operation {
        abstract fun execute(clock: AtomicInteger, register: AtomicInteger, measure: () -> Unit)

        data class Add(val x: Int) : Operation() {
            override fun execute(clock: AtomicInteger, register: AtomicInteger, measure: () -> Unit) {
                measure()
                clock.addAndGet(1)
                measure()

                register.addAndGet(x)

                measure()
                clock.addAndGet(1)
                measure()
            }
        }
        object Noop : Operation() {
            override fun execute(clock: AtomicInteger, register: AtomicInteger, measure: () -> Unit) {
                measure()
                clock.addAndGet(1)
                measure()
            }

            override fun toString(): String {
                return "Noop"
            }
        }
    }

    private fun String.toOperation(): Operation =
        when {
            this == "noop" -> Operation.Noop
            this.startsWith("addx") -> Operation.Add(this.substringAfter("addx ").toInt())
            else -> throw IllegalArgumentException()
        }

    fun test1(file: String): Int {
        val operations = readLines(file)
            .map { it.toOperation() }

        val register = AtomicInteger(1)
        val sum = AtomicInteger(0)
        val cycles = listOf(20, 60, 100, 140, 180, 220)
        val clock = AtomicInteger(1)
        val calculatedAt = mutableSetOf<Int>()

        val measure: () -> Unit = {
            val currentCycle = clock.get()
            if (currentCycle in cycles && !calculatedAt.contains(currentCycle)) {
                // println("calculating signal strength at cycle=$currentCycle - register=${register.get()}")
                sum.addAndGet(getSignalStrength(clock, register))
                calculatedAt.add(currentCycle)
            }
        }

        operations.forEach { operation ->
            // println("cycle=$clock x=$register - operation $operation")
            when (operation) {
                is Operation.Add -> operation.execute(clock, register, measure)
                Operation.Noop -> operation.execute(clock, register, measure)
            }
        }

        return sum.get()
    }

    private fun getSignalStrength(currentCycle: AtomicInteger, register: AtomicInteger) = currentCycle.get() * register.get()

    fun test2(file: String): Int {
        val operations = readLines(file)
            .map { it.toOperation() }

        val register = AtomicInteger(1)
        val clock = AtomicInteger(1)
        val drawSymbol = Array(240) { '.' }
        val calculatedAt = mutableSetOf<Int>()

        val draw: () -> Unit = {
            val currentCycle = clock.get()
            if (!calculatedAt.contains(currentCycle)) {
                // println("calculating pixel at cycle=$currentCycle - register=${register.get()}")
                val spritePosition = register.get() + 1
                val currentIndex = clock.get() % 40
                if (currentIndex in (spritePosition - 1..spritePosition + 1)) {
                    drawSymbol[clock.get() - 1] = '#'
                }
                calculatedAt.add(currentCycle)
            }
        }

        operations.forEach { operation ->
            // println("cycle=$clock x=$register - operation $operation")
            when (operation) {
                is Operation.Add -> operation.execute(clock, register, draw)
                Operation.Noop -> operation.execute(clock, register, draw)
            }
        }

        drawSymbol.forEachIndexed { i, char ->
            print(char)
            if ((i + 1) % 40 == 0) {
                println()
            }
        }
        return 0
    }

    @Test
    fun `test1 sample`() {
        val actual = test1("sample")
        assertEquals(0, actual)
    }

    @Test
    fun `test1 sample-larger`() {
        val actual = test1("sample-larger")
        assertEquals(13140, actual)
    }

    @Test
    @Disabled
    fun `test2 sample`() {
        val actual = test2("sample-larger")
        assertEquals(-1, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = test1("input")
        assertEquals(14920, actual)
    }

    @Test
    @Disabled
    fun `test2 actual`() {
        val actual = test2("input")
        assertEquals(-1, actual)
    }
}
