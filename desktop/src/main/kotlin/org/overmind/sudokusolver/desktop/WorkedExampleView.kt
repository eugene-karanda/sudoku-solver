package org.overmind.sudokusolver.desktop

import tornadofx.*

class WorkedExampleView : View() {
    override val root = gridpane {
        (0 until 9).forEach { rowIndex ->
            (0 until 9).forEach { columnIndex ->
                button {
                    gridpaneConstraints {
                        columnRowIndex(columnIndex, rowIndex)
                    }
                }
            }
        }
    }
}

class WorkedExampleApp : App(WorkedExampleView::class)

fun main(args: Array<String>) {
    launch<WorkedExampleApp>(args)
}