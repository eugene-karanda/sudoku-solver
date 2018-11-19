package org.overmind.sudokusolver.core

import javafx.scene.layout.GridPane
import tornadofx.*

class SudokuView : View() {
    override val root = GridPane()

    init {
        title = "Hello World"

        with(root) {
            (1..9).forEach { rowIndex ->
                row {
                    (1..9).forEach { columnIndex ->
                        button("$rowIndex$columnIndex")
                    }
                }
            }
        }
    }

    override fun onDock() {
        currentStage?.isResizable = false
    }
}

class SudokuDesktopApp : App(SudokuView::class, Styles::class)

class Styles : Stylesheet() {
    init {
        button {
            minWidth = 100.px
            minHeight = 100.px
        }
    }
}

fun main(args: Array<String>) {
    launch<SudokuDesktopApp>(args)
}