package org.overmind.sudokusolver.desktop

import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.control.Button
import tornadofx.*

class SelectionFragment : View() {
    private val buttons = mutableListOf<Button>()

    override val root: Parent = gridpane {
        addClass(Styles.selectionPane)

        (0 until 3).forEach { rowIndex ->
            row {
                (0 until 3).forEach { columnIndex ->
                    val number = rowIndex * 3 + columnIndex + 1

                    buttons += button("$number") {}
                }
            }
        }
    }

    fun onSelect(block: (Int) -> Unit) {
        buttons.forEach {button ->
            button.onAction = EventHandler {
                block(button.text.toInt())
            }
        }
    }
}
