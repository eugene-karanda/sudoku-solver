package org.overmind.sudokusolver

class CandidatesProcessor {
    fun process(rawSudoku: Sudoku<RawCell>): Sudoku<Cell> {
        val matrix = List(9) matrixList@{ rowIndex ->
            return@matrixList List(9) rowList@{ columnIndex ->
                val cell = rawSudoku[rowIndex, columnIndex]
                if (cell is NumberCell) {
                    return@rowList cell as Cell
                }

                val candidates = (1..9).toSortedSet()

                rawSudoku.neighborsOfCell(rowIndex, columnIndex)
                        .forEach { anotherCell ->
                            if (anotherCell is NumberCell) {
                                candidates.remove(anotherCell.number)
                            }
                        }

                return@rowList CandidatesCell(candidates)
            }
        }

        return Sudoku(matrix)
    }
}