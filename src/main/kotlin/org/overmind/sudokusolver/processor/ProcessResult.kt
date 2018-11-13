package org.overmind.sudokusolver.processor

import org.overmind.sudokusolver.*

sealed class Action<V : RawCellValue> {
    abstract fun perform(cellValue: V): V

    abstract fun merge(another: Action<*>): Action<V>
}

data class SetupCandidates(val candidates: Set<Int>) : Action<RawCellValue>() {
    constructor(vararg candidates: Int) : this(candidates.toSet())

    override fun perform(cellValue: RawCellValue): CandidatesCellValue {
        return CandidatesCellValue(candidates)
    }

    override fun merge(another: Action<out RawCellValue>): Action<RawCellValue> {
        throw IllegalArgumentException("SetupCandidates can't be merged with anything")
    }
}

data class NumberPut(val number: Int) : Action<CellValue>() {
    override fun perform(cellValue: CellValue): NumberCellValue {
        return NumberCellValue(number)
    }

    override fun merge(another: Action<*>): Action<CellValue> {
        if(another is CandidatesLose) {
            return this
        }

        throw IllegalArgumentException("NumberPut can't be merged with ${another::class}")
    }
}

data class CandidatesLose(val candidates: Set<Int>) : Action<CellValue>() {
    override fun merge(another: Action<*>): Action<CellValue> {
        if(another is NumberPut) {
            return another.merge(this)
        }

        if(another is CandidatesLose) {
            return CandidatesLose(candidates + another.candidates)
        }

        throw IllegalArgumentException("CandidatesLose can't be merged with ${another::class}")
    }

    constructor(vararg candidates: Int) : this(candidates.toSet())

    override fun perform(cellValue: CellValue): CellValue {
        if(cellValue is CandidatesCellValue) {
            return cellValue.candidates
                    .asSequence()
                    .filter {
                        it !in candidates
                    }
                    .toSet()
                    .run(::CandidatesCellValue)
        }

        throw IllegalArgumentException("perform can't be executed on ${cellValue::class}")
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

    fun process(sudoku: Sudoku<V>): Sudoku<R> {
        @Suppress("UNCHECKED_CAST")
        return sudoku.map { cell ->
            updates
                    .asSequence()
                    .filter { (position, _) ->
                        cell.position == position
                    }
                    .map(Update<V>::action)
                    .fold(null as Action<V>?) { left, right ->
                        left?.merge(right)
                    } ?.perform(cell.value) ?: cell.value
        } as Sudoku<R>
    }
}