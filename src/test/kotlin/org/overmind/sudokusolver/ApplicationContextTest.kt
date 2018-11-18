package org.overmind.sudokusolver

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ApplicationContext")
class ApplicationContextTest {
    @Test
    fun `sudokuSolver should be not null`() {
        assertThat(ApplicationContext.sudokuSolver)
                .isNotNull
    }
}