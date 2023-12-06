import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/main/resources/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Get the string between start and end
 */
fun String.substringBetween(start: String, end: String): String = substringAfter(start).substringBefore(end)

fun String.splitToLongs(): List<Long> {
    return trim().split(Regex("\\s+")).map { it.toLong() }
}

fun String.splitToInts(): List<Int> {
    return trim().split(Regex("\\s+")).map { it.toInt() }
}

/**
 * Assert that actual == expected
 */
fun <T> assertEquals(actual: T, expected: T) {
    if (actual != expected) {
        throw AssertionError("actual != expected => '$actual' != '$expected'")
    }
}

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Measure how long the given block calculation takes to execute
 */
fun measure(block: () -> Unit) {
    val start = System.currentTimeMillis()
    block()
    println("Execution duration: ${System.currentTimeMillis() - start} ms" )
}
