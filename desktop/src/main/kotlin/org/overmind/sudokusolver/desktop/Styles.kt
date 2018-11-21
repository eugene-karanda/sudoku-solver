package org.overmind.sudokusolver.desktop

import tornadofx.Stylesheet
import tornadofx.c
import tornadofx.cssclass
import tornadofx.px

class Styles : Stylesheet() {
    companion object {
        val sudoku by cssclass()
        val square by cssclass()
        val numberButton by cssclass()
        val selectionPane by cssclass()
    }

    init {
        sudoku {
            backgroundColor += c("black")
            vgap = 15.px
            hgap = 15.px
        }

        numberButton {
            prefWidth = 100.px
            prefHeight = 100.px
            fontSize = 50.px
        }

        selectionPane {
            prefWidth = 100.px
            prefHeight = 100.px

            button {
                prefWidth = 33.3.px
                prefHeight = 33.3.px
            }
        }
    }
}