package org.overmind.sudokusolver.core.processor.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.overmind.sudokusolver.core.Position
import org.overmind.sudokusolver.core.Sudoku
import org.overmind.sudokusolver.core.filepath
import org.overmind.sudokusolver.core.processor.CandidatesLose
import org.overmind.sudokusolver.core.processor.ProcessResult
import kotlin.properties.Delegates.notNull

@DisplayName("LockedCandidate")
class LockedCandidateTest {
    private var subject: LockedCandidate by notNull()

    @BeforeEach
    fun setUp() {
        this.subject = LockedCandidate()
    }

    @Nested
    @DisplayName("update")
    inner class Process {
        @Test
        fun `should lose candidate in each group if candidate occur only in this sub-group of any square`() {
            val sudoku = Sudoku.fromFile(filepath("sudoku.txt"))

            assertThat(subject.process(sudoku))
                    .isEqualTo(ProcessResult {
                        CandidatesLose(9) at Position(0, 4)
                        CandidatesLose(9) at Position(1, 4)
                        CandidatesLose(4) at Position(2, 1)
                        CandidatesLose(2) at Position(2, 6)
                        CandidatesLose(2) at Position(6, 4)
                        CandidatesLose(2) at Position(7, 4)
                    })
        }
    }
}