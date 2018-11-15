package org.overmind.sudokusolver.processor.impl

import org.overmind.sudokusolver.CandidatesCell
import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.except
import org.overmind.sudokusolver.processor.CandidatesLose
import org.overmind.sudokusolver.processor.ProcessResult
import org.overmind.sudokusolver.processor.SudokuProcessor

class LockedCandidate : SudokuProcessor {
    override fun process(sudoku: Sudoku) = ProcessResult.builder {
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