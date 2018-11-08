package org.overmind.sudokusolver

import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("RawSudoku")
class RawSudokuTest {

    @Nested
    inner class Companion {

        private val companion = RawSudoku

        @Nested
        @DisplayName("fromFile")
        inner class FromFile {

            @Test
            fun `should return RawSudoku`() {
                val rawSudoku = companion.fromFile(filepath("abc.txt"))

                assertSoftly {
                    it.assertThat(rawSudoku[0, 2])
                            .isEqualTo(EmptyCell)

                    it.assertThat(rawSudoku[5, 4])
                            .isEqualTo(EmptyCell)

                    it.assertThat(rawSudoku[5, 6])
                            .isEqualTo(
                                    NumberCell(6)
                            )

                    it.assertThat(rawSudoku[8, 7])
                            .isEqualTo(
                                    NumberCell(2)
                            )
                }
            }
        }
    }
}