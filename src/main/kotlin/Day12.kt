import kotlin.math.abs

class Day12 : Day() {

    data class Node(val x: Int, val y: Int, val char: Char) {
        fun isStart() = char == 'S'
        fun isEnd() = char == 'E'
        private fun elevation(): Int = "abcdefghijklmnopqrstuvwxyz".indexOf(
            when (char) {
                'S' -> 'a'
                'E' -> 'z'
                else -> char
            }
        )

        fun reachable(other: Node): Boolean {
            val isNeighbour = (abs(other.x - x) + abs(other.y - y)) == 1
            val isAtMostOneHigher = other.elevation() <= elevation() + 1
            return isNeighbour && isAtMostOneHigher
        }
    }

    data class Edge(val source: Node, val target: Node)

    class Graph {
        val nodes: MutableList<Node> = mutableListOf()
        val edges: MutableList<Edge> = mutableListOf()

        fun findRoute(start: Node, end: Node): List<Node> {
            TODO("Not yet implemented")
        }

        fun findEdge(source: Node, target: Node): Edge? =
            edges.firstOrNull { edge -> edge.source == source && edge.target == target }
    }

    fun test1(file: String): Int {
        val graph = parseGraph(file)
        val start = graph.nodes.first { it.isStart() }
        val end = graph.nodes.first { it.isEnd() }

        val route = graph.findRoute(start = start, end = end)
        return route.size
    }

    private fun parseGraph(file: String): Graph {
        val nodes = readLines(file)
            .flatMapIndexed { lineIndex, line ->
                line.mapIndexed { charIndex, char -> Node(x = lineIndex, y = charIndex, char = char) }
            }
        val graph = Graph()
        graph.nodes.addAll(nodes)

        nodes.forEach { n1 ->
            nodes.forEach { n2 ->
                if (n1 != n2 && n1.reachable(n2)) {
                    graph.edges.add(Edge(n1, n2))
                }
            }
        }

        return graph
    }

    fun test2(file: String): Int {
        return readLines(file)
            .count()
    }

}
