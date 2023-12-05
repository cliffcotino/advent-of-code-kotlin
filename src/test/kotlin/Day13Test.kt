import Day13Test.Packet.PacketList
import Day13Test.Packet.PacketValue
import PacketsParser.ListContext
import PacketsParser.ValueContext
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test : DayTemplate() {

    data class Data(val packets: Any) {
        val index: Int
            get() = 0
    }

    sealed interface Packet {
        data class PacketList(val packets: List<Packet>) : Packet

        data class PacketValue(val value: Long) : Packet
    }

    private fun String.toDomain(): Any {
        println("parsing: '$this'")
        val input = CharStreams.fromString(this)
        val lexer = PacketsLexer(input)
        val tokens = CommonTokenStream(lexer)
        val parser = PacketsParser(tokens)
        val tree: ListContext = parser.list()
        val packetList = traverse(tree)
        println("packetList: $packetList")
        return packetList
    }

    private fun traverse(listContext: ListContext): Packet {
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
            .map { chunk ->
                val left = Data(chunk[0].toDomain())
                val right = Data(chunk[1].toDomain())
                left to right
            }
            .filter { areInCorrectOrder(it.first, it.second) }
            .sumOf { it.first.index }

    private fun areInCorrectOrder(left: Data, right: Data): Boolean {
        TODO("Not yet implemented")
    }

    fun test2(file: String): Int = readLines(file).map { it.toDomain() }.count()

    @Test
    fun `test1 sample`() {
        val actual = test1("sample")
        assertEquals(-1, actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = test2("sample")
        assertEquals(-1, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = test1("input")
        assertEquals(-1, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = test2("input")
        assertEquals(-1, actual)
    }
}
