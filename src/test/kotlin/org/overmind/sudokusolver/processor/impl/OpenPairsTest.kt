package org.overmind.sudokusolver.processor.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.overmind.sudokusolver.Position
import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.filepath
import org.overmind.sudokusolver.processor.CandidatesLose
import org.overmind.sudokusolver.processor.ProcessResult
import kotlin.properties.Delegates.notNull

@DisplayName("OpenPairs")
class OpenPairsTest {
    private var subject: OpenPairs by notNull()

    @BeforeEach
    fun setUp() {
        this.subject = OpenPairs()
    }

    @Nested
    @DisplayName("process")
    inner class Process {
        @Test
        fun `should lose candidates in each group where occur subgroup of size more than candidates in this group`() {
            val sudoku = Sudoku.fromFile(filepath("/sudoku.txt"))

            assertThat(subject.process(sudoku))
                    .isEqualTo(ProcessResult.builder {
                        CandidatesLose(5) at Position(4, 1)
                        CandidatesLose(5) at Position(4, 2)

                        CandidatesLose(5, 6) at Position(4, 1)

                        CandidatesLose(5) at Position(5, 2)
                        CandidatesLose(2, 6, 9) at Position(6, 2)
                        CandidatesLose(2, 6) at Position(7, 2)

                        CandidatesLose(5, 6) at Position(0, 8)

                        CandidatesLose(5) at Position(3, 6)
                        CandidatesLose(1, 5) at Position(4, 6)
                        CandidatesLose(1, 5) at Position(5, 7)

                        CandidatesLose(2, 5) at Position(3, 6)
                        CandidatesLose(1, 5) at Position(5, 7)
                    })
        }
    }
}