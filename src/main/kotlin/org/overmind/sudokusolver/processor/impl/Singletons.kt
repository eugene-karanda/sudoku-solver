package org.overmind.sudokusolver.processor.impl

import org.overmind.sudokusolver.CandidatesCell
import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.processor.NumberPut
import org.overmind.sudokusolver.processor.ProcessResult
import org.overmind.sudokusolver.processor.SudokuProcessor

class Singletons : SudokuProcessor {
    override fun process(sudoku: Sudoku) = ProcessResult.builder {
        sudoku.cells
                .asSequence()
                .filterIsInstance<CandidatesCell>()
                .filter { it.candidates.size == 1 }
                .forEach { NumberPut(it.candidates.first()) at it.position }
    }
}