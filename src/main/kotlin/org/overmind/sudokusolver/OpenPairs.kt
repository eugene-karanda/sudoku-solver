package org.overmind.sudokusolver

class OpenPairs {
    fun process(sudoku: Sudoku<CellValue>) = ProcessResult.builder<CellValue, CellValue> {
        sudoku.columns.forEach { column ->
            val candidatesMap = column.values()
                    .mapIndexed { index, value ->
                        index to value
                    }
                    .filter { (_, value) ->
                        value is CandidatesCellValue
                    }
                    .associateBy(
                            { it.first },
                            { (it.second as CandidatesCellValue).candidates }
                    )
                    .filterValues {
                        it.size == 2
                    }


        }

    }

    private fun twoCandidatesValues(column: Sudoku<CellValue>.Column): Sequence<CandidatesCellValue> {
        return column.values()
                .filterIsInstance<CandidatesCellValue>()
                .filter {
                    it.candidates.size == 2
                }
    }
}