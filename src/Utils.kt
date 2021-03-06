import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

fun readInputRaw(name: String) = File("src", "$name.txt").readText()

fun readInputInt(name: String) = File("src", "$name.txt").readLines().map(String::toInt)

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun <T> List<List<T>>.transpose() = (0 until first().size).map { colIdx ->
    indices.map { rowIdx ->
        this[rowIdx][colIdx]
    }
}

data class Point(val x: Int, val y: Int)
