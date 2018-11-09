package org.overmind.sudokusolver

class Singletons {
    fun process(sudoku: Sudoku<Cell>): Sudoku<Cell> {
        return sudoku.map { cell ->
            if(cell is CandidatesCell) {
                with(cell.candidates) {
                    if(size == 1) {
                        return@map NumberCell(first())
                    }
                }
            }

            return@map cell
        }
    }
}