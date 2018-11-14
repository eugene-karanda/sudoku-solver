package org.overmind.sudokusolver.processor.impl

import org.overmind.sudokusolver.CandidatesCellValue
import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.hasCandidate
import org.overmind.sudokusolver.ifRun
import org.overmind.sudokusolver.processor.NumberPut
import org.overmind.sudokusolver.processor.ProcessResult
import org.overmind.sudokusolver.processor.SudokuProcessor

class HiddenSingletons : SudokuProcessor {
    override fun process(sudoku: Sudoku) = ProcessResult.builder {
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