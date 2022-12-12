import java.lang.IllegalArgumentException
import java.util.concurrent.atomic.AtomicInteger

class Day10 : Day() {

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
                println("calculating signal strength at cycle=$currentCycle - register=${register.get()}")
                sum.addAndGet(getSignalStrength(clock, register))
                calculatedAt.add(currentCycle)
            }
        }

        operations.forEach { operation ->
            println("cycle=$clock x=$register - operation $operation")
            when (operation) {
                is Operation.Add -> operation.execute(clock, register, measure)
                Operation.Noop -> operation.execute(clock, register, measure)
            }
        }

        return sum.get()
    }

    private fun getSignalStrength(currentCycle: AtomicInteger, register: AtomicInteger) = currentCycle.get() * register.get()

    fun test2(file: String): Int =
        readLines(file)
            .count()

}
