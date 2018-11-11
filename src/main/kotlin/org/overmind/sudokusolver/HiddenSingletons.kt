package org.overmind.sudokusolver

class HiddenSingletons {
    fun process(sudoku: Sudoku<CellValue>) = ProcessResult.builder<CellValue, CellValue> {
        sudoku.cells.values.forEach { cell ->
            cell.value.run {
                if (this is CandidatesCellValue) {
                    candidates.forEach { candidate ->
                        cell.neighborsGroups()
                                .any { group ->
                                    !group.hasCandidate(candidate)
                                }
                                .ifRun {
                                    NumberPut(candidate) at cell.position
                                }
                    }
                }
            }
        }
    }
}