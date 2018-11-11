package org.overmind.sudokusolver

class Singletons {
    fun process(sudoku: Sudoku<CellValue>) = ProcessResult.builder<CellValue, CellValue> {
        sudoku.cells.values.forEach { cell ->
            cell.value.run {
                if (this is CandidatesCellValue) {
                    with(candidates) {
                        if (size == 1) {
                            NumberPut(candidates.first()) at cell.position
                        }
                    }
                }
            }
        }
    }
}