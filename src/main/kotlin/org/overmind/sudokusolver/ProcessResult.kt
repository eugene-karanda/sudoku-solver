package org.overmind.sudokusolver

interface Action<V : RawCellValue> {
    fun perform(cellValue: V): V

    fun merge(another: Action<V>): Action<V> = TODO()
}

data class SetupCandidates(val candidates: Set<Int>) : Action<RawCellValue> {
    constructor(vararg candidates: Int) : this(candidates.toSet())

    override fun perform(cellValue: RawCellValue): CandidatesCellValue {
        return CandidatesCellValue(candidates)
    }
}

data class NumberPut(val number: Int) : Action<CellValue> {
    override fun perform(cellValue: CellValue): NumberCellValue {
        return NumberCellValue(number)
    }
}

data class CandidatesLose(val candidates: Set<Int>) : Action<CandidatesCellValue> {
    constructor(vararg candidates: Int) : this(candidates.toSet())

    override fun perform(cellValue: CandidatesCellValue): CandidatesCellValue {
        return cellValue.candidates
                .asSequence()
                .filter {
                    it !in candidates
                }
                .toSet()
                .run(::CandidatesCellValue)

    }
}

data class Update<V : RawCellValue>(val position: Position, val action: Action<V>)

data class ProcessResult<V : RawCellValue, R : V>(val updates: Set<Update<V>>) {
    interface Builder<V : RawCellValue> {
        infix fun Action<V>.at(position: Position)
    }

    companion object {
        fun <V : RawCellValue, R : V> builder(block: Builder<V>.() -> Unit): ProcessResult<V, R> {
            val updates = mutableSetOf<Update<V>>()

            object : Builder<V> {
                override fun Action<V>.at(position: Position) {
                    updates.add(Update(position, this))
                }

            }.also(block)

            return ProcessResult(updates)
        }
    }

    /*fun proceed(sudoku: Sudoku<V>): Sudoku<R> {
        sudoku.cells.values.map { cell ->
            updates
                    .filter { (position, _) ->
                        cell.position == position
                    }
                    .map(Update<V>::action)
                    .reduce(Action<V>::merge)
        }
    }*/
}