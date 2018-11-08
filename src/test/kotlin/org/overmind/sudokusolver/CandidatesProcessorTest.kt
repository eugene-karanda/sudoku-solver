package org.overmind.sudokusolver

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CandidatesProcessorTest {
    private lateinit var subject: CandidatesProcessor

    @BeforeEach
    fun setUp() {
        this.subject = CandidatesProcessor()
    }

    @Test
    fun name() {
        val rawSudoku = RawSudoku.fromFile(filepath("abc.txt"))

        assertThat(subject.process(rawSudoku))
                .isEqualTo(
                        Sudoku()
                )
    }
}

/*
fun ProxyableObjectAssert<Cell>.hasCandidates(vararg candidates: Int): ProxyableObjectAssert<Cell> {
    return this.isInstanceOfSatisfying(CandidatesCell::class.java) { cell ->
        assertThat(cell.candidates)
                .containsExactly(*candidates.toTypedArray())
    }
}*/
