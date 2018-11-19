package org.overmind.sudokusolver

interface CellValue {
    fun toCell(position: Position, sudoku: Sudoku): Cell
}

object EmptyCellValue : CellValue {
    override fun toCell(position: Position, sudoku: Sudoku): Cell {
        return EmptyCell(position, sudoku, this)
    }
}

data class NumberCellValue(val number: Int) : CellValue {
    override fun toCell(position: Position, sudoku: Sudoku): Cell {
        return NumberCell(number, position, sudoku, this)
    }
}

data class CandidatesCellValue(val candidates: Set<Int>) : CellValue {
    constructor(vararg candidates: Int) : this(candidates.toSet())

    override fun toCell(position: Position, sudoku: Sudoku): Cell {
        return CandidatesCell(candidates, position, sudoku, this)
    }
}