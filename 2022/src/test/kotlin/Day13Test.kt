import Day13Test.Packet.PacketList
import Day13Test.Packet.PacketValue
import PacketsParser.ListContext
import PacketsParser.ValueContext
import java.lang.IllegalStateException
import kotlin.test.Test
import kotlin.test.assertEquals
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

class Day13Test : DayTemplate() {

    data class Data(val index: Int, val packets: Packet)

    sealed interface Packet {
        data class PacketList(val packets: List<Packet>) : Packet

        data class PacketValue(val value: Long) : Packet
    }

    private fun String.toDomain(): PacketList {
        val input = CharStreams.fromString(this)
        val lexer = PacketsLexer(input)
        val tokens = CommonTokenStream(lexer)
        val parser = PacketsParser(tokens)
        val tree: ListContext = parser.list()
        return traverse(tree)
    }

    private fun traverse(listContext: ListContext): PacketList {
        return PacketList(listContext.value().map { traverse(it) })
    }

    private fun traverse(valueContext: ValueContext): Packet {
        return when {
            valueContext.INT() != null -> {
                PacketValue(value = valueContext.INT().text.toLong())
            }
            valueContext.list() != null -> {
                // recursion!
                traverse(valueContext.list())
            }
            else -> {
                throw IllegalArgumentException("ValueContext was neither a list nor a value")
            }
        }
    }

    fun test1(file: String): Int =
        readLines(file)
            .chunked(3)
            .mapIndexed { i, chunk ->
                val left = Data(index = i + 1, packets = chunk[0].toDomain())
                val right = Data(index = i + 1, packets = chunk[1].toDomain())
                left to right
            }
            .filter { areInCorrectOrder(it.first.packets, it.second.packets) == Vote.YAY }
            .sumOf { it.first.index }

    fun test2(file: String): Int {
        val parsedPackets = readLines(file)
            .chunked(3)
            .flatMap { chunk -> listOf(chunk[0].toDomain(), chunk[1].toDomain()) }

        val dividerPackage1 = dividerPackage(2L)
        val dividerPackage2 = dividerPackage(6L)
        val sortedPackets =
            (parsedPackets + dividerPackage1 + dividerPackage2)
                .sortedWith { o1: PacketList, o2: PacketList ->
                    val vote = areInCorrectOrder(o1, o2)
                    when (vote) {
                        Vote.YAY -> -1
                        Vote.NAY -> 1
                        Vote.MAY -> 0
                    }
                }
        println(sortedPackets.joinToString("\n"))
        val indexOfDividerPackage1 = sortedPackets.indexOf(dividerPackage1) + 1
        val indexOfDividerPackage2 = sortedPackets.indexOf(dividerPackage2) + 1
        return indexOfDividerPackage1 * indexOfDividerPackage2
    }

    private fun dividerPackage(value: Long) =
        PacketList(packets = listOf(PacketList(packets = listOf(PacketValue(value = value)))))

    private fun areInCorrectOrder(left: Packet, right: Packet): Vote {
        return when {
            left is PacketList && right is PacketList -> {
                if (left.packets.isEmpty() && right.packets.isEmpty()) {
                    Vote.MAY // continue checking
                } else if (left.packets.isEmpty()) { // right.packets.isNotEmpty()
                    Vote.YAY // correct
                } else if (right.packets.isEmpty()) { // left.packets.isNotEmpty()
                    Vote.NAY // incorrect
                } else { // left.packets.isNotEmpty() && right.packets.isNotEmpty())
                    val headsCorrect =
                        areInCorrectOrder(left.packets.first(), right.packets.first())
                    if (headsCorrect == Vote.MAY) {
                        // continue checking
                        areInCorrectOrder(
                            PacketList(packets = left.packets.drop(1)),
                            PacketList(packets = right.packets.drop(1))
                        )
                    } else {
                        headsCorrect
                    }
                }
            }
            left is PacketList && right is PacketValue -> {
                areInCorrectOrder(left, PacketList(packets = listOf(right)))
            }
            left is PacketValue && right is PacketValue -> {
                if (left.value < right.value) {
                    Vote.YAY
                } else if (left.value > right.value) {
                    Vote.NAY
                } else { // left.value == right.value
                    Vote.MAY
                }
            }
            left is PacketValue && right is PacketList -> {
                areInCorrectOrder(PacketList(packets = listOf(left)), right)
            }
            else -> throw IllegalStateException()
        }
    }

    enum class Vote {
        YAY,
        NAY,
        MAY
    }

    @Test
    fun `test1 sample`() {
        val actual = test1("sample")
        assertEquals(13, actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = test2("sample")
        assertEquals(140, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = test1("input")
        assertEquals(5605, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = test2("input")
        assertEquals(24969, actual)
    }
}
