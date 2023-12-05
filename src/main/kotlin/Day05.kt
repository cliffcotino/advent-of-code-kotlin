
fun main() {

    fun part1(input: List<String>): Int {
        val seeds = Seeds(input[0].substringAfter(" ").split(" ").map { it.toInt() })
        val mapping = parseMappings(input.drop(2))
        return mapping.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    assertEquals(part1(testInput), 35)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

private fun parseMappings(mappingContent: List<String>): List<Mapping> {
    val maps = mutableListOf<Mapping>()
    var i = 0
    while (i < mappingContent.size) {
        val name = mappingContent[i++]
        val mappingRanges = mutableListOf<Mapping.MappingRange>()
        while (i < mappingContent.size && mappingContent[i].matches(Regex("\\d.*"))) {
            val line = mappingContent[i].trim().split(" ")
            mappingRanges.add(
                Mapping.MappingRange(
                    destinationRangeStart = line[0].toInt(),
                    sourceRangeStart = line[1].toInt(),
                    rangeLength = line[2].toInt()
                )
            )
            i++
        }
        maps.add(
            Mapping(
                sourceCategory = name.substringBefore("-to-"),
                destinationCategory = name.substringAfter("-to-").substringBefore(" map:"),
                ranges = mappingRanges
            )
        )
        i++
    }
    return maps
}

private data class Seeds(val seeds: List<Int>)

private data class Mapping(
    val sourceCategory: String,
    val destinationCategory: String,
    val ranges: List<MappingRange>
) {
    data class MappingRange(
        val destinationRangeStart: Int,
        val sourceRangeStart: Int,
        val rangeLength: Int
    )
}

