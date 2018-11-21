package org.overmind.sudokusolver.desktop

import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.control.Button
import tornadofx.*

class CellFragment : Fragment() {
    private var numberButton: Button by singleAssign()
    private var selectionPane = SelectionFragment()

    override val root: Parent = vbox {
        numberButton = button {
            addClass(Styles.numberButton)
        }
    }

    init {
        numberButton.onMouseClicked = EventHandler {
            numberButton.replaceWith(selectionPane.root)
        }

        selectionPane.onSelect {
            numberButton.text = "$it"
            selectionPane.root.replaceWith(numberButton)
        }
    }
}