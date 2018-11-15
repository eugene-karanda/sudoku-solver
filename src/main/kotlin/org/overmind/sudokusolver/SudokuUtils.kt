package org.overmind.sudokusolver

infix fun Boolean.ifRun(block: () -> Unit) {
    if (this) {
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

fun <E : SudokuElement<*>> Sequence<E>.except(element: E): Sequence<E> {
    return this.filter {
        it.position != element.position
                || it.sudoku != element.sudoku
    }
}

fun <E : SudokuElement<*>> Sequence<E>.except(elements: Sequence<E>): Sequence<E> {
    return this.filter {
        !elements.any { element ->
            it.position == element.position
                    && it.sudoku == element.sudoku
        }
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