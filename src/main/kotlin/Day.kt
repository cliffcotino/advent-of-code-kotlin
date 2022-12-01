abstract class Day {

    fun readLines(suffix: String = ""): List<String> {
        val className = javaClass.simpleName
        val resource = javaClass.getResourceAsStream("/$className$suffix.txt")!!
        return resource.bufferedReader().use { it.readLines() }
    }
}