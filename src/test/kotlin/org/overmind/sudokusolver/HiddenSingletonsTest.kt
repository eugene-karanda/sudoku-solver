package org.overmind.sudokusolver

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("HiddenSingletons")
class HiddenSingletonsTest {
    private lateinit var subject: HiddenSingletons

    @BeforeEach
    fun setUp() {
        this.subject = HiddenSingletons()
    }

    @Nested
    @DisplayName("process")
    inner class Process {
        @Test
        fun `should replace all cells with candidate that occur only in this cell of this either row or column or square`() {
            val sudoku = Sudoku.fromFile(filepath("/sudoku.txt"))
            val expectedSudoku = Sudoku.fromFile(filepath("/hidden-singletons.txt"))

            Assertions.assertThat(subject.process(sudoku))
                    .isEqualTo(expectedSudoku)
        }
    }
}