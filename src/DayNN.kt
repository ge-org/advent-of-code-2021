fun main() {
    fun part1(input: List<String>): Int = 42

    fun part2(input: List<String>): Int = 42

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("DayNN_test")
    check(part1(testInput) == 42)
    check(part2(testInput) == 42)

    val input = readInput("DayNN")
    println(part1(input))
    println(part2(input))
}
