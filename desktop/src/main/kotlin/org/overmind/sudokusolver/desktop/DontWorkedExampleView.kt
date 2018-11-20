package org.overmind.sudokusolver.desktop

import tornadofx.*

class MyView: View() {
    override val root = button()
}

class DoNotWorkedExampleView : View() {
    override val root = gridpane {
        (0 until 9).forEach { rowIndex ->
            (0 until 9).forEach { columnIndex ->
                add<MyView> {
                    gridpaneConstraints {
                        columnRowIndex(columnIndex, rowIndex)
                    }
                }
            }
        }
    }
}

class DoNotWorkedExampleApp : App(DoNotWorkedExampleView::class)

fun main(args: Array<String>) {
    launch<DoNotWorkedExampleApp>(args)
}