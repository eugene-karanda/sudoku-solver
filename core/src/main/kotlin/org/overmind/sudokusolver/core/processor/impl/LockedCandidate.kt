package org.overmind.sudokusolver.core.processor.impl

import org.overmind.sudokusolver.core.CandidatesCell
import org.overmind.sudokusolver.core.Sudoku
import org.overmind.sudokusolver.core.except
import org.overmind.sudokusolver.core.processor.CandidatesLose
import org.overmind.sudokusolver.core.processor.ProcessResult
import org.overmind.sudokusolver.core.processor.SudokuProcessor

class LockedCandidate : SudokuProcessor {
    override fun process(sudoku: Sudoku) = ProcessResult {
        sudoku.squares.values
                .asSequence()
                .flatMap { square ->
                    sequenceOf(square.rows, square.columns)
                }
                .forEach { group ->
                    val squareGroupCandidatesMap = group
                            .associateBy(
                                    { it },
                                    { squareGroup ->
                                        squareGroup.cells()
                                                .filterIsInstance<CandidatesCell>()
                                                .map(CandidatesCell::candidates)
                                                .fold(emptySet<Int>()) { left, right ->
                                                    left + right
                                                }
                                    }
                            )

                    val squareGroupUniqueCandidatesMap = squareGroupCandidatesMap
                            .mapValues { (_, candidates) ->
                                candidates.asSequence()
                                        .filter { candidate ->
                                            squareGroupCandidatesMap.values.count { set ->
                                                candidate in set
                                            } == 1
                                        }.toSet()
                            }.filterValues(Set<Int>::isNotEmpty)

                    squareGroupUniqueCandidatesMap.forEach { squareGroup, uniqueCandidates ->
                        squareGroup.fullGroup.cells()
                                .except(squareGroup.cells())
                                .filterIsInstance<CandidatesCell>()
                                .forEach { cell ->
                                    val intersectCandidates = cell.candidates
                                            .intersect(uniqueCandidates)
                                    if (intersectCandidates.isNotEmpty()) {
                                        CandidatesLose(intersectCandidates) at cell.position
                                    }
                                }
                    }
                }
    }
}