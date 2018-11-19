package org.overmind.sudokusolver.core.processor.impl

import org.overmind.sudokusolver.core.CandidatesCell
import org.overmind.sudokusolver.core.Sudoku
import org.overmind.sudokusolver.core.hasCandidate
import org.overmind.sudokusolver.core.ifRun
import org.overmind.sudokusolver.core.processor.NumberPut
import org.overmind.sudokusolver.core.processor.ProcessResult
import org.overmind.sudokusolver.core.processor.SudokuProcessor

class HiddenSingletons : SudokuProcessor {
    override fun process(sudoku: Sudoku) = ProcessResult {
        sudoku.cells
                .asSequence()
                .filterIsInstance<CandidatesCell>()
                .forEach { cell ->
                    cell.candidates.forEach { candidate ->
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