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
        fun `should put number in each cell with candidate that occur only in this cell of this cell row(column, square)`() {
            val sudoku = Sudoku.fromFile(filepath("/sudoku.txt"))

            Assertions.assertThat(subject.process(sudoku))
                    .isEqualTo(ProcessResult.builder<CellValue, CellValue> {
                        NumberPut(9) at Position(0, 3)
                        NumberPut(2) at Position(0, 8)
                        NumberPut(4) at Position(2, 0)
                        NumberPut(4) at Position(4, 1)
                        NumberPut(9) at Position(5, 4)
                        NumberPut(2) at Position(6, 5)
                    })
        }
    }
}