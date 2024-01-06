import java.nio.file.Path
import java.nio.file.Paths

abstract class Day {

    fun getTestInputPath(): Path {
        val format = String.format("/%s.txt", this.javaClass.simpleName.lowercase())
        val resource = javaClass.getResource(format)
        return Paths.get(resource!!.path)
    }

}
