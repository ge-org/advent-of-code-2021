fun main() {
    fun part1(input: List<List<Int>>): Int {
        val matrix = input.transpose()
        val gamma = frequencies(matrix) { zeros, ones -> if (zeros > ones) 0 else 1 }.asBinary()
        val epsilon = frequencies(matrix) { zeros, ones -> if (zeros < ones) 0 else 1 }.asBinary()
        return gamma * epsilon
    }

    fun part2(input: List<List<Int>>): Int {
        val oxygenCriteria: Criteria = { zeros, ones -> if (zeros > ones) 0 else 1 }
        val co2Criteria: Criteria =  { zeros, ones -> if (zeros <= ones) 0 else 1 }
        val oxygen = reduceWithCriteria(input, 0, oxygenCriteria).flatten().asBinary()
        val co2 = reduceWithCriteria(input, 0, co2Criteria).flatten().asBinary()
        return oxygen * co2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = parseInput(readInput("Day03_test"))
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = parseInput(readInput("Day03"))
    println(part1(input))
    println(part2(input))
}

private fun parseInput(input: List<String>) = input.map { it.map(Char::digitToInt) }

tailrec fun reduceWithCriteria(rows: List<List<Int>>, columnIndex: Int, criteria: Criteria): List<List<Int>> {
    if (rows.size == 1) return rows
    val frequencies = frequencies(rows.transpose(), criteria)
    return reduceWithCriteria(rows.filter { it[columnIndex] == frequencies[columnIndex] }, columnIndex + 1, criteria)
}

/**
 * Calculates the frequencies of zeros and ones and maps each frequency to a discrete value.
 */
private fun frequencies(matrix: List<List<Int>>, f: Criteria) = matrix
    .map { it.groupingBy { it }.eachCount() }
    .map { it.getOrDefault(0, 0) to it.getOrDefault(1, 0) }
    .map { (zeros, ones) -> f(zeros, ones) }

private fun <T> List<List<T>>.transpose() = (0 until first().size).map { colIdx ->
    indices.map { rowIdx ->
        this[rowIdx][colIdx]
    }
}

private fun List<Int>.asBinary() = joinToString("").toInt(2)

private typealias Criteria = (zeros: Int, ones: Int) -> Int
