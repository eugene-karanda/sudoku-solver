package org.overmind.sudokusolver.core.processor.impl

import org.overmind.sudokusolver.core.CandidatesCell
import org.overmind.sudokusolver.core.Sudoku
import org.overmind.sudokusolver.core.except
import org.overmind.sudokusolver.core.powerSet
import org.overmind.sudokusolver.core.processor.CandidatesLose
import org.overmind.sudokusolver.core.processor.ProcessResult
import org.overmind.sudokusolver.core.processor.SudokuProcessor

class HiddenPairs : SudokuProcessor {
    override fun process(sudoku: Sudoku) = ProcessResult {
        sudoku.groups.forEach { group ->
            val candidatesCells = group.cells()
                    .filterIsInstance<CandidatesCell>()
                    .filter { cell ->
                        cell.candidates.size >= 2
                    }
                    .toSet()

            candidatesCells.powerSet()
                    .asSequence()
                    .filter { it.size in (2 until 5) }
                    .forEach { set ->
                        val intersectCandidates = set
                                .asSequence()
                                .map(CandidatesCell::candidates) // 5, 6, 7, 8
                                .reduce { left, right ->
                                    left.intersect(right)
                                }

                        val otherCandidates = candidatesCells
                                .asSequence()
                                .except(set)
                                .map(CandidatesCell::candidates)
                                .fold(emptySet<Int>()) { left, right ->
                                    left + right
                                }

                        val uniqueCandidates = intersectCandidates - otherCandidates

                        if(uniqueCandidates.size == set.size) {
                            set.forEach { cell ->
                                CandidatesLose(cell.candidates - uniqueCandidates) at cell.position
                            }
                        }
                    }
        }
    }
}