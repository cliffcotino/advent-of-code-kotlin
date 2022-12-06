import java.util.*

class Day05 : Day() {

    data class Stack(val crates: Deque<Char> = LinkedList()) {
        fun top(): Char? =
            if (crates.isEmpty()) null else crates.last
    }

    class Stacks(val stacks: Array<Stack> = Array(10) { Stack() }) {
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
    }

    data class Move(val amount: Int, val from: Int, val to: Int)

    private fun parseStacks(iterator: Iterator<String>): Stacks {
        val stacks = Stacks()
        while (iterator.hasNext()) {
            val line = iterator.next()
            if (line.replace(" ", "").toIntOrNull() != null) {
                break
            }

            line.chunked(4).forEachIndexed { idx, part ->
                if (part.isNotBlank()) {
                    val crate = part.substring(part.indexOf('[') + 1, part.indexOf(']'))
                    val chars = crate.toCharArray()
                    check(chars.size == 1)
                    stacks.addToStack(chars[0], idx)
                }
            }
        }
        return stacks
    }

    private fun parseMoves(iterator: Iterator<String>): List<Move> {
        val moves = mutableListOf<Move>()
        while (iterator.hasNext()) {
            val line = iterator.next()
            if (line.isNotBlank()) {
                val regex = Regex("""move (\d+) from (\d+) to (\d+)""")
                val (amount, from, to) = regex.find(line)!!.destructured
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
        return stacks.stacks.getTops()
    }

    private fun Array<Stack>.getTops(): String =
        mapNotNull { it.top() }
            .joinToString("") { it.toString() }

    fun test2(file: String): String {
        val iter = readLines(file).iterator()
        val stacks = parseStacks(iter)
        parseMoves(iter).forEach { move ->
            stacks.moveMultiple(move)
        }
        return stacks.stacks.getTops()
    }

}
