fun main() {

    fun part1(input: List<String>): Long {
        val mappings = input.drop(2).toMappings()
        val seedRanges = input[0].substringAfter(" ").splitToLongs()
            .map { long -> SeedRange(long, 1) }
        return getLowestLocation(seedRanges, mappings)
    }

    fun part2(input: List<String>): Long {
        val mappings = input.drop(2).toMappings()
        val seedRanges = input[0].substringAfter(" ").splitToLongs()
            .chunked(2)
            .map { pair -> SeedRange(pair[0], pair[1]) }
        return getLowestLocation(seedRanges, mappings)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    assertEquals(part1(testInput), 35)
    assertEquals(part2(testInput), 46)

    val input = readInput("Day05")
    part1(input).println() // 993500720
    // part2(input).println() // ?
}

private fun List<String>.toMappings(): List<Mapping> {
    val maps = mutableListOf<Mapping>()
    var i = 0
    while (i < size) {
        val name = this[i++]
        val mappingRanges = mutableListOf<Mapping.MappingRange>()
        while (i < size && this[i].matches(Regex("\\d.*"))) {
            val split = this[i].splitToLongs()
            mappingRanges.add(
                Mapping.MappingRange(
                    destinationRangeStart = split[0],
                    sourceRangeStart = split[1],
                    rangeLength = split[2]
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

private fun getLowestLocation(seedRanges: List<SeedRange>, mappings: List<Mapping>): Long {
    val minima = mutableListOf<Long>()
    seedRanges.forEach { seedRange ->
        val movedSeedRanges = seedRange.moveAccordingTo(mappings)
        val minOfSeedRanges = movedSeedRanges.minOf { seedRange.start }
        println("minOfSeedRange: $minOfSeedRanges")
        minima.add(minOfSeedRanges)
    }
    val minimum = minima.min()
    println("min: $minimum")
    return minimum
}

private fun SeedRange.moveAccordingTo(mappings: List<Mapping>): List<SeedRange> {
    var mappedSeeds = listOf(this)
    for (mapping in mappings) {
        // mappedSeeds = mapping.applyTo(mappedSeeds)
    }
    return mappedSeeds
}

private data class SeedRange(val start: Long, val length: Long) {
    val end: Long
        get() = start + length
}

private data class Mapping(
    val sourceCategory: String,
    val destinationCategory: String,
    val ranges: List<MappingRange>
) {
    fun applyTo(seeds: List<Long>): List<Long> {
        return (seeds).map { i ->
            val moved = applyMappings(i)
            moved ?: i
        }
    }

    private fun applyMappings(value: Long): Long? {
        for (r in ranges) {
            if (value >= r.sourceRangeStart && value < r.sourceRangeStart + r.rangeLength) {
                val distanceFromStart = value - r.sourceRangeStart
                return (r.destinationRangeStart + distanceFromStart)
            }
        }
        return null
    }

    data class MappingRange(
        val destinationRangeStart: Long,
        val sourceRangeStart: Long,
        val rangeLength: Long
    )
}

