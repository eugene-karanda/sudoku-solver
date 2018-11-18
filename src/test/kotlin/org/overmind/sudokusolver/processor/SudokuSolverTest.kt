package org.overmind.sudokusolver.processor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.filepath
import org.overmind.sudokusolver.processor.impl.CandidatesProcessor
import org.overmind.sudokusolver.processor.impl.HiddenPairs
import org.overmind.sudokusolver.processor.impl.HiddenSingletons
import org.overmind.sudokusolver.processor.impl.LockedCandidate
import org.overmind.sudokusolver.processor.impl.OpenPairs
import org.overmind.sudokusolver.processor.impl.Singletons
import org.overmind.sudokusolver.solved
import kotlin.properties.Delegates.notNull

@DisplayName("SudokuSolver")
class SudokuSolverTest {

    private var subject: SudokuSolver by notNull()

    private var sudoku: Sudoku by notNull()

    @BeforeEach
    fun setUp() {
        this.sudoku = Sudoku.fromFile(filepath("sudoku.txt"))

        this.subject = SudokuSolver(
                preProcessor = CandidatesProcessor(),
                processors = listOf(
                        Singletons(),
                        HiddenSingletons(),
                        LockedCandidate(),
                        OpenPairs(),
                        HiddenPairs()
                )
        )
    }

    @Nested
    @DisplayName("solve")
    inner class Solve {
        @Test
        fun `should call pre-processor first`() {
            val updatedSudoku = subject.solve(sudoku)

            assertThat(updatedSudoku.solved)
                    .isTrue()
        }
    }
}