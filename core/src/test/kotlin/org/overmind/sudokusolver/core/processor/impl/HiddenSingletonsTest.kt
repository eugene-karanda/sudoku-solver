package org.overmind.sudokusolver.core.processor.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.overmind.sudokusolver.core.Position
import org.overmind.sudokusolver.core.Sudoku
import org.overmind.sudokusolver.core.filepath
import org.overmind.sudokusolver.core.processor.NumberPut
import org.overmind.sudokusolver.core.processor.ProcessResult
import kotlin.properties.Delegates.notNull

@DisplayName("HiddenSingletons")
class HiddenSingletonsTest {
    private var subject: HiddenSingletons by notNull()

    @BeforeEach
    fun setUp() {
        this.subject = HiddenSingletons()
    }

    @Nested
    @DisplayName("update")
    inner class Process {
        @Test
        fun `should put number in each cell with candidate that occur only in this cell of this cell row(column, square)`() {
            val sudoku = Sudoku.fromFile(filepath("sudoku.txt"))

            assertThat(subject.process(sudoku))
                    .isEqualTo(ProcessResult {
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