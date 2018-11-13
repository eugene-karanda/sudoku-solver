package org.overmind.sudokusolver

import java.io.FileInputStream
import java.util.*

data class Sudoku<V : RawCellValue>(private val matrix: List<List<V>>) {
    val rows: List<Row> = List(9, ::Row)

    val columns: List<Column> = List(9, ::Column)

    val squares: Map<SquarePosition, Square> = SquarePosition.all
            .associateBy({ it }, { Square(it) })
            .toSortedMap()

    val groups: List<Group<V>> = rows + columns + squares.values

    val cells: Map<Position, Cell> = Position.all
            .associateBy({ it }, { Cell(it) })
            .toSortedMap()


    operator fun get(rowIndex: Int, columnIndex: Int): V {
        return matrix[rowIndex][columnIndex]
    }

    operator fun get(position: Position): V {
        return matrix[position.rowIndex][position.columnIndex]
    }

    fun <R : V> map(block: (Cell) -> R) : Sudoku<R> {
        return List(9) { rowIndex ->
            List(9) { columnIndex ->
                val position = Position(rowIndex, columnIndex)
                val cell = cells[position]!!
                block(cell)
            }
        }.run(::Sudoku)
    }

    interface Group<V : RawCellValue> {
        fun values(): Sequence<V>

        fun cells(): Sequence<Sudoku<V>.Cell>
    }

    inner class Row(private val rowIndex: Int) : Group<V> {
        override fun values(): Sequence<V> {
            return matrix[rowIndex]
                    .asSequence()
        }

        override fun cells(): Sequence<Cell> {
            return cells.asSequence()
                    .filter { (position, _) ->
                        position.rowIndex == rowIndex
                    }
                    .map { (_, cell) ->
                        cell
                    }
        }

    }

    inner class Column(private val columnIndex: Int) : Group<V> {
        override fun values(): Sequence<V> {
            return ((0 until 9))
                    .asSequence()
                    .map { innerRowIndex ->
                        this@Sudoku[innerRowIndex, columnIndex]
                    }
        }

        override fun cells() : Sequence<Cell> {
            return cells.asSequence()
                    .filter { (position, _) ->
                        position.columnIndex == columnIndex
                    }
                    .map { (_, cell) ->
                        cell
                    }
        }
    }

    inner class Square(private val squarePosition: SquarePosition) : Group<V> {
        override fun values(): Sequence<V> {
            return PositionInSquare.all
                    .map { innerPosition ->
                        this@Sudoku[
                                squarePosition.rowIndex * 3 + innerPosition.rowIndex,
                                squarePosition.columnIndex * 3 + innerPosition.columnIndex
                        ]
                    }
        }

        override fun cells(): Sequence<Cell> {
            return cells.asSequence()
                    .filter { (position, _) ->
                        position.squarePosition == squarePosition
                    }
                    .map { (_, cell) ->
                        cell
                    }
        }

        fun additionalValues(positionInSquare: SquarePosition): Sequence<V> {
            return SquarePosition.all
                    .filter {
                        it.rowIndex != positionInSquare.rowIndex || it.columnIndex != positionInSquare.columnIndex
                    }
                    .map { innerPosition ->
                        this@Sudoku[
                                squarePosition.rowIndex * 3 + innerPosition.rowIndex,
                                squarePosition.columnIndex * 3 + innerPosition.columnIndex
                        ]
                    }
        }
    }

    inner class Cell(val position: Position) {
        val row: Row = rows[position.rowIndex]

        val column: Column = columns[position.columnIndex]

        val square: Square = squares[position.squarePosition]!!

        val value: V = this@Sudoku[position]

        private fun rowNeighbors(): Sequence<V> {
            return row.values()
                    .except(value)
        }

        private fun columnNeighbors(): Sequence<V> {
            return column.values()
                    .except(value)
        }

        private fun squareNeighbors(): Sequence<V> {
            return square.values()
                    .except(value)
        }

        fun neighbors(): Sequence<V> {
            return rowNeighbors() + columnNeighbors() + square.additionalValues(position.positionInSquare)
        }

        fun neighborsGroups(): Sequence<Sequence<V>> {
            return sequenceOf(
                    rowNeighbors(),
                    columnNeighbors(),
                    squareNeighbors()
            )
        }
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