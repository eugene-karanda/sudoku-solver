package org.overmind.sudokusolver

data class Update<V : RawCellValue>(val position: Position, val action: Action<V>)

interface Action<V : RawCellValue>

data class SetupCandidates(val candidates: Set<Int>): Action<RawCellValue> {
    constructor(vararg candidates: Int) : this(candidates.toSet())
}

data class NumberPut(val number: Int): Action<CellValue>

data class CandidatesLose(val candidates: Set<Int>): Action<CellValue> {
    constructor(vararg candidates: Int) : this(candidates.toSet())
}

data class ProcessResult<V : RawCellValue, R : RawCellValue>(val updates: Set<Update<V>>) {
    companion object {
        fun <V : RawCellValue, R : RawCellValue> builder(block: Builder<V>.() -> Unit): ProcessResult<V, R> {
            val updates = mutableSetOf<Update<V>>()

            object : Builder<V> {
                override fun Action<V>.at(position: Position) {
                    updates.add(Update(position, this))
                }

            }.also(block)

            return ProcessResult(updates)
        }
    }

    interface Builder<V : RawCellValue> {
        infix fun Action<V>.at(position: Position)
    }
}