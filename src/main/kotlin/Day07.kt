fun main() {
    fun part1(input: List<String>): Long {
        val camelHands = input.map { s -> s.toCamelHand() }.sortedWith(comparatorForPart1())
        return camelHands.mapIndexed(winningsForHand()).sum()
    }

    fun part2(input: List<String>): Long {
        val camelHands = input.map { s -> s.toCamelHand() }.sortedWith(comparatorForPart2())
        return camelHands.mapIndexed(winningsForHand()).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    assertEquals(part1(testInput), 6440)
    assertEquals(part2(testInput), 5905)

    val input = readInput("Day07")
    part1(input).println() // 251927063
    part2(input).println() // 255632664
}

private fun winningsForHand(): (Int, CamelHand) -> Long {
    return { i: Int, hand: CamelHand -> hand.bid * (i + 1) }
}

private fun comparatorForPart1(): Comparator<CamelHand> {
    return comparatorFor(
            handTypeExtractor = { hand -> hand.type },
            cardValueExtractor = { card -> card.ordinal }
        )
        .reversed()
}

private fun comparatorForPart2(): Comparator<CamelHand> {
    return comparatorFor(
            handTypeExtractor = { hand -> hand.optimalHand.type },
            cardValueExtractor = { card ->
                if (card == CamelCard.J) {
                    CamelCard.TWO.ordinal + 1
                } else {
                    card.ordinal
                }
            }
        )
        .reversed()
}

private fun comparatorFor(
    handTypeExtractor: (CamelHand) -> HandType,
    cardValueExtractor: (CamelCard) -> Int
): Comparator<CamelHand> =
    object : Comparator<CamelHand> {
        override fun compare(o1: CamelHand, o2: CamelHand): Int {
            val handType1 = handTypeExtractor.invoke(o1)
            val handType2 = handTypeExtractor.invoke(o2)
            val comparedByType = handType1.compareTo(handType2)
            if (comparedByType == 0) {
                for (i in o1.cards.indices) {
                    val cardValue1 = cardValueExtractor.invoke(o1.cards[i])
                    val cardValue2 = cardValueExtractor.invoke(o2.cards[i])
                    val comparedByCard = cardValue1.compareTo(cardValue2)
                    if (comparedByCard == 0) {
                        continue
                    } else {
                        return comparedByCard
                    }
                }
                return 0
            } else {
                return comparedByType
            }
        }
    }

private data class CamelHand(val cards: List<CamelCard>, val bid: Long) {

    val type: HandType by lazy {
        HandType.entries.first { type ->
            val groupedByCard: Map<CamelCard, Int> =
                cards.groupBy { it }.mapValues { l -> l.value.size }
            type.matches(groupedByCard)
        }
    }

    val optimalHand: CamelHand by lazy {
        if (cards.none { card -> card == CamelCard.J }) {
            this
        } else {
            val jokerIndexes =
                cards.mapIndexedNotNull { index, elem -> index.takeIf { elem == CamelCard.J } }
            val permutations = (CamelCard.entries.toList()).permutations(jokerIndexes.size)
            permutations
                .map { permutation -> CamelHand(cards = cards.combineWith(jokerIndexes, permutation), bid = bid) }
                .sortedWith(comparatorForPart1().reversed())
                .first()
        }
    }
}

private fun List<CamelCard>.combineWith(jokerIndexes: List<Int>, permutation: List<CamelCard>): List<CamelCard> {
    val answer = this.toMutableList()
    for (i in permutation.indices) {
        val jokerIndex = jokerIndexes[i]
        answer[jokerIndex] = permutation[i]
    }
    return answer
}

private fun String.toCamelHand(): CamelHand {
    val split = split(" ")
    return CamelHand(cards = split[0].toCards(), bid = split[1].toLong())
}

private fun String.toCards(): List<CamelCard> {
    return toCharArray().map { c -> CamelCard.toCard(c) }
}

private enum class HandType(private val predicate: (Map<CamelCard, Int>) -> Boolean) {
    FIVE_OF_A_KIND({ map -> map.valuesList() == listOf(5) }),
    FOUR_OF_A_KIND({ map -> map.valuesList() == listOf(4, 1) }),
    FULL_HOUSE({ map -> map.valuesList() == listOf(3, 2) }),
    THREE_OF_A_KIND({ map -> map.valuesList() == listOf(3, 1, 1) }),
    TWO_PAIR({ map -> map.valuesList() == listOf(2, 2, 1) }),
    ONE_PAIR({ map -> map.valuesList() == listOf(2, 1, 1, 1) }),
    HIGH_CARD({ true }); // the default

    fun matches(map: Map<CamelCard, Int>): Boolean = predicate.invoke(map)
}

private fun Map<CamelCard, Int>.valuesList(): List<Int> = this.values.toList().sortedDescending()

private enum class CamelCard(val char: Char) {
    A('A'),
    K('K'),
    Q('Q'),
    J('J'),
    T('T'),
    NINE('9'),
    EIGHT('8'),
    SEVEN('7'),
    SIX('6'),
    FIVE('5'),
    FOUR('4'),
    THREE('3'),
    TWO('2');

    companion object {
        fun toCard(char: Char) = CamelCard.entries.first { it.char == char }
    }
}
