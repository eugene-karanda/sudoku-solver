package org.overmind.sudokusolver

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Candidates Processor")
class CandidatesProcessorTest {
    private lateinit var subject: CandidatesProcessor

    @BeforeEach
    fun setUp() {
        this.subject = CandidatesProcessor()
    }

    @Nested
    @DisplayName("process")
    inner class Process {
        @Test
        fun `should return sudoku with candidate cells instead of empty cells`() {
            val rawSudoku = Sudoku.rawFromFile(filepath("/raw-sudoku.txt"))
            val sudoku = Sudoku.fromFile(filepath("/sudoku.txt"))

            assertThat(subject.process(rawSudoku))
                    .isEqualTo(sudoku)
        }
    }
}