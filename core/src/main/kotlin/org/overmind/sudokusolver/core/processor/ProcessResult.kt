package org.overmind.sudokusolver.core.processor

import org.overmind.sudokusolver.core.CandidatesCell
import org.overmind.sudokusolver.core.CandidatesCellValue
import org.overmind.sudokusolver.core.Cell
import org.overmind.sudokusolver.core.CellValue
import org.overmind.sudokusolver.core.NumberCellValue
import org.overmind.sudokusolver.core.Position
import org.overmind.sudokusolver.core.Sudoku

interface Action {
    fun perform(cellValue: Cell): CellValue

    fun merge(another: Action): Action
}

data class SetupCandidates(val candidates: Set<Int>) : Action {
    constructor(vararg candidates: Int) : this(candidates.toSet())

    override fun perform(cellValue: Cell): CandidatesCellValue {
        return CandidatesCellValue(candidates)
    }

    override fun merge(another: Action): Action {
        throw IllegalArgumentException("SetupCandidates can't be merged with ${another::class}")
    }
}

data class NumberPut(val number: Int) : Action {
    override fun perform(cellValue: Cell): NumberCellValue {
        return NumberCellValue(number)
    }

    override fun merge(another: Action): Action {
        if (another is CandidatesLose) {
            return this
        }

        throw IllegalArgumentException("NumberPut can't be merged with ${another::class}")
    }
}

data class CandidatesLose(val candidates: Set<Int>) : Action {
    constructor(vararg candidates: Int) : this(candidates.toSet())

    override fun perform(cellValue: Cell): CellValue {
        if (cellValue is CandidatesCell) {
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

    override fun merge(another: Action): Action {
        if (another is NumberPut) {
            return another.merge(this)
        }

        if (another is CandidatesLose) {
            return CandidatesLose(candidates + another.candidates)
        }

        throw IllegalArgumentException("CandidatesLose can't be merged with ${another::class}")
    }
}

data class Update(val position: Position, val action: Action)

data class ProcessResult(val updates: Set<Update>) {
    val notEmpty: Boolean
        get() = updates.isNotEmpty()

    interface Builder {
        infix fun Action.at(position: Position)
    }

    companion object {
        operator fun invoke(block: Builder.() -> Unit): ProcessResult {
            val updates = mutableSetOf<Update>()

            object : Builder {
                override fun Action.at(position: Position) {
                    updates.add(Update(position, this))
                }

            }.also(block)

            return ProcessResult(updates)
        }
    }

    fun update(sudoku: Sudoku): Sudoku {
        return sudoku.map { cell ->
            updates
                    .asSequence()
                    .filter { (position, _) ->
                        cell.position == position
                    }
                    .map(Update::action)
                    .fold(null as Action?) { left, right ->
                        left?.merge(right) ?: right
                    }?.perform(cell)
        }
    }
}