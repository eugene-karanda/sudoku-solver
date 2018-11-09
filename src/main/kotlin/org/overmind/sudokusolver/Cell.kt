package org.overmind.sudokusolver

sealed class RawCell

sealed class Cell : RawCell()

object EmptyCell : RawCell()

data class NumberCell(val number: Int) : Cell()

data class CandidatesCell internal constructor(val candidates: Set<Int>): Cell() {
    constructor(vararg candidates: Int) : this(sortedSetOf(*candidates.toTypedArray()))
}