package org.overmind.sudokusolver.processor.impl

import org.overmind.sudokusolver.CandidatesCell
import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.powerSet
import org.overmind.sudokusolver.processor.CandidatesLose
import org.overmind.sudokusolver.processor.ProcessResult
import org.overmind.sudokusolver.processor.SudokuProcessor

class OpenPairs : SudokuProcessor {
    override fun process(sudoku: Sudoku) = ProcessResult.builder {
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
                    .map { set ->
                        val positions = set.map(CandidatesCell::position)

                        val candidates = set
                                .asSequence()
                                .map(CandidatesCell::candidates)
                                .reduce { left, right ->
                                    left + right
                                }

                        positions to candidates
                    }
                    .filter { (positions, candidatesOfSet) ->
                        positions.size >= candidatesOfSet.size
                    }
                    .forEach { (positionsOfSet, candidatesOfSet) ->
                        candidatesCells.filter { cell ->
                            cell.position !in positionsOfSet
                        }.forEach { cell ->
                            val lostCandidates = cell.candidates.intersect(candidatesOfSet)
                            CandidatesLose(lostCandidates) at cell.position
                        }
                    }
        }

    }
}