package org.overmind.sudokusolver

import org.overmind.sudokusolver.processor.SudokuSolver
import org.overmind.sudokusolver.processor.impl.CandidatesProcessor
import org.overmind.sudokusolver.processor.impl.HiddenPairs
import org.overmind.sudokusolver.processor.impl.HiddenSingletons
import org.overmind.sudokusolver.processor.impl.OpenPairs
import org.overmind.sudokusolver.processor.impl.Singletons

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