package org.overmind.sudokusolver.processor

import org.overmind.sudokusolver.*

interface Action {
    fun perform(cellValue: CellValue): CellValue

    fun merge(another: Action): Action
}

data class NumberPut(val number: Int) : Action {
    override fun perform(cellValue: CellValue): NumberCellValue {
        return NumberCellValue(number)
    }

    override fun merge(another: Action): Action {
        if(another is CandidatesLose) {
            return this
        }

        throw IllegalArgumentException("NumberPut can't be merged with ${another::class}")
    }
}

data class CandidatesLose(val candidates: Set<Int>) : Action {
    override fun merge(another: Action): Action {
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

data class Update(val position: Position, val action: Action)

data class ProcessResult(val updates: Set<Update>) {
    interface Builder {
        infix fun Action.at(position: Position)
    }

    companion object {
        fun  builder(block: Builder.() -> Unit): ProcessResult {
            val updates = mutableSetOf<Update>()

            object : Builder {
                override fun Action.at(position: Position) {
                    updates.add(Update(position, this))
                }

            }.also(block)

            return ProcessResult(updates)
        }
    }

    fun process(sudoku: Sudoku): Sudoku {
        return sudoku.map { cell ->
            updates
                    .asSequence()
                    .filter { (position, _) ->
                        cell.position == position
                    }
                    .map(Update::action)
                    .fold(null as Action?) { left, right ->
                        left?.merge(right)
                    } ?.perform(cell.value) ?: cell.value
        }
    }
}