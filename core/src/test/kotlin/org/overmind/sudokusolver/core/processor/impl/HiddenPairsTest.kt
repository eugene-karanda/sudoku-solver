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

@DisplayName("HiddenPairs")
class HiddenPairsTest {
    private var subject: HiddenPairs by notNull()

    @BeforeEach
    fun setUp() {
        this.subject = HiddenPairs()
    }

    @Nested
    @DisplayName("update")
    inner class Process {
        @Test
        fun `should lose all others candidates in sub-group if candidates occur only in this sub-group of group`() {
            val sudoku = Sudoku.fromFile(filepath("sudoku.txt"))

            assertThat(subject.process(sudoku))
                    .isEqualTo(ProcessResult {
                        CandidatesLose(5) at Position(5, 2)
                        CandidatesLose(1, 5) at Position(5, 7)

                        CandidatesLose(6) at Position(2, 3)
                        CandidatesLose(6) at Position(6, 3)

                        CandidatesLose(2, 5) at Position(3, 6)
                    })
        }
    }
}