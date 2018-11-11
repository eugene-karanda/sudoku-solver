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
        fun `should put number in each cells with single candidate`() {
            val sudoku = Sudoku.fromFile(filepath("/sudoku.txt"))

            Assertions.assertThat(subject.process(sudoku))
                    .isEqualTo(ProcessResult.builder<CellValue, CellValue> {
                        NumberPut(7) at Position(3, 0)
                        NumberPut(8) at Position(3, 3)
                        NumberPut(5) at Position(5, 5)
                    })
        }
    }
}