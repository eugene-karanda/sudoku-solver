package org.overmind.sudokusolver.desktop

import tornadofx.*

class SudokuView : View() {
    override val root = gridpane {
        addClass(Styles.sudoku)

        (0 until 9).forEach { rowIndex ->
            (0 until 9).forEach { columnIndex ->
                this += CellView().run {
                    root.gridpaneConstraints {
                        columnRowIndex(columnIndex, rowIndex)
                    }
                }
            }
        }
    }

    init {
        title = "Hello World"
    }

    override fun onDock() {
        //currentStage?.isResizable = false
    }
}

class SudokuDesktopApp : App(SudokuView::class, Styles::class)

fun main(args: Array<String>) {
    launch<SudokuDesktopApp>(args)
}