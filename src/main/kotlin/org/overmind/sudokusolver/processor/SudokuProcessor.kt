package org.overmind.sudokusolver.processor

import org.overmind.sudokusolver.CellValue
import org.overmind.sudokusolver.Sudoku

interface SudokuProcessor {
    fun process(sudoku: Sudoku<CellValue>): ProcessResult
}