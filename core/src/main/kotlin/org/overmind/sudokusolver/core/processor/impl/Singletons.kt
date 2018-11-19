package org.overmind.sudokusolver.core.processor.impl

import org.overmind.sudokusolver.core.CandidatesCell
import org.overmind.sudokusolver.core.Sudoku
import org.overmind.sudokusolver.core.processor.CandidatesLose
import org.overmind.sudokusolver.core.processor.NumberPut
import org.overmind.sudokusolver.core.processor.ProcessResult
import org.overmind.sudokusolver.core.processor.SudokuProcessor

class Singletons : SudokuProcessor {
    override fun process(sudoku: Sudoku) = ProcessResult {
        sudoku.cells
                .asSequence()
                .filterIsInstance<CandidatesCell>()
                .filter { it.candidates.size == 1 }
                .forEach { cell ->
                    val number = cell.candidates.first()
                    NumberPut(number) at cell.position

                    cell.neighbors()
                            .filterIsInstance<CandidatesCell>()
                            .filter { neighborCell ->
                                number in neighborCell.candidates
                            }
                            .forEach { neighborCell ->
                                CandidatesLose(number) at neighborCell.position
                            }
                }
    }
}