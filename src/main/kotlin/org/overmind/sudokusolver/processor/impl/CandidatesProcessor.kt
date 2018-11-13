package org.overmind.sudokusolver.processor.impl

import org.overmind.sudokusolver.*
import org.overmind.sudokusolver.processor.ProcessResult
import org.overmind.sudokusolver.processor.SetupCandidates
import org.overmind.sudokusolver.processor.SudokuProcessor

class CandidatesProcessor : SudokuProcessor<RawCellValue, CellValue> {
    override fun process(sudoku: Sudoku<RawCellValue>) = ProcessResult.builder<RawCellValue, CellValue> {
        sudoku.cells.values.forEach { cell ->
            if (cell.value is EmptyCellValue) {
                val candidates = (1..9).toMutableSet()

                cell.neighbors()
                        .filterIsInstance<NumberCellValue>()
                        .map(NumberCellValue::number)
                        .forEach {
                            candidates.remove(it)
                        }

                SetupCandidates(candidates) at cell.position
            }
        }
    }
}