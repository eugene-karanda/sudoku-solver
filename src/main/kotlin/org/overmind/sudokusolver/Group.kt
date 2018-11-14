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
    fun cells(): Sequence<Cell>
}

class SquareRow(val rowIndex: Int) : SquareGroup {
    override fun cells(): Sequence<Cell> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class SquareColumn(val columnIndex: Int) : SquareGroup {
    override fun cells(): Sequence<Cell> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class Square(private val squarePosition: SquarePosition, private val sudoku: Sudoku) : Group {
    val rows: List<SquareRow> = List(3, ::SquareRow)

    val columns: List<SquareColumn> = List(3, ::SquareColumn)

    val groups: List<SquareGroup> = rows + columns

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