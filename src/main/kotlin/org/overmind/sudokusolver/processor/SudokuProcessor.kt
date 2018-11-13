package org.overmind.sudokusolver.processor

import org.overmind.sudokusolver.RawCellValue
import org.overmind.sudokusolver.Sudoku

interface SudokuProcessor<V : RawCellValue, R : V> {
    fun process(sudoku: Sudoku<V>): ProcessResult<V, R>
}