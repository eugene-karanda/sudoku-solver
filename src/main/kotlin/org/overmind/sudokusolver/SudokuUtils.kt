package org.overmind.sudokusolver

infix fun Boolean.ifRun(block: () -> Unit) {
    if(this) {
        block()
    }
}

fun Sequence<Cell>.hasCandidate(candidate: Int): Boolean {
    return this.any {
        when (it) {
            is CandidatesCell -> candidate in it.candidates
            else -> false
        }
    }
}

fun Sequence<Cell>.except(cell: Cell): Sequence<Cell> {
    return this.filter {
        it.position != cell.position
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