package org.overmind.sudokusolver.processor.impl

import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.processor.ProcessResult
import org.overmind.sudokusolver.processor.SudokuProcessor

class LockedCandidate : SudokuProcessor {
    override fun process(sudoku: Sudoku) = ProcessResult.builder {
        sudoku.squares.values
                .asSequence()
                .forEach { square ->
                    val map = square.rows.associateBy(
                            { it.rowIndex },
                            { squareRow ->
                                squareRow.values()
                                        .filterIsInstance<CandidatesCellValue>()
                                        .map(CandidatesCellValue::candidates)
                                        .fold(emptySet<Int>()) { left, right ->
                                            left + right
                                        }

                            }
                    )

                    val a = map.mapValues { (_, candidates) ->
                        candidates.asSequence()
                                .filter { candidate ->
                                    map.values.count { set ->
                                        candidate in set
                                    } == 1
                                }.toSet()
                    }.filterValues(Set<Int>::isNotEmpty)
                }
    }
}