package org.overmind.sudokusolver

infix fun Boolean.ifRun(block: () -> Unit) {
    if(this) {
        block()
    }
}

fun <C : RawCellValue> Sequence<C>.hasCandidate(candidate: Int): Boolean {
    return this.any {
        return@any when (it) {
            is CandidatesCellValue -> candidate in it.candidates
            is NumberCellValue -> candidate == it.number
            else -> false
        }
    }
}

fun <C : RawCellValue> Sequence<C>.except(cell: C): Sequence<C> {
    return this.filter {
        it !== cell
    }
}