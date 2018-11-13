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

fun <T> Collection<T>.powerSet(): Set<Set<T>> = powerSet(this, setOf(setOf()))

private tailrec fun <T> powerSet(left: Collection<T>, acc: Set<Set<T>>): Set<Set<T>> = when {
    left.isEmpty() -> acc
    else -> powerSet(
            left.drop(1),
            acc + acc.map {
                it + left.first()
            }
    )
}