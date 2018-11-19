package org.overmind.sudokusolver.processor

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.overmind.sudokusolver.CandidatesCellValue
import org.overmind.sudokusolver.NumberCellValue
import org.overmind.sudokusolver.Position
import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.filepath

@DisplayName("SetupCandidates")
class SetupCandidatesTest {

    private val sudoku = Sudoku.rawFromFile(filepath("raw-sudoku.txt"))

    @Test
    fun `update should put CandidatesCell at specified position`() {
        val processResult = ProcessResult {
            SetupCandidates(2, 5, 6, 9) at Position(0, 2)
        }

        val updatedSudoku = processResult.update(sudoku)

        assertThat(updatedSudoku[0, 2])
                .isEqualTo(
                        CandidatesCellValue(2, 5, 6, 9)
                )
    }

    @Test
    fun `update should throw exception if multiple updates occur in any position`() {
        val processResult = ProcessResult {
            SetupCandidates(2, 5) at Position(0, 2)
            SetupCandidates(6, 9) at Position(0, 2)
        }

        assertThatThrownBy { processResult.update(sudoku) }
                .isInstanceOf(IllegalArgumentException::class.java)
                .hasMessage("SetupCandidates can't be merged with ${SetupCandidates::class}")
    }
}

@DisplayName("NumberPut")
class NumberPutTest {

    private val sudoku = Sudoku.fromFile(filepath("sudoku.txt"))

    @Test
    fun `update should put NumberCell at specified position`() {
        val processResult = ProcessResult {
            NumberPut(5) at Position(0, 2)
        }

        val updatedSudoku = processResult.update(sudoku)

        assertThat(updatedSudoku[0, 2])
                .isEqualTo(
                        NumberCellValue(5)
                )
    }
}

@DisplayName("CandidatesLose")
class CandidatesLoseTest {

    private val sudoku = Sudoku.fromFile(filepath("sudoku.txt"))

    @Test
    fun `update should remove candidates from CandidatesCell at specified position`() {
        val processResult = ProcessResult {
            CandidatesLose(5, 6) at Position(0, 2)
        }

        val updatedSudoku = processResult.update(sudoku)

        assertThat(updatedSudoku[0, 2])
                .isEqualTo(
                        CandidatesCellValue(2, 9)
                )
    }
}