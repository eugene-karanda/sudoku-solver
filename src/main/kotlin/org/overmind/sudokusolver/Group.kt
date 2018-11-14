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

class Square(private val squarePosition: SquarePosition, private val sudoku: Sudoku) : Group {
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