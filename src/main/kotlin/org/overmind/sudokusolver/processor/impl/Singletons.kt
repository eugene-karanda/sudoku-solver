package org.overmind.sudokusolver.processor.impl

import org.overmind.sudokusolver.CandidatesCell
import org.overmind.sudokusolver.Sudoku
import org.overmind.sudokusolver.processor.CandidatesLose
import org.overmind.sudokusolver.processor.NumberPut
import org.overmind.sudokusolver.processor.ProcessResult
import org.overmind.sudokusolver.processor.SudokuProcessor

class Singletons : SudokuProcessor {
    override fun process(sudoku: Sudoku) = ProcessResult.builder {
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