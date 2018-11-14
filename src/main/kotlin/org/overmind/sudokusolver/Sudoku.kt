package org.overmind.sudokusolver

import java.io.FileInputStream
import java.util.*

data class Sudoku(val matrix: List<List<CellValue>>) {
    val rows: List<Row> = List(9) {
        Row(it, this)
    }

    val columns: List<Column> = List(9) {
        Column(it, this)
    }

    val squares: Map<SquarePosition, Square> = SquarePosition.all
            .associateBy(
                    { it },
                    { Square(it, this) }
            ).toSortedMap()

    val groups: List<Group> = rows + columns + squares.values

    val cells: Map<Position, Cell> = Position.all
            .associateBy(
                    { it },
                    { Cell(it, this) }
            ).toSortedMap()

    operator fun get(rowIndex: Int, columnIndex: Int): CellValue {
        return matrix[rowIndex][columnIndex]
    }

    operator fun get(position: Position): CellValue {
        return matrix[position.rowIndex][position.columnIndex]
    }

    fun map(block: (Cell) -> CellValue) : Sudoku {
        return List(9) { rowIndex ->
            List(9) { columnIndex ->
                val position = Position(rowIndex, columnIndex)
                val cell = cells[position]!!
                block(cell)
            }
        }.run(::Sudoku)
    }

    companion object {
        fun fromFile(path: String): Sudoku {
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
    }
}