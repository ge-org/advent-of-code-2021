fun main() {
    fun part1(input: String): Long = getCyclesAndFish(input).tick(days = 80).numberOfFish

    fun part2(input: String): Long = getCyclesAndFish(input).tick(days = 256).numberOfFish

    // test if implementation meets criteria from the description, like:
    val testInput = readInputRaw("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539L)

    val input = readInputRaw("Day06")
    println(part1(input))
    println(part2(input))
}

private fun Map<Int, Long>.tick(days: Int): Map<Int, Long> = (0 until days).fold(this) { dict, _ ->
    dict.mapKeys { it.key - 1 }.toMutableMap().apply {
        val fishToSpawn = remove(-1) ?: 0
        put(6, getOrDefault(6, 0) + fishToSpawn)
        put(8, getOrDefault(8, 0) + fishToSpawn)
    }
}

/**
 * Map of remaining days to number of fish
 */
private fun getCyclesAndFish(input: String) = input.split(",")
    .mapNotNull { it.trim().toIntOrNull() }
    .groupingBy { it }.eachCount().mapValues { it.value.toLong() }

private val Map<Int, Long>.numberOfFish get() = values.sumOf { it }
