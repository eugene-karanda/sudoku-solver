package org.overmind.sudokusolver

interface Group {
    fun cells(): Sequence<Cell>
}

class Row(private val rowIndex: Int, private val sudoku: Sudoku) : Group {
    override fun cells(): Sequence<Cell> {
        return sudoku.cells
                .asSequence()
                .filter {
                    it.position.rowIndex == rowIndex
                }
    }
}

class Column(private val columnIndex: Int, private val sudoku: Sudoku) : Group {
    override fun cells(): Sequence<Cell> {
        return sudoku.cells
                .asSequence()
                .filter {
                    it.position.columnIndex == columnIndex
                }
    }
}

interface SquareGroup {
    val square: Square

    val fullGroup: Group

    fun cells(): Sequence<Cell>
}

data class SquareRow(val rowIndex: Int, override val square: Square) : SquareGroup {
    override val fullGroup: Row = square.sudoku.rows[square.squarePosition.rowIndex * 3 + rowIndex]

    override fun cells(): Sequence<Cell> {
        return square.cells()
                .filter {
                    it.position.positionInSquare.rowIndex == rowIndex
                }
    }
}

data class SquareColumn(val columnIndex: Int, override val square: Square) : SquareGroup {
    override val fullGroup: Column = square.sudoku.columns[square.squarePosition.columnIndex * 3 + columnIndex]

    override fun cells(): Sequence<Cell> {
        return square.cells()
                .filter {
                    it.position.positionInSquare.columnIndex == columnIndex
                }
    }
}

data class Square(val squarePosition: SquarePosition, val sudoku: Sudoku) : Group {
    val rows: List<SquareRow> = List(3) {
        SquareRow(it, this)
    }

    val columns: List<SquareColumn> = List(3) {
        SquareColumn(it, this)
    }

    override fun cells(): Sequence<Cell> {
        return sudoku.cells.asSequence()
                .filter {
                    it.position.squarePosition == squarePosition
                }

    }

    fun additionalValues(positionInSquare: PositionInSquare): Sequence<Cell> {
        return cells()
                .filter {
                    it.position.positionInSquare.rowIndex != positionInSquare.rowIndex
                            || it.position.positionInSquare.columnIndex != positionInSquare.columnIndex
                }
    }
}