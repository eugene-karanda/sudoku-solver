package org.overmind.sudokusolver.proccessor.impl

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.overmind.sudokusolver.Position
import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.filepath
import org.overmind.sudokusolver.processor.NumberPut
import org.overmind.sudokusolver.processor.ProcessResult
import org.overmind.sudokusolver.processor.impl.HiddenSingletons
import kotlin.properties.Delegates.notNull

@DisplayName("HiddenSingletons")
class HiddenSingletonsTest {
    private var subject: HiddenSingletons by notNull()

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
                    .isEqualTo(ProcessResult.builder {
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