import java.lang.IllegalArgumentException
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

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
