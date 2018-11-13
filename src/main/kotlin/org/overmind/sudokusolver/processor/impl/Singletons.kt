package org.overmind.sudokusolver.processor.impl

import org.overmind.sudokusolver.CandidatesCellValue
import org.overmind.sudokusolver.CellValue
import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.processor.NumberPut
import org.overmind.sudokusolver.processor.ProcessResult
import org.overmind.sudokusolver.processor.SudokuProcessor

class Singletons : SudokuProcessor {
    override fun process(sudoku: Sudoku<CellValue>) = ProcessResult.builder {
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