package org.overmind.sudokusolver.processor

import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.solved

class SudokuSolver(private val preProcessor: SudokuPreProcessor, private val processors: List<SudokuProcessor>) {

    fun solve(sudoku: Sudoku): Sudoku {
        var processedSudoku: Sudoku = let {
            val preProcessResult = preProcessor.process(sudoku)
            preProcessResult.update(sudoku)
        }

        while (!processedSudoku.solved) {
            processors
                    .asSequence()
                    .map { processor -> processor.process(processedSudoku) }
                    .firstOrNull { processResult -> processResult.notEmpty }
                    ?.let { processedSudoku = it.update(processedSudoku) }
                    ?: run { throw IllegalArgumentException("Sudoku can't be solved") }
        }

        return processedSudoku
    }
}