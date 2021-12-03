fun main() {
    fun part1(input: List<Int>): Int = input.zipWithNext().count { (a, b) -> a < b }

    fun part2(input: List<Int>): Int = input
        .windowed(3)
        .map(List<Int>::sum)
        .zipWithNext().count { (a, b) -> a < b }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputInt("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInputInt("Day01")
    println(part1(input))
    println(part2(input))
}
