package org.overmind.sudokusolver

import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Sudoku")
class SudokuTest {
    @Nested
    inner class Companion {

        private val companion = Sudoku

        @Nested
        @DisplayName("fromFile")
        inner class FromFile {

            @Test
            fun `should return RawSudoku`() {
                val sudoku = companion.fromFile(filepath("/sudoku.txt"))

                SoftAssertions.assertSoftly {
                    it.assertThat(sudoku[0, 2])
                            .isEqualTo(CandidatesCellValue(2, 5, 6, 9))

                    it.assertThat(sudoku[5, 4])
                            .isEqualTo(CandidatesCellValue(1, 5, 9))

                    it.assertThat(sudoku[5, 6])
                            .isEqualTo(
                                    NumberCellValue(6)
                            )

                    it.assertThat(sudoku[8, 7])
                            .isEqualTo(
                                    NumberCellValue(2)
                            )
                }
            }
        }

/*
        @Nested
        @DisplayName("rawFromFile")
        inner class RawFromFile {

            @Test
            fun `should return RawSudoku`() {
                val rawSudoku = companion.rawFromFile(filepath("/raw-sudoku.txt"))

                SoftAssertions.assertSoftly {
                    it.assertThat(rawSudoku[0, 2])
                            .isEqualTo(EmptyCellValue)

                    it.assertThat(rawSudoku[5, 4])
                            .isEqualTo(EmptyCellValue)

                    it.assertThat(rawSudoku[5, 6])
                            .isEqualTo(
                                    NumberCellValue(6)
                            )

                    it.assertThat(rawSudoku[8, 7])
                            .isEqualTo(
                                    NumberCellValue(2)
                            )
                }
            }
        }
*/
    }
}