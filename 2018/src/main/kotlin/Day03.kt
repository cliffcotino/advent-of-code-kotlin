import java.nio.file.Files

class Day03 : Day() {

    data class Claim (
        val id: String,
        val offsetLeft: Int,
        val offsetTop: Int,
        val width: Int,
        val height: Int
    )

    private val sample =
            """
                #1 @ 1,3: 4x4
                #2 @ 3,1: 4x4
                #3 @ 5,5: 2x2
            """.trimIndent().lines()

    fun testOverlappingCellsSample() {
        val claims = sample
                .map { lineToClaim(it) }
                .toList()
        val cloth = Cloth(10, 10)
        for (claim in claims) {
            cloth.mark(claim)
        }
        cloth.print()

        check(cloth.overlappingCells() == 4)
    }

    fun testOverlappingCells() {
        val claims = Files.lines(getTestInputPath())
                .map { lineToClaim(it) }
                .toList()
        val cloth = Cloth(1024, 1024)
        for (claim in claims) {
            cloth.mark(claim)
        }
        check(cloth.overlappingCells() == 110195)
    }

    fun testClaimOverlapsNoneSample() {
        val claims = sample
                .map { lineToClaim(it) }
                .toList()
        val cloth = Cloth(10, 10)
        for (claim in claims) {
            cloth.mark(claim)
        }
        cloth.print()

        for (claim in claims) {
            if (!cloth.hasOverlap(claim)) {
                check(claim.id == "3")
            }
        }
    }

    fun testClaimOverlapsNone() {
        val claims = Files.lines(getTestInputPath())
                .map { lineToClaim(it) }
                .toList()
        val cloth = Cloth(1024, 1024)
        for (claim in claims) {
            cloth.mark(claim)
        }

        for (claim in claims) {
            if (!cloth.hasOverlap(claim)) {
                check(claim.id == "894")
            }
        }
    }

    private fun lineToClaim(line: String): Claim {
        // "#1287 @ 152,94: 10x27"
        val split = line.trim().split(" ")
        val offsets = split[2].split(",")
        val dimensions = split[3].split("x")
        return Claim(split[0].substringAfter("#"),
                offsets[0].toInt(),
                offsets[1].replace(":", "").toInt(),
                dimensions[0].toInt(),
                dimensions[1].toInt())
    }
}

class Cloth(width: Int, height: Int) {

    private val area: Array<IntArray> = Array(height) { IntArray(width) }
    private val claimLists: Array<Array<HashSet<String>>> = Array(height) { Array(width) { HashSet<String>() } }

    fun mark(claim: Day03.Claim) {
        forEachCell(claim) { i, j ->
            area[i][j] = area[i][j] + 1
            claimLists[i][j].add(claim.id)
        }
    }

    fun overlappingCells(): Int {
        var overlap = 0
        for (rows in area) {
            for (cell in rows) {
                if (cell > 1) {
                    overlap++
                }
            }
        }
        return overlap
    }

    fun hasOverlap(claim: Day03.Claim): Boolean {
        var overlap = false
        forEachCell(claim) { i, j ->
            if (claimLists[i][j].size > 1) {
                overlap = true
            }
        }
        return overlap
    }

    private fun forEachCell(claim: Day03.Claim, consumer: (Int, Int) -> Unit) {
        for (i in claim.offsetTop..claim.offsetTop - 1 + claim.height) {
            for (j in claim.offsetLeft..claim.offsetLeft - 1 + claim.width) {
                consumer(i, j)
            }
        }
    }

    fun print() {
        for (rows in area) {
            for (cell in rows) {
                print(if (cell > 0) cell else ".")
            }
            println()
        }
    }
}
