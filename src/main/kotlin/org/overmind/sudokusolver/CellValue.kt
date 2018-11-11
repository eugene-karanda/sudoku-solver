package org.overmind.sudokusolver

sealed class RawCellValue

sealed class CellValue : RawCellValue()

object EmptyCellValue : RawCellValue()

data class NumberCellValue(val number: Int) : CellValue()

data class CandidatesCellValue constructor(val candidates: Set<Int>): CellValue() {
    constructor(vararg candidates: Int) : this(candidates.toSet())
}