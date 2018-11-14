package org.overmind.sudokusolver.processor.impl

/*
class CandidatesProcessor {
    fun process(sudoku: Sudoku<RawCellValue>) : Sudoku<CellValue> {
        return sudoku.map { cell ->
            val value = cell.value

            if (value is EmptyCellValue) {
                val candidates = (1..9).toMutableSet()

                cell.neighbors()
                        .filterIsInstance<NumberCellValue>()
                        .map(NumberCellValue::number)
                        .forEach {
                            candidates.remove(it)
                        }

                return@map CandidatesCellValue(candidates)
            } else {
                return@map value as CellValue
            }
        }
    }
}*/
