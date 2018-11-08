package org.overmind.sudokusolver

sealed class Cell

object EmptyCell : Cell()

data class NumberCell(val number: Int) : Cell()

data class CandidatesCell(val candidates: Set<Int>): Cell()