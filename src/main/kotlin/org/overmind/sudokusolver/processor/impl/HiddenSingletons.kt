package org.overmind.sudokusolver.processor.impl

import org.overmind.sudokusolver.*
import org.overmind.sudokusolver.processor.NumberPut
import org.overmind.sudokusolver.processor.ProcessResult
import org.overmind.sudokusolver.processor.SudokuProcessor

class HiddenSingletons : SudokuProcessor<CellValue, CellValue> {
    override fun process(sudoku: Sudoku<CellValue>) = ProcessResult.builder<CellValue, CellValue> {
        sudoku.cells.values.forEach { cell ->
            cell.value.run {
                if (this is CandidatesCellValue) {
                    candidates.forEach { candidate ->
                        cell.neighborsGroups()
                                .any { group ->
                                    !group.hasCandidate(candidate)
                                }
                                .ifRun {
                                    NumberPut(candidate) at cell.position
                                }
                    }
                }
            }
        }
    }
}