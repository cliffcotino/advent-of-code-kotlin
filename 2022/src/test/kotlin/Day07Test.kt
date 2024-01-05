
import Day07Test.Node.Directory
import Day07Test.Node.File
import kotlin.test.Test
import kotlin.test.assertEquals

class Day07Test : DayTemplate() {

    data class Tree(
        val root: Directory = Directory(name = "/", parent = null),
    ) {
        fun resolveDirectory(currentDirectory: Directory?, targetDirectory: String): Directory {
            return when (targetDirectory) {
                "/" -> root
                ".." -> currentDirectory!!.parent!!
                else -> currentDirectory!!.children.filterIsInstance<Directory>().first { it.name == targetDirectory }
            }
        }

        fun traverse(): MutableList<Node> {
            val nodes = mutableListOf<Node>()
            traverse(root, nodes)
            return nodes
        }

        private fun traverse(node: Node, nodes: MutableList<Node>) {
            when (node) {
                is Directory -> {
                    nodes.add(node)
                    node.children.forEach { traverse(it, nodes) }
                }
                is File -> nodes.add(node)
            }
        }
    }

    sealed interface Node {
        val size: Int
        val name: String
        val parent: Directory?
        val children: List<Node>

        data class File(
            override val name: String,
            override val size: Int,
            override val parent: Directory?,
            override val children: List<Node> = listOf(),
        ) : Node {
            override fun toString(): String =
                "file: $name - parent: ${parent?.name}"
        }

        data class Directory(
            override val name: String,
            override val parent: Directory?,
            override val children: MutableList<Node> = mutableListOf(),
        ) : Node {
            override val size: Int
                get() = children.sumOf { it.size }

            fun addNode(node: Node) {
                children.add(node)
            }

            override fun toString(): String {
                return "dir: $name - parent: ${parent?.name}"
            }
        }
    }

    private fun parseTree(iterator: Iterator<String>): Tree {
        val tree = Tree()
        var currentDirectory: Directory? = null

        while (iterator.hasNext()) {
            val line = iterator.next()
            when {
                line.startsWith("\$ cd") -> {
                    val targetDirectory = line.substringAfter("\$ cd ")
                    currentDirectory = tree.resolveDirectory(currentDirectory, targetDirectory)
                }
                line.startsWith("\$ ls") -> {
                    // noop
                }
                line.startsWith("dir") -> {
                    val name = line.substringAfter("dir ")
                    currentDirectory!!.addNode(Directory(name = name, parent = currentDirectory))
                }
                else -> {
                    val (size, name) = line.split(" ")
                    currentDirectory!!.addNode(File(name = name, parent = currentDirectory, size = size.toInt()))
                }
            }
        }
        return tree
    }

    fun test1(file: String): Int {
        val iter = readLines(file).iterator()
        val tree = parseTree(iter)
        return tree.traverse().asSequence()
            .filterIsInstance<Directory>()
            .filter { it.size <= 100000 }
            .sumOf { it.size }
    }

    fun test2(file: String): Int {
        val iter = readLines(file).iterator()
        val tree = parseTree(iter)
        val sortedBySize = tree.traverse().asSequence()
            .filterIsInstance<Directory>()
            .sortedBy { it.size }
        val totalSize = 70_000_000
        val unusedRequired = 30_000_000
        val unusedSize = totalSize - tree.root.size
        return sortedBySize
            .map { it.size }
            .first { unusedSize + it >= unusedRequired }
    }

    @Test
    fun `test1 sample`() {
        val actual = test1("sample")
        assertEquals(95437, actual)
    }

    @Test
    fun `test2 sample`() {
        val actual = test2("sample")
        assertEquals(24933642, actual)
    }

    @Test
    fun `test1 actual`() {
        val actual = test1("input")
        assertEquals(1315285, actual)
    }

    @Test
    fun `test2 actual`() {
        val actual = test2("input")
        assertEquals(9847279, actual)
    }
}
