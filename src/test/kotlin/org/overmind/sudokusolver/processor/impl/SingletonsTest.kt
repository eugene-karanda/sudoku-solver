package org.overmind.sudokusolver.processor.impl

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.overmind.sudokusolver.Position
import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.filepath
import org.overmind.sudokusolver.processor.NumberPut
import org.overmind.sudokusolver.processor.ProcessResult
import kotlin.properties.Delegates.notNull

@DisplayName("Singletons")
class SingletonsTest {
    private var subject: Singletons by notNull()

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

            assertThat(subject.process(sudoku))
                    .isEqualTo(ProcessResult.builder {
                        NumberPut(7) at Position(3, 0)
                        NumberPut(8) at Position(3, 3)
                        NumberPut(5) at Position(5, 5)
                    })
        }
    }
}