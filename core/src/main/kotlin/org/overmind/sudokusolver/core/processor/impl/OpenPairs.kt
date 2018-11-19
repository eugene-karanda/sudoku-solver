package org.overmind.sudokusolver.core.processor.impl

import org.overmind.sudokusolver.core.CandidatesCell
import org.overmind.sudokusolver.core.Sudoku
import org.overmind.sudokusolver.core.except
import org.overmind.sudokusolver.core.powerSet
import org.overmind.sudokusolver.core.processor.CandidatesLose
import org.overmind.sudokusolver.core.processor.ProcessResult
import org.overmind.sudokusolver.core.processor.SudokuProcessor

class OpenPairs : SudokuProcessor {
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
                    .map { cellsSet ->
                        val candidates = cellsSet
                                .asSequence()
                                .map(CandidatesCell::candidates)
                                .reduce { left, right ->
                                    left + right
                                }

                        cellsSet to candidates
                    }
                    .filter { (cellsSet, candidatesOfSet) ->
                        cellsSet.size >= candidatesOfSet.size
                    }
                    .forEach { (cellsSet, candidatesOfSet) ->
                        candidatesCells
                                .asSequence()
                                .except(cellsSet)
                                .forEach { cell ->
                                    val lostCandidates = cell.candidates.intersect(candidatesOfSet)
                                    CandidatesLose(lostCandidates) at cell.position
                                }
                    }
        }
    }
}