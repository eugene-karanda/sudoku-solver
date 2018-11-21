package org.overmind.sudokusolver.desktop

import tornadofx.*

class SudokuView : View() {
    override val root = gridpane {
        addClass(Styles.sudoku)

        (0 until 3).forEach {
            row {
                (0 until 3).forEach {
                    gridpane {
                        addClass(Styles.square)

                        (0 until 3).forEach {
                            row {
                                (0 until 3).forEach {
                                    add<CellFragment> {
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    init {
        title = "Hello World"
    }

    override fun onDock() {
        currentStage?.isResizable = false
    }
}

class SudokuDesktopApp : App(SudokuView::class, Styles::class)

fun main(args: Array<String>) {
    launch<SudokuDesktopApp>(args)
}