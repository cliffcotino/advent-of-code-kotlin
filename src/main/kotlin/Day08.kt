fun main() {

    fun part1(input: List<String>): Long {
        val steps = input[0].trim().toCharArray()
        val graphNodes = input.toGraphNodes()

        val startNode = graphNodes["AAA"]!!
        return calculateStepsToGoal(startNode, steps, graphNodes) { n -> n.id == "ZZZ" }
    }

    fun part2(input: List<String>): Long {
        val steps = input[0].trim().toCharArray()
        val graphNodes = input.toGraphNodes()

        val startNodes = graphNodes.filterKeys { k -> k.endsWith("A") }.map { it.value }.toList()
        val stepsToGoal =
            startNodes.map { startNode ->
                calculateStepsToGoal(startNode, steps, graphNodes) { n -> n.id.endsWith("Z") }
            }

        return lcm(*stepsToGoal.toLongArray())
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    assertEquals(part1(testInput), 2)

    val testInput2 = readInput("Day08_test2")
    assertEquals(part1(testInput2), 6)

    val testInput3 = readInput("Day08_test3")
    assertEquals(part2(testInput3), 6)

    val input = readInput("Day08")
    part1(input).println() // 22411
    part2(input).println() // 11.188.774.513.823
}

private data class GraphNode(val id: String, val left: String, val right: String)

private fun List<String>.toGraphNodes(): Map<String, GraphNode> {
    TODO()
//    return drop(2)
//        .map { s ->
//            graphs.GraphNode(
//                id = s.substringBefore(" = "),
//                left = s.substringBetween("(", ", "),
//                right = s.substringBetween(", ", ")")
//            )
//        }
//        .associateBy { graphNode -> graphNode.id }
}

private fun calculateStepsToGoal(
    startNode: GraphNode,
    steps: CharArray,
    graphNodes: Map<String, GraphNode>,
    endCondition: (GraphNode) -> Boolean
): Long {
    var i = 0L
    var currentNode = startNode
    while (!endCondition.invoke(currentNode)) {
        currentNode = steps.fold(currentNode) { acc, step ->
            when (step) {
                'L' -> graphNodes[acc.left]!!
                'R' -> graphNodes[acc.right]!!
                else -> throw IllegalArgumentException()
            }
        }
        i += steps.size
        if (currentNode.id.endsWith("Z")) {
            return i
        }
    }
    return -1L
}
