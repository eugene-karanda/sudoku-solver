package org.overmind.sudokusolver

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Singletons")
class SingletonsTest {
    private lateinit var subject: Singletons

    @BeforeEach
    fun setUp() {
        this.subject = Singletons()
    }

    @Nested
    @DisplayName("process")
    inner class Process {
        @Test
        fun `should replace all cells with single candidate to cells with number`() {
            val sudoku = Sudoku.fromFile(filepath("/sudoku.txt"))
            val expectedSudoku = Sudoku.fromFile(filepath("/singletons.txt"))

            Assertions.assertThat(subject.process(sudoku))
                    .isEqualTo(expectedSudoku)
        }
    }
}