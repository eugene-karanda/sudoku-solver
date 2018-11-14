package org.overmind.sudokusolver

data class Cell(val position: Position, private val sudoku: Sudoku) {
    val row: Row = sudoku.rows[position.rowIndex]

    val column: Column = sudoku.columns[position.columnIndex]

    val square: Square = sudoku.squares[position.squarePosition]!!

    val value: CellValue = sudoku[position]

    private fun rowNeighbors(): Sequence<CellValue> {
        return row.values()
                .except(value)
    }

    private fun columnNeighbors(): Sequence<CellValue> {
        return column.values()
                .except(value)
    }

    private fun squareNeighbors(): Sequence<CellValue> {
        return square.values()
                .except(value)
    }

    fun neighbors(): Sequence<CellValue> {
        return rowNeighbors() + columnNeighbors() + square.additionalValues(position.positionInSquare)
    }

    fun neighborsGroups(): Sequence<Sequence<CellValue>> {
        return sequenceOf(
                rowNeighbors(),
                columnNeighbors(),
                squareNeighbors()
        )
    }
}