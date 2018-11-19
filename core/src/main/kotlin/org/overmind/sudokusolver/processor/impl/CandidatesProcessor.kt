package org.overmind.sudokusolver.processor.impl

import org.overmind.sudokusolver.EmptyCell
import org.overmind.sudokusolver.NumberCell
import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.processor.ProcessResult
import org.overmind.sudokusolver.processor.SetupCandidates
import org.overmind.sudokusolver.processor.SudokuPreProcessor

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