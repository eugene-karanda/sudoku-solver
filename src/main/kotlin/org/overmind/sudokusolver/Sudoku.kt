package org.overmind.sudokusolver

import java.io.FileInputStream
import java.util.*

data class Sudoku<V : RawCellValue>(val matrix: List<List<V>>) {
    val rows: List<Row<V>> = List(9) {
        Row(it, this)
    }

    val columns: List<Column<V>> = List(9) {
        Column(it, this)
    }

    val squares: Map<SquarePosition, Square<V>> = SquarePosition.all
            .associateBy(
                    { it },
                    { Square(it, this) }
            ).toSortedMap()

    val groups: List<Group<V>> = rows + columns + squares.values

    val cells: Map<Position, Cell<V>> = Position.all
            .associateBy(
                    { it },
                    { Cell(it, this) }
            ).toSortedMap()

    operator fun get(rowIndex: Int, columnIndex: Int): V {
        return matrix[rowIndex][columnIndex]
    }

    operator fun get(position: Position): V {
        return matrix[position.rowIndex][position.columnIndex]
    }

    fun <R : V> map(block: (Cell<V>) -> R) : Sudoku<R> {
        return List(9) { rowIndex ->
            List(9) { columnIndex ->
                val position = Position(rowIndex, columnIndex)
                val cell = cells[position]!!
                block(cell)
            }
        }.run(::Sudoku)
    }

    companion object {
        fun fromFile(path: String): Sudoku<CellValue> {
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
                MutableList(9) rowList@{ columnIndex ->
                    val leftTopChar = charMatrix[rowIndex * 3][columnIndex * 3]
                    if (leftTopChar == '*') {
                        val centralChar = charMatrix[rowIndex * 3 + 1][columnIndex * 3 + 1]
                        val number = Character.digit(centralChar, 10)
                        return@rowList NumberCellValue(number)
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
                                throw IllegalArgumentException("Wrong number $number at value($rowIndex, $columnIndex) at position($rowInnerIndex, $columnInnerIndex)")
                            }

                            candidates.add(number)
                        }
                    }

                    return@rowList CandidatesCellValue(candidates)
                }
            }

            return Sudoku(matrix)
        }

        fun rawFromFile(path: String): Sudoku<RawCellValue> {
            val fis = FileInputStream(path)
            val scanner = Scanner(fis)
            val matrix = mutableListOf<MutableList<RawCellValue>>()

            for (rowGroupIndex in 0 until 3) {
                scanner.nextLine()
                for (rowIndex in 0 until 3) {
                    val line = scanner.nextLine()
                    line.filter {
                        it.isDigit() || it.isWhitespace()
                    }.map {
                        when {
                            it.isDigit() -> NumberCellValue(Character.digit(it, 10))
                            it == ' ' -> EmptyCellValue
                            else -> throw IllegalArgumentException("Unknown char - '$it'")
                        }
                    }.also {
                        matrix.add(it.toMutableList())
                    }
                }
            }
            scanner.nextLine()

            return Sudoku(matrix)
        }
    }
}