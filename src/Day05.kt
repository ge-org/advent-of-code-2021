fun main() {

    fun part1(input: List<String>): Int {
        val straightLines = getLines(input).filter(::isStraightLine)
        return expandIntoPoints(straightLines).getIntersections()
    }

    fun part2(input: List<String>): Int {
        val lines = getLines(input)
        return expandIntoPoints(lines).getIntersections()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

private fun isStraightLine(it: Pair<Point, Point>) = it.first.x == it.second.x || it.first.y == it.second.y

private fun expandIntoPoints(lines: List<Pair<Point, Point>>) = lines.fold(emptyList<Point>()) { allPoints, line ->
    val xRange = line.first.x.toward(line.second.x)
    val yRange = line.first.y.toward(line.second.y)
    when {
        isStraightLine(line) -> xRange.map { x ->
            yRange.map { y -> Point(x, y) }
        }.flatten()
        else -> xRange.zip(yRange) { x, y -> Point(x, y) }
    } + allPoints
}

private fun getLines(input: List<String>): List<Pair<Point, Point>> = input.map {
    val digits = it.replace(" -> ", ",").split(",").map(String::toInt)
    Point(digits[0], digits[1]) to Point(digits[2], digits[3])
}

private fun List<Point>.getIntersections(min: Int = 2) = groupingBy { it }.eachCount().count { it.value >= min }

private fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}
