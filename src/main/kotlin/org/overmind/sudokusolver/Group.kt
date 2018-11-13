package org.overmind.sudokusolver

interface Group<V : RawCellValue> {
    fun values(): Sequence<V>

    fun cells(): Sequence<Cell<V>>
}

class Row<V : RawCellValue>(private val rowIndex: Int, private val sudoku: Sudoku<V>) : Group<V> {
    override fun values(): Sequence<V> {
        return sudoku.matrix[rowIndex]
                .asSequence()
    }

    override fun cells(): Sequence<Cell<V>> {
        return sudoku.cells.asSequence()
                .filter { (position, _) ->
                    position.rowIndex == rowIndex
                }
                .map { (_, cell) ->
                    cell
                }
    }
}

class Column<V : RawCellValue>(private val columnIndex: Int, private val sudoku: Sudoku<V>) : Group<V> {
    override fun values(): Sequence<V> {
        return ((0 until 9))
                .asSequence()
                .map { innerRowIndex ->
                    sudoku[innerRowIndex, columnIndex]
                }
    }

    override fun cells() : Sequence<Cell<V>> {
        return sudoku.cells.asSequence()
                .filter { (position, _) ->
                    position.columnIndex == columnIndex
                }
                .map { (_, cell) ->
                    cell
                }
    }
}

class Square<V : RawCellValue>(private val squarePosition: SquarePosition, private val sudoku: Sudoku<V>) : Group<V> {
    override fun values(): Sequence<V> {
        return PositionInSquare.all
                .map { innerPosition ->
                    sudoku[
                            squarePosition.rowIndex * 3 + innerPosition.rowIndex,
                            squarePosition.columnIndex * 3 + innerPosition.columnIndex
                    ]
                }
    }

    override fun cells(): Sequence<Cell<V>> {
        return sudoku.cells.asSequence()
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
                    sudoku[
                            squarePosition.rowIndex * 3 + innerPosition.rowIndex,
                            squarePosition.columnIndex * 3 + innerPosition.columnIndex
                    ]
                }
    }
}