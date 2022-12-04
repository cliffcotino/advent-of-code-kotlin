
class DayTemplate : Day() {

    fun String.toDomain(): Any =
        TODO()

    fun test1(file: String): Int =
        readLines(file)
            .map { it.toDomain() }
            .count()

    fun test2(file: String): Int =
        readLines(file)
            .map { it.toDomain() }
            .count()

}
