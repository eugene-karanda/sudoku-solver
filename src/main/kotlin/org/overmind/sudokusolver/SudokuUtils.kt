package org.overmind.sudokusolver

infix fun Boolean.ifRun(block: () -> Unit) {
    if(this) {
        block()
    }
}

fun <V : CellValue> Sequence<V>.hasCandidate(candidate: Int): Boolean {
    return this.any {
        return@any when (it) {
            is CandidatesCellValue -> candidate in it.candidates
            is NumberCellValue -> candidate == it.number
            else -> false
        }
    }
}

fun <V : CellValue> Sequence<V>.except(cell: V): Sequence<V> {
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