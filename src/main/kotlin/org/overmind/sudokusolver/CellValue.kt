package org.overmind.sudokusolver

sealed class CellValue

data class NumberCellValue(val number: Int) : CellValue()

data class CandidatesCellValue constructor(val candidates: Set<Int>): CellValue() {
    constructor(vararg candidates: Int) : this(candidates.toSet())
}