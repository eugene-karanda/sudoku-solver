package org.overmind.sudokusolver.core

import org.overmind.sudokusolver.core.processor.SudokuSolver
import org.overmind.sudokusolver.core.processor.impl.*

object ApplicationContext {
    val sudokuSolver = SudokuSolver(
            preProcessor = CandidatesProcessor(),
            processors = listOf(
                    Singletons(),
                    HiddenSingletons(),
                    OpenPairs(),
                    HiddenPairs()
            )
    )
}