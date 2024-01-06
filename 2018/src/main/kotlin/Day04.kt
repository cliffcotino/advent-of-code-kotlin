import java.time.Instant
import java.time.format.DateTimeFormatter

class Day04 : Day() {

    class Schedule {
        private val startMinute = 0
        private val endMinute = 59
        private val days = MutableList<Array<Pair<Guard, Boolean>>>(0) { emptyArray() }
//    private val currentDay = Array<Pair<Guard, Boolean>>(0) { emptyArray() }

        fun addAction(guard: String?, s: String) {
//        TODO("not implemented")
//        currentDay.add()
        }
    }

    class Guard (
        val guard: String
    )

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    private val sample =
            """
                [1518-11-01 00:00] Guard #10 begins shift
                [1518-11-01 00:05] falls asleep
                [1518-11-01 00:25] wakes up
                [1518-11-01 00:30] falls asleep
                [1518-11-01 00:55] wakes up
                [1518-11-01 23:58] Guard #99 begins shift
                [1518-11-02 00:40] falls asleep
                [1518-11-02 00:50] wakes up
                [1518-11-03 00:05] Guard #10 begins shift
                [1518-11-03 00:24] falls asleep
                [1518-11-03 00:29] wakes up
                [1518-11-04 00:02] Guard #99 begins shift
                [1518-11-04 00:36] falls asleep
                [1518-11-04 00:46] wakes up
                [1518-11-05 00:03] Guard #99 begins shift
                [1518-11-05 00:45] falls asleep
                [1518-11-05 00:55] wakes up
            """.trimIndent().lines()

    fun testGuardsSample() {
        val lines: List<String> = sample.sorted()
        val schedule = Schedule()
        var guard: String? = null
        var previousDay = Instant.ofEpochMilli(0)
        for (line in lines) {
            val date = line.substringAfter("[").substringBefore("]")
            val dateTime = Instant.from(dateTimeFormatter.parse(date))
            var currentDay = dateTime
            when {
                line.contains("begins shift") -> {
                    guard = line.substringAfter("#").substringBefore(" ")
                }
                line.contains("falls asleep") -> {
                    schedule.addAction(guard, "sleep")
                }
                line.contains("wakes up") -> {
                    schedule.addAction(guard, "wake")
                }
            }
        }
        check(schedule.toString() == "4")
    }
}

