import kotlin.math.abs

fun main() {
    fun part1(input: String): Int = getPositions(input).getMinFuelConsumption()

    fun part2(input: String): Int = getPositions(input).getMinFuelConsumption { steps -> steps * (steps + 1) / 2 }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputRaw("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInputRaw("Day07")
    println(part1(input))
    println(part2(input))
}

private fun getPositions(input: String) = input.split(",")
    .mapNotNull { it.trim().toIntOrNull() }

private fun List<Int>.getMinFuelConsumption(
    consumedFuel: (steps: Int) -> Int = { it },
) = (0 until maxOrNull()!!).minOfOrNull { targetPosition ->
    sumOf { consumedFuel(abs(targetPosition - it)) }
}!!
