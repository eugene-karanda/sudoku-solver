package org.overmind.sudokusolver.processor.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.overmind.sudokusolver.Position
import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.filepath
import org.overmind.sudokusolver.processor.ProcessResult
import org.overmind.sudokusolver.processor.SetupCandidates
import kotlin.properties.Delegates.notNull


@DisplayName("CandidatesProcessor")
class CandidatesProcessorTest {
    private var subject: CandidatesProcessor by notNull()

    @BeforeEach
    fun setUp() {
        this.subject = CandidatesProcessor()
    }

    @Nested
    @DisplayName("update")
    inner class Process {
        @Test
        fun `should setup candidates for each empty cell`() {
            val sudoku = Sudoku.rawFromFile(filepath("raw-sudoku.txt"))

            assertThat(subject.process(sudoku))
                    .isEqualTo(
                            ProcessResult.builder {
                                SetupCandidates(2, 5, 6, 9) at Position(0, 2)
                                SetupCandidates(6, 9) at Position(0, 3)
                                SetupCandidates(3, 5, 6, 9) at Position(0, 4)
                                SetupCandidates(3, 5, 6) at Position(0, 7)
                                SetupCandidates(2, 5, 6) at Position(0, 8)

                                SetupCandidates(1, 5, 6, 9) at Position(1, 1)
                                SetupCandidates(5, 6, 9) at Position(1, 2)
                                SetupCandidates(5, 6, 7, 8, 9) at Position(1, 4)
                                SetupCandidates(1, 5, 7, 8) at Position(1, 6)
                                SetupCandidates(1, 5, 6, 7, 8) at Position(1, 7)
                                SetupCandidates(1, 5, 6) at Position(1, 8)

                                SetupCandidates(1, 4, 6) at Position(2, 0)
                                SetupCandidates(1, 2, 4, 5, 6) at Position(2, 1)
                                SetupCandidates(2, 5, 6) at Position(2, 2)
                                SetupCandidates(6, 7, 8) at Position(2, 3)
                                SetupCandidates(3, 5, 6, 7, 8) at Position(2, 4)
                                SetupCandidates(3, 5, 8) at Position(2, 5)
                                SetupCandidates(1, 2, 3, 5, 7, 8) at Position(2, 6)
                                SetupCandidates(1, 3, 5, 6, 7, 8) at Position(2, 7)

                                SetupCandidates(7) at Position(3, 0)
                                SetupCandidates(3, 5) at Position(3, 1)
                                SetupCandidates(8) at Position(3, 3)
                                SetupCandidates(2, 5, 8) at Position(3, 4)
                                SetupCandidates(2, 3, 5, 7) at Position(3, 6)

                                SetupCandidates(4, 5, 6) at Position(4, 1)
                                SetupCandidates(5, 6) at Position(4, 2)
                                SetupCandidates(1, 2, 5) at Position(4, 4)
                                SetupCandidates(1, 2, 5) at Position(4, 6)
                                SetupCandidates(1, 5) at Position(4, 7)

                                SetupCandidates(3, 5, 7) at Position(5, 2)
                                SetupCandidates(1, 5, 9) at Position(5, 4)
                                SetupCandidates(5) at Position(5, 5)
                                SetupCandidates(1, 3, 5, 7) at Position(5, 7)
                                SetupCandidates(1, 5) at Position(5, 8)

                                SetupCandidates(1, 2, 3, 6, 9) at Position(6, 1)
                                SetupCandidates(2, 3, 6, 7, 8, 9) at Position(6, 2)
                                SetupCandidates(6, 7, 8) at Position(6, 3)
                                SetupCandidates(2, 3, 4, 6, 7, 8) at Position(6, 4)
                                SetupCandidates(2, 3, 8) at Position(6, 5)
                                SetupCandidates(1, 8, 9) at Position(6, 6)
                                SetupCandidates(1, 4, 6, 8) at Position(6, 7)
                                SetupCandidates(1, 6) at Position(6, 8)

                                SetupCandidates(6, 7) at Position(7, 0)
                                SetupCandidates(2, 6) at Position(7, 1)
                                SetupCandidates(2, 6, 7, 8) at Position(7, 2)
                                SetupCandidates(2, 4, 6, 7, 8) at Position(7, 4)
                                SetupCandidates(5, 8) at Position(7, 6)
                                SetupCandidates(4, 5, 6, 8) at Position(7, 7)

                                SetupCandidates(1, 6) at Position(8, 0)
                                SetupCandidates(1, 3, 6, 9) at Position(8, 1)
                                SetupCandidates(3, 6, 8) at Position(8, 4)
                                SetupCandidates(3, 8) at Position(8, 5)
                                SetupCandidates(1, 8, 9) at Position(8, 6)
                            }
                    )
        }
    }
}