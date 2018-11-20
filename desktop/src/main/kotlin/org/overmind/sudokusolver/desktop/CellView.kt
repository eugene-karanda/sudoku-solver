package org.overmind.sudokusolver.desktop

import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.control.Button
import tornadofx.*

class CellView : View() {
    private var numberButton: Button by singleAssign()
    private var selectionPane = gridpane {
        addClass(Styles.selectionPane)

        (0 until 9).forEach { index -> //TODO to view
            button("${index + 1}") {
                gridpaneConstraints {
                    columnRowIndex(index % 3, index / 3)
                }
            }
        }
    }

    override val root: Parent = vbox {
        numberButton = button {
            addClass(Styles.numberButton)
        }
    }

    init {
        numberButton.onMouseClicked = EventHandler {
            numberButton.replaceWith(selectionPane)
        }
    }
}