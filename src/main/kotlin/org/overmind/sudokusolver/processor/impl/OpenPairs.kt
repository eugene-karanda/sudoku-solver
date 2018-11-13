package org.overmind.sudokusolver.processor.impl

import org.overmind.sudokusolver.*
import org.overmind.sudokusolver.processor.CandidatesLose
import org.overmind.sudokusolver.processor.ProcessResult
import org.overmind.sudokusolver.processor.SudokuProcessor

class OpenPairs : SudokuProcessor<CellValue, CellValue> {
    override fun process(sudoku: Sudoku<CellValue>) = ProcessResult.builder<CellValue, CellValue> {
        sudoku.groups.forEach { group ->
            val candidatesCells = group.cells()
                    .filter { cell ->
                        cell.value is CandidatesCellValue
                                && cell.value.candidates.size >= 2
                    }
                    .toSet()

            candidatesCells.powerSet()
                    .asSequence()
                    .filter { it.size in (2 until 5) }
                    .map { set ->
                        val positions = set.map {
                            it.position
                        }

                        val candidates = set.asSequence()
                                .map { (it.value as CandidatesCellValue).candidates }
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
                        }.map {
                            it.position to (it.value as CandidatesCellValue).candidates
                        }.forEach { (position, candidates) ->
                            val lostCandidates = candidates.intersect(candidatesOfSet)
                            CandidatesLose(lostCandidates) at position
                        }
                    }
        }

    }
}