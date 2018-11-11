package org.overmind.sudokusolver

class CandidatesProcessor {
    fun process(rawSudoku: Sudoku<RawCellValue>) = ProcessResult.builder<RawCellValue, CellValue> {
        rawSudoku.cells.values.forEach { cell ->
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