package org.overmind.sudokusolver

data class Cell<V : RawCellValue>(val position: Position, private val sudoku: Sudoku<V>) {
    val row: Row<V> = sudoku.rows[position.rowIndex]

    val column: Column<V> = sudoku.columns[position.columnIndex]

    val square: Square<V> = sudoku.squares[position.squarePosition]!!

    val value: V = sudoku[position]

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