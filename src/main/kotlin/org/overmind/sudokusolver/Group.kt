package org.overmind.sudokusolver

interface Group {
    fun values(): Sequence<CellValue>

    fun cells(): Sequence<Cell>
}

class Row(private val rowIndex: Int, private val sudoku: Sudoku) : Group {
    override fun values(): Sequence<CellValue> {
        return sudoku.matrix[rowIndex]
                .asSequence()
    }

    override fun cells(): Sequence<Cell> {
        return sudoku.cells.asSequence()
                .filter { (position, _) ->
                    position.rowIndex == rowIndex
                }
                .map { (_, cell) ->
                    cell
                }
    }
}

class Column(private val columnIndex: Int, private val sudoku: Sudoku) : Group {
    override fun values(): Sequence<CellValue> {
        return ((0 until 9))
                .asSequence()
                .map { innerRowIndex ->
                    sudoku[innerRowIndex, columnIndex]
                }
    }

    override fun cells() : Sequence<Cell> {
        return sudoku.cells.asSequence()
                .filter { (position, _) ->
                    position.columnIndex == columnIndex
                }
                .map { (_, cell) ->
                    cell
                }
    }
}

class Square(private val squarePosition: SquarePosition, private val sudoku: Sudoku) : Group {
    override fun values(): Sequence<CellValue> {
        return PositionInSquare.all
                .map { innerPosition ->
                    sudoku[
                            squarePosition.rowIndex * 3 + innerPosition.rowIndex,
                            squarePosition.columnIndex * 3 + innerPosition.columnIndex
                    ]
                }
    }

    override fun cells(): Sequence<Cell> {
        return sudoku.cells.asSequence()
                .filter { (position, _) ->
                    position.squarePosition == squarePosition
                }
                .map { (_, cell) ->
                    cell
                }
    }

    fun additionalValues(positionInSquare: SquarePosition): Sequence<CellValue> {
        return SquarePosition.all
                .filter {
                    it.rowIndex != positionInSquare.rowIndex || it.columnIndex != positionInSquare.columnIndex
                }
                .map { innerPosition ->
                    sudoku[
                            squarePosition.rowIndex * 3 + innerPosition.rowIndex,
                            squarePosition.columnIndex * 3 + innerPosition.columnIndex
                    ]
                }
    }
}