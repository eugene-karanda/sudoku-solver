package org.overmind.sudokusolver

import java.io.FileInputStream
import java.util.*

data class RawSudoku(private val matrix: List<List<Cell>>) {
    operator fun get(rowIndex: Int, columnIndex: Int): Cell {
        return matrix[rowIndex][columnIndex]
    }

    companion object {
        fun fromFile(path: String): RawSudoku {
            val fis = FileInputStream(path)
            val scanner = Scanner(fis)
            val matrix = mutableListOf<List<Cell>>()

            for (rowGroupIndex in 0..2) {
                scanner.nextLine()
                for (rowIndex in 0..2) {
                    val line = scanner.nextLine()
                    line.filter {
                        it != '|'
                    }.map {
                        when {
                            it.isDigit() ->NumberCell(Character.digit(it, 10))
                            it == ' ' -> EmptyCell
                            else -> throw IllegalArgumentException("Unknown char - '$it'")
                        }
                    }.also {
                        matrix.add(it)
                    }
                }
            }
            scanner.nextLine()

            return RawSudoku(matrix)
        }
    }
}
