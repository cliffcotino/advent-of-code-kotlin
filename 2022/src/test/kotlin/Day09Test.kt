
import org.junit.jupiter.api.Disabled
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test : DayTemplate() {

    enum class Direction {
        L, R, U, D
    }

    data class Move(val direction: Direction, val steps: Int)

    data class Point(val x: Int, val y: Int) {
        override fun toString(): String {
            return "(x=$x, y=$y)"
        }

        fun moveInDirection(direction: Direction) =
            when (direction) {
                Direction.L -> Point(x - 1, y)
                Direction.R -> Point(x + 1, y)
                Direction.U -> Point(x, y + 1)
                Direction.D -> Point(x, y - 1)
            }

        fun moveAdjacentTo(other: Point): Point {
            data class PointDistance(val point: Point, val distance: Int)

            //println("Current position $this - relative to $other d=${distanceTo(other)}")

            val neighbours = neighbours()
            val theirNeighbours = other.neighbours()

            return if (theirNeighbours.contains(this)) {
                //println("Touching: $other")
                this
            } else {
                val reachable = neighbours.intersect(theirNeighbours)
                val reachableByDistance = reachable
                    .map { PointDistance(it, distanceTo(it)) }
                    .filter { other.distanceTo(it.point) == 1 }
                    .sortedBy(PointDistance::distance)

                val movingTo = reachableByDistance.first()
                //println("Moving to: ${movingTo.point} d=${movingTo.distance}")
                movingTo.point
            }
        }

        private fun distanceTo(it: Point) = abs(it.x - x) + abs(it.y - y)

        private fun neighbours() =
            (-1..1)
                .flatMap { deltaX ->
                    (-1..1)
                        .map { deltaY -> Point(x + deltaX, y + deltaY) }
                }
                .toSet()
    }

    private fun String.toMove(): Move =
        when {
            startsWith("L") -> Move(Direction.L, substringAfter(" ").toInt())
            startsWith("R") -> Move(Direction.R, substringAfter(" ").toInt())
            startsWith("U") -> Move(Direction.U, substringAfter(" ").toInt())
            startsWith("D") -> Move(Direction.D, substringAfter(" ").toInt())
            else -> throw IllegalArgumentException("Unexpected move $this")
        }

    private class Rope(size: Int) {

        val knots = (0 until size).map { Point(x = 0, y = 0) }.toMutableList()
        fun moveHead(move: Move) {
            knots[0] = knots[0].moveInDirection(move.direction)
        }

        fun moveRemainingKnots() {
            (1 until knots.size)
                .forEach {
                    knots[it] = knots[it].moveAdjacentTo(knots[it - 1])
                }
        }

        val tail: Point
            get() = knots.last()
    }

    fun test1(file: String): Int {
        val moves = readLines(file)
            .map { it.toMove() }

        val rope = Rope(2)
        val tailPositions = mutableSetOf<Point>()

        moves.forEach { move ->
            //println(move)
            repeat(move.steps) {
                rope.moveHead(move)
                rope.moveRemainingKnots()
                tailPositions.add(rope.tail)
            }
            //println()
        }
        return tailPositions.size
    }

    fun test2(file: String): Int =
        readLines(file)
            .count()


    @Test
    fun `test1 sample`() {
        val actual = test1("sample")
        assertEquals(13, actual)
    }

    @Test
    @Disabled
    fun `test2 sample larger`() {
        val actual = test2("sample-larger")
        assertEquals(36, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = test1("input")
        assertEquals(6367, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = test2("input")
        assertEquals(2000, actual)
    }
}
