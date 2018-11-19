package org.overmind.sudokusolver.core.processor

import org.overmind.sudokusolver.core.Sudoku

interface SudokuProcessor {
    fun process(sudoku: Sudoku): ProcessResult
}

interface SudokuPreProcessor : SudokuProcessor