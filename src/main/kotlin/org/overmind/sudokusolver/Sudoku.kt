package org.overmind.sudokusolver

import java.io.FileInputStream
import java.util.*

data class Sudoku<T : RawCell>(val matrix: List<List<T>>) {
    operator fun get(rowIndex: Int, columnIndex: Int): T {
        return matrix[rowIndex][columnIndex]
    }

    fun <R : T> map(block: (T) -> R): Sudoku<R> {
        return matrix.map { row ->
            row.map(block)
        }.run(::Sudoku)
    }

    fun <R : T> mapIndexed(block: (Int, Int, T) -> R): Sudoku<R> {
        return matrix.mapIndexed() { rowIndex, row ->
            row.mapIndexed { columnIndex, cell ->
                block(rowIndex, columnIndex, cell)
            }
        }.run(::Sudoku)
    }

    fun rowNeighborsOfCell(rowIndex: Int, columnIndex: Int): Sequence<T> {
        return matrix[rowIndex]
                .asSequence()
                .filterIndexed { index, _ ->
                    index != columnIndex
                }
    }

    fun columnNeighborsOfCell(rowIndex: Int, columnIndex: Int): Sequence<T> {
        return ((0 until rowIndex) + (rowIndex + 1 until 9))
                .asSequence()
                .map { innerRowIndex ->
                    this[innerRowIndex, columnIndex]
                }
    }

    fun squareNeighborsOfCell(rowIndex: Int, columnIndex: Int): Sequence<T> {
        val squareTopRowIndex = (rowIndex / 3) * 3
        val squareLeftColumnIndex = (columnIndex / 3) * 3

        return (0 until 3)
                .asSequence()
                .flatMap { squareRowIndex ->
                    (0 until 3)
                            .asSequence()
                            .filter { squareColumnIndex ->
                                squareRowIndex != rowIndex || squareColumnIndex != columnIndex
                            }
                            .map { squareColumnIndex ->
                                this[squareTopRowIndex + squareRowIndex, squareLeftColumnIndex + squareColumnIndex]
                            }
                }
    }

    fun neighborsOfCell(rowIndex: Int, columnIndex: Int): Sequence<T> {
        val rowNeighbors = matrix[rowIndex]
                .asSequence()
                .filterIndexed { index, _ ->
                    index != columnIndex
                }

        val columnNeighbors = ((0 until rowIndex) + (rowIndex + 1 until 9))
                .asSequence()
                .map { innerRowIndex ->
                    this[innerRowIndex, columnIndex]
                }

        val squareTopRowIndex = (rowIndex / 3) * 3
        val squareLeftColumnIndex = (columnIndex / 3) * 3

        val squareNeighbors = (0 until 3)
                .asSequence()
                .filter { squareRowIndex ->
                    squareRowIndex != rowIndex
                }
                .flatMap { squareRowIndex ->
                    (0 until 3)
                            .asSequence()
                            .filter { squareColumnIndex ->
                                squareColumnIndex != columnIndex
                            }
                            .map { squareColumnIndex ->
                                this[squareTopRowIndex + squareRowIndex, squareLeftColumnIndex + squareColumnIndex]
                            }
                }

        return rowNeighbors + columnNeighbors + squareNeighbors
    }

    companion object {
        fun fromFile(path: String): Sudoku<Cell> {
            val fis = FileInputStream(path)
            val scanner = Scanner(fis)
            val charMatrix = mutableListOf<String>()

            for (rowGroupIndex in 0..2) {
                for (rowIndex in 0..2) {
                    scanner.nextLine()
                    for (rowInnerIndex in 0..2) {
                        scanner.nextLine().filter {
                            it.isDigit() || it.isWhitespace() || it == '*'
                        }.also {
                            charMatrix.add(it)
                        }
                    }
                }
            }
            scanner.nextLine()

            val matrix = List(9) matrixList@{ rowIndex ->
                List(9) rowList@{ columnIndex ->
                    val leftTopChar = charMatrix[rowIndex * 3][columnIndex * 3]
                    if (leftTopChar == '*') {
                        val centralChar = charMatrix[rowIndex * 3 + 1][columnIndex * 3 + 1]
                        val number = Character.digit(centralChar, 10)
                        return@rowList NumberCell(number)
                    }

                    val candidates = sortedSetOf<Int>()

                    for (rowInnerIndex in 0..2) {
                        for (columnInnerIndex in 0..2) {
                            val char = charMatrix[rowIndex * 3 + rowInnerIndex][columnIndex * 3 + columnInnerIndex]

                            if (char.isWhitespace()) {
                                continue
                            }

                            val number = Character.digit(char, 10)

                            if (number != (rowInnerIndex * 3 + columnInnerIndex + 1)) {
                                throw IllegalArgumentException("Wrong number $number at cell($rowIndex, $columnIndex) at position($rowInnerIndex, $columnInnerIndex)")
                            }

                            candidates.add(number)
                        }
                    }

                    return@rowList CandidatesCell(candidates)
                }
            }

            return Sudoku(matrix)
        }

        fun rawFromFile(path: String): Sudoku<RawCell> {
            val fis = FileInputStream(path)
            val scanner = Scanner(fis)
            val matrix = mutableListOf<List<RawCell>>()

            for (rowGroupIndex in 0 until 3) {
                scanner.nextLine()
                for (rowIndex in 0 until 3) {
                    val line = scanner.nextLine()
                    line.filter {
                        it.isDigit() || it.isWhitespace()
                    }.map {
                        when {
                            it.isDigit() -> NumberCell(Character.digit(it, 10))
                            it == ' ' -> EmptyCell
                            else -> throw IllegalArgumentException("Unknown char - '$it'")
                        }
                    }.also {
                        matrix.add(it)
                    }
                }
            }
            scanner.nextLine()

            return Sudoku(matrix)
        }
    }
}