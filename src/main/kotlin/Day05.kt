import java.util.*

class Day05 : Day() {

    data class Stack(val crates: Deque<Char> = LinkedList()) {
        fun top(): Char? =
            if (crates.isEmpty()) null else crates.last
    }

    class Stacks(private val stacks: Array<Stack> = Array(10) { Stack() }) {
        fun moveOneByOne(move: Move) {
            val source = stacks[move.from - 1]
            val target = stacks[move.to - 1]
            repeat(move.amount) {
                target.crates.addLast(source.crates.removeLast())
            }
        }

        fun moveMultiple(move: Move) {
            val source = stacks[move.from - 1]
            val target = stacks[move.to - 1]
            val removed = (1..move.amount)
                .map { source.crates.removeLast() }

            removed.reversed().forEach {
                target.crates.addLast(it)
            }
        }

        fun addToStack(char: Char, stack: Int) {
            stacks[stack].crates.push(char)
        }

        fun topOfStacks(): String =
            stacks.mapNotNull { it.top() }
                .joinToString("", transform = Char::toString)
    }

    data class Move(val amount: Int, val from: Int, val to: Int)

    private fun parseStacks(iterator: Iterator<String>): Stacks {
        val stacks = Stacks()
        while (iterator.hasNext()) {
            val line = iterator.next()
            if (!line.contains("[")) {
                // ignore the line with only the stack numbers
                break
            }

            // 4 is the length of a string like: "[A] "
            line.chunked(4).forEachIndexed { idx, part ->
                if (part.isNotBlank()) {
                    val crate = part.substring(part.indexOf('[') + 1, part.indexOf(']'))
                    check(crate.length == 1)
                    stacks.addToStack(crate[0], idx)
                }
            }
        }
        return stacks
    }

    private fun parseMoves(iterator: Iterator<String>): List<Move> {
        val regex = Regex("""move (\d+) from (\d+) to (\d+)""")
        val moves = mutableListOf<Move>()
        while (iterator.hasNext()) {
            val line = iterator.next()
            regex.find(line)?.let {
                val (amount, from, to) = it.destructured
                moves.add(Move(amount.toInt(), from.toInt(), to.toInt()))
            }
        }
        return moves
    }

    fun test1(file: String): String {
        val iter = readLines(file).iterator()
        val stacks = parseStacks(iter)
        parseMoves(iter).forEach { move ->
            stacks.moveOneByOne(move)
        }
        return stacks.topOfStacks()
    }


    fun test2(file: String): String {
        val iter = readLines(file).iterator()
        val stacks = parseStacks(iter)
        parseMoves(iter).forEach { move ->
            stacks.moveMultiple(move)
        }
        return stacks.topOfStacks()
    }

}
