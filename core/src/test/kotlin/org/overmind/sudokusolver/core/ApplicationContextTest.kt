package org.overmind.sudokusolver.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ApplicationContext")
class ApplicationContextTest {
    @Test
    fun `sudokuSolver should be not null`() {
        assertThat(org.overmind.sudokusolver.core.ApplicationContext.sudokuSolver)
                .isNotNull
    }
}