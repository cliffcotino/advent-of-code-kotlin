fun main() {

    fun part1(input: List<String>): Int {
        return input.first().split(",").sumOf { it.hash() }
    }

    fun part2(input: List<String>): Int {
        val boxes = (0 until 256).associateWith { mutableListOf<Lens>() }

        input.first().split(",").forEach {
            val label = it.substringBefore("=").substringBefore("-")
            val labelHash = label.hash()
            val box = boxes[labelHash]!!
            println("Box $label -> $labelHash: ${box.prettyPrint()}")
            when {
                it.contains("=") -> {
                    val lens = Lens(label = label, focalLength = it.substringAfter("=").toInt())
                    val index = box.indexOfFirst { boxLens -> boxLens.label == lens.label }
                    if (index < 0) {
                        box.add(lens)
                    } else {
                        box[index] = lens
                    }
                }
                it.contains("-") -> {
                    box.removeIf { lens -> lens.label == label }
                }
            }
        }

        boxes.values
            .filter { it.isNotEmpty() }
            .forEachIndexed { index, box -> println("Box $index - ${box.prettyPrint()}") }

        return boxes.values.mapIndexed { index, box -> (index + 1) * box.focusingPower() }.sum()
    }

    assertEquals(part1(listOf("HASH")), 52)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    assertEquals(part1(testInput), 1320)
    assertEquals(part2(testInput), 145)

    val input = readInput("Day15")
    part1(input).println() // 510388
    part2(input).println() // 291774
}

private fun String.hash(): Int {
    val letters = substringBefore(",").toCharArray()
    return letters.fold(0) { acc, char -> ((acc + char.code) * 17) % 256 }
}

private fun List<Lens>.focusingPower(): Int {
    return mapIndexed { index, lens ->
            val power = (index + 1) * lens.focalLength
            // println("Power for ${lens.label} ${lens.focalLength} -> $power")
            power
        }
        .sum()
}

private fun List<Lens>.prettyPrint(): String {
    return joinToString(" ") { "[${it.label} ${it.focalLength}]" }
}

private data class Lens(val label: String, val focalLength: Int)
