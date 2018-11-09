package org.overmind.sudokusolver

class HiddenSingletons {
    fun process(sudoku: Sudoku<Cell>): Sudoku<Cell> {
        return sudoku.mapIndexed { cellRowIndex, cellColumnIndex, cell ->
            if (cell is CandidatesCell) {
                cell.candidates.forEach { candidate ->
                    val singlePredicate: (Cell) -> Boolean = single@{ anotherCell ->
                        if (anotherCell is CandidatesCell) {
                            return@single candidate in anotherCell.candidates
                        }

                        return@single false
                    }

                    val single = sudoku.rowNeighborsOfCell(cellRowIndex, cellColumnIndex).none(singlePredicate) ||
                            sudoku.columnNeighborsOfCell(cellRowIndex, cellColumnIndex).none(singlePredicate) ||
                            sudoku.squareNeighborsOfCell(cellRowIndex, cellColumnIndex).none(singlePredicate)

                    if(single) {
                        return@mapIndexed NumberCell(candidate)
                    }
                }
            }

            return@mapIndexed cell
        }
    }
}