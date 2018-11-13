package org.overmind.sudokusolver.proccessor.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.filepath
import org.overmind.sudokusolver.processor.impl.CandidatesProcessor

@DisplayName("CandidatesProcessor")
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
        fun `should calculate candidates for each empty cell`() {
            val rawSudoku = Sudoku.rawFromFile(filepath("/raw-sudoku.txt"))
            val sudoku = Sudoku.fromFile(filepath("/sudoku.txt"))

            assertThat(subject.process(rawSudoku))
                    .isEqualTo(sudoku)
        }
    }
}