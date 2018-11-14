package org.overmind.sudokusolver

interface Cell {
    val position: Position

    val row: Row

    val column: Column

    val square: Square

    val value: CellValue

    fun rowNeighbors(): Sequence<Cell>

    fun columnNeighbors(): Sequence<Cell>

    fun squareNeighbors(): Sequence<Cell>

    fun neighbors(): Sequence<Cell>

    fun neighborsGroups(): Sequence<Sequence<Cell>>
}

data class BaseCell(override val position: Position, val sudoku: Sudoku, override val value: CellValue) : Cell {
    override val row: Row = sudoku.rows[position.rowIndex]

    override val column: Column = sudoku.columns[position.columnIndex]

    override val square: Square = sudoku.squares[position.squarePosition]!!

    override fun rowNeighbors(): Sequence<Cell> {
        return row.cells()
                .except(this)
    }

    override fun columnNeighbors(): Sequence<Cell> {
        return column.cells()
                .except(this)
    }

    override fun squareNeighbors(): Sequence<Cell> {
        return square.cells()
                .except(this)
    }

    override fun neighbors(): Sequence<Cell> {
        return rowNeighbors() +
                columnNeighbors() +
                square.additionalValues(position.positionInSquare)
    }

    override fun neighborsGroups(): Sequence<Sequence<Cell>> {
        return sequenceOf(
                rowNeighbors(),
                columnNeighbors(),
                squareNeighbors()
        )
    }
}

class NumberCell(
        val number: Int,
        override val position: Position,
        val sudoku: Sudoku,
        value: CellValue
) : Cell by BaseCell(position, sudoku, value)

class CandidatesCell(
        val candidates: Set<Int>,
        position: Position,
        sudoku: Sudoku,
        value: CellValue
) : Cell by BaseCell(position, sudoku, value)