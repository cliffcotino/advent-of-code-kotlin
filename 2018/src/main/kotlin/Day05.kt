
class Day05 : Day() {

    fun testPolymersSample() {
        val input = listOf("dabAcCaCBAcCcaDA")[0]
        val answer = react(input)
        check(answer == "dabCBAcaDA")
    }

    private fun react(input: String): String {
        val reacted = input

        val current = input
        do {
            val chars = current.toCharArray()

            var i = 0
            while (i < chars.size) {
                val char = chars[i]
                val nextChar = if (chars.size <= i) chars[i + 1] else '_'

                if (matches(char, nextChar)) {
                    i += 0
                }
            }

        } while (reacted != current)

        return reacted
    }

    private fun matches(current: Char, next: Char): Boolean {
        return ((current.isLowerCase() && next.isUpperCase() && current == next.lowercaseChar())
                || (current.isUpperCase() && next.isLowerCase() && current == next.uppercaseChar()))
    }
}