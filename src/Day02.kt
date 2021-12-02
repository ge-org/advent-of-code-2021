fun main() {
    fun part1(input: List<String>): Int = input.parse()
        .fold(Position.Simple(0, 0)) { position, (command, delta) ->
            when (command) {
                "forward" -> position.copy(horizontal = position.horizontal + delta)
                "down" -> position.copy(depth = position.depth + delta)
                "up" -> position.copy(depth = position.depth - delta)
                else -> position
            }
        }.total

    fun part2(input: List<String>): Int = input.parse()
        .fold(Position.Complex(0, 0, 0)) { position, (command, delta) ->
            when (command) {
                "forward" -> position.copy(
                    horizontal = position.horizontal + delta,
                    depth = position.depth + position.aim * delta,
                )
                "down" -> position.copy(aim = position.aim + delta)
                "up" -> position.copy(aim = position.aim - delta)
                else -> position
            }
        }.total

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.parse() = map {
    it.split(" ").let { Command(it[0], it[1].toInt()) }
}

private data class Command(val name: String, val delta: Int)

private sealed class Position {
    abstract val horizontal: Int
    abstract val depth: Int

    data class Simple(override val horizontal: Int, override val depth: Int) : Position()
    data class Complex(val aim: Int, override val horizontal: Int, override val depth: Int) : Position()

    val total get() = horizontal * depth
}
