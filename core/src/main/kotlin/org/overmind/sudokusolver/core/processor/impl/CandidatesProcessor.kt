package org.overmind.sudokusolver.core.processor.impl

import org.overmind.sudokusolver.core.EmptyCell
import org.overmind.sudokusolver.core.NumberCell
import org.overmind.sudokusolver.core.Sudoku
import org.overmind.sudokusolver.core.processor.ProcessResult
import org.overmind.sudokusolver.core.processor.SetupCandidates
import org.overmind.sudokusolver.core.processor.SudokuPreProcessor

class CandidatesProcessor : SudokuPreProcessor {
    override fun process(sudoku: Sudoku) = ProcessResult {
        sudoku.cells
                .asSequence()
                .filterIsInstance<EmptyCell>()
                .forEach {cell ->
                    val occurredCandidates = cell.neighbors()
                            .filterIsInstance<NumberCell>()
                            .map(NumberCell::number)
                            .toSet()

                    SetupCandidates((1.. 9).toSet() - occurredCandidates) at cell.position
                }
    }
}