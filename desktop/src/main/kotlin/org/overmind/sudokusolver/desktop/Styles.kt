package org.overmind.sudokusolver.desktop

import tornadofx.Stylesheet
import tornadofx.c
import tornadofx.cssclass
import tornadofx.px

class Styles : Stylesheet() {
    companion object {
        val numberButton by cssclass()
        val selectionPane by cssclass()
        val sudoku by cssclass()
    }

    init {
        sudoku {
            backgroundColor += c("black")
            //vgap = 15.px
            //hgap = 15.px
        }

        numberButton {
            minWidth = 100.px
            minHeight = 100.px
        }

        selectionPane {
            minWidth = 100.px
            minHeight = 100.px

            button {
                prefWidth = 33.3.px
                prefHeight = 33.3.px
            }
        }
    }
}