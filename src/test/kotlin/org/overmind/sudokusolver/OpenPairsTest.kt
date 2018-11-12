package org.overmind.sudokusolver

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("OpenPairs")
class OpenPairsTest {
    private lateinit var subject: OpenPairs

    @BeforeEach
    fun setUp() {
        this.subject = OpenPairs()
    }

    @Nested
    @DisplayName("process")
    inner class Process {
        @Test
        fun `should remove each candidate from row(column) if it occur only in row(column) of square`() { //TODO rename
            val sudoku = Sudoku.fromFile(filepath("/sudoku.txt"))

            Assertions.assertThat(subject.process(sudoku))
                    .isEqualTo(ProcessResult.builder<CellValue, CellValue> {
                        CandidatesLose(2) at Position(6, 4)
                        CandidatesLose(2) at Position(7, 4)
                    })
        }
    }
}