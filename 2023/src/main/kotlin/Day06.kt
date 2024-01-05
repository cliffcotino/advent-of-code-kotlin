fun main() {

    fun part1(input: List<String>): Long {
        val timesAvailable = input[0].substringAfter("Time:").splitToLongs()
        val distancesToCover = input[1].substringAfter("Distance:").splitToLongs()
        return productOfWinningStrategies(timesAvailable, distancesToCover)
    }

    fun part2(input: List<String>): Long {
        val timesAvailable = input[0].substringAfter("Time:").replace(Regex("\\s+"), "").splitToLongs()
        val distancesToCover = input[1].substringAfter("Distance:").replace(Regex("\\s+"), "").splitToLongs()
        return productOfWinningStrategies(timesAvailable, distancesToCover)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    assertEquals(part1(testInput), 288)

    val input = readInput("Day06")
    part1(input).println() // 140220
    part2(input).println() // 39570185
}

private fun RaceDistance.winningStrategies(): Long {
    var answer: Long = 0
    var chargeTime: Long = 1
    while (chargeTime < timeAvailable) {
        val distanceForChargeTime = speedAfter(chargeTime) * (timeAvailable - chargeTime)
        if (distanceForChargeTime > distanceToCover) {
            answer++
        }
        chargeTime++
    }
    return answer
}

private fun productOfWinningStrategies(
    timesAvailable: List<Long>,
    distancesToCover: List<Long>
): Long {
    return timesAvailable.mapIndexed { i, t ->
        RaceDistance(
            timeAvailable = t,
            distanceToCover = distancesToCover[i]
        )
    }
        .map { raceDistance -> raceDistance.winningStrategies() }
        .fold(1) { acc, l -> acc * l }
}

private fun speedAfter(chargeTime: Long): Long {
    return chargeTime
}

private data class RaceDistance(val timeAvailable: Long, val distanceToCover: Long)
