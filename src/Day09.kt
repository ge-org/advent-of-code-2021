fun main() {
    fun part1(input: List<String>): Int = getLowPoints(input).fold(0) { totalRiskLevel, lowPoint ->
        1 + totalRiskLevel + input[lowPoint.y][lowPoint.x].digitToInt()
    }

    fun part2(input: List<String>): Int = 42

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
//    check(part2(testInput) == 42)

    val input = readInput("Day09")
    println(part1(input))
//    println(part2(input))
}

private fun getLowPoints(input: List<String>): List<Point> = input.flatMapIndexed { rowIndex, row ->
    val heights = row.split("").mapNotNull(String::toIntOrNull)
    heights.mapIndexedNotNull { columnIndex, height ->
        if (
            listOfNotNull(
                heights.getOrNull(columnIndex - 1),
                heights.getOrNull(columnIndex + 1),
                input.getOrNull(rowIndex - 1)?.getOrNull(columnIndex)?.digitToIntOrNull(),
                input.getOrNull(rowIndex + 1)?.getOrNull(columnIndex)?.digitToIntOrNull(),
            ).all { it > height }
        ) Point(columnIndex, rowIndex) else null
    }
}
