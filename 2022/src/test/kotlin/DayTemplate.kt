abstract class DayTemplate {

    fun readLines(suffix: String): List<String> {
        val className = javaClass.simpleName
        val resource = javaClass.getResourceAsStream("/${className.substringBefore("Test")}$suffix.txt")!!
        return resource.bufferedReader().use { it.readLines() }
    }

}
