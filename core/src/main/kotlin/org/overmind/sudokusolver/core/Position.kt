package org.overmind.sudokusolver.core

data class Position(val rowIndex: Int, val columnIndex: Int) : Comparable<Position> {
    val positionInSquare: PositionInSquare = PositionInSquare(rowIndex % 3, columnIndex % 3)

    val squarePosition: SquarePosition = SquarePosition(rowIndex / 3, columnIndex / 3)

    override fun compareTo(other: Position): Int = compareValuesBy(this, other,
            { it.rowIndex },
            { it.columnIndex }
    )
    companion object {
        val all: Sequence<Position> = (0 until 9)
                .asSequence()
                .flatMap { rowIndex ->
                    (0 until 9)
                            .asSequence()
                            .map { columnIndex ->
                                Position(rowIndex, columnIndex)
                            }
                }
    }
}

data class SquarePosition(val rowIndex: Int, val columnIndex: Int) : Comparable<SquarePosition> {

    override fun compareTo(other: SquarePosition): Int = compareValuesBy(this, other,
            { it.rowIndex },
            { it.columnIndex }
    )

    companion object {
        val all: Sequence<SquarePosition> = (0 until 3)
                .asSequence()
                .flatMap { rowIndex ->
                    (0 until 3)
                            .asSequence()
                            .map { columnIndex ->
                                SquarePosition(rowIndex, columnIndex)
                            }
                }
    }
}

data class PositionInSquare(val rowIndex: Int, val columnIndex: Int) : Comparable<PositionInSquare> {

    override fun compareTo(other: PositionInSquare): Int = compareValuesBy(this, other,
            { it.rowIndex },
            { it.columnIndex }
    )

    companion object {
        val all: Sequence<PositionInSquare> = (0 until 3)
                .asSequence()
                .flatMap { rowIndex ->
                    (0 until 3)
                            .asSequence()
                            .map { columnIndex ->
                                PositionInSquare(rowIndex, columnIndex)
                            }
                }
    }
}