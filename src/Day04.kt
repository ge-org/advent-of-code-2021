fun main() {
    fun part1(input: String): Int {
        val draws = getDraws(input)
        val boards = getBoards(input)
        val firstWinner = getWinners(draws, boards).first()
        return sumUnmarked(firstWinner.result) * firstWinner.winningDraw
    }

    fun part2(input: String): Int {
        val draws = getDraws(input)
        val boards = getBoards(input)
        val lastWinner = getWinners(draws, boards).last()
        return sumUnmarked(lastWinner.result) * lastWinner.winningDraw
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputRaw("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInputRaw("Day04")
    println(part1(input))
    println(part2(input))
}

private fun getWinners(draws: List<Int>, boards: List<Board>): List<WinningBoard> {
    tailrec fun play(draws: List<Int>, boards: List<Board>, winningBoards: List<WinningBoard>): List<WinningBoard> = when {
        draws.isEmpty() -> winningBoards
        else -> {
            val draw = draws.first()
            val results = mark(draw, boards)
            val winners = results.filter(::isWon)
            val unfinishedBoards = results - winners.toSet()
            play(draws.drop(1), unfinishedBoards, winningBoards + winners.map { WinningBoard(draw, it) })
        }
    }
    return play(draws, boards, emptyList())
}

private data class Board(val rows: List<List<Cell>>)

private data class WinningBoard(
    val winningDraw: Int,
    val result: Board,
)

private sealed class Cell {
    abstract val value: Int
    data class Unmarked(override val value: Int) : Cell()
    data class Marked(override val value: Int) : Cell()
}

private fun mark(draw: Int, boards: List<Board>): List<Board> = boards.map { mark(draw, it) }

private fun mark(draw: Int, board: Board) = board.rows.map { cells ->
    cells.map { if (it.value == draw) Cell.Marked(draw) else it }
}.let(::Board)

private fun isWon(board: Board): Boolean {
    fun anyComplete(cells: List<List<Cell>>) = cells.any { it.all { cell -> cell is Cell.Marked } }
    return anyComplete(board.rows) || anyComplete(board.rows.transpose())
}

private fun sumUnmarked(board: Board): Int = board.rows.sumOf { cells ->
    cells.sumOf {
        when (it) {
            is Cell.Marked -> 0
            is Cell.Unmarked -> it.value
        }
    }
}

private fun getBoards(input: String) = input.substringAfter("\n").trim().split("\n\n").map {
    it.split("\n").map { line -> line.trim().split("\\s+".toRegex()).map { c -> Cell.Unmarked(c.toInt()) } }
}.map(::Board)

private fun getDraws(input: String) = input.substringBefore("\n").split(",").map(String::toInt)
