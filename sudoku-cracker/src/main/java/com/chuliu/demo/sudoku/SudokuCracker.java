package com.chuliu.demo.sudoku;

import com.chuliu.demo.sudoku.exception.NoSolutionException;
import com.chuliu.demo.sudoku.solution.AbstractCrackStrategy;
import com.chuliu.demo.sudoku.solution.LookBackUponStrategy;
import com.chuliu.demo.sudoku.solution.SodukuMatrix;

/**
 * 功能入口
 *
 * @author liuchu
 * Date 2019/12/3
 * Time 16:17
 */
public class SudokuCracker {

    /**
     * entrance
     * compute by default crack solution #{@link LookBackUponStrategy}
     *
     * @param input data
     */
    public static int[][] compute(int[][] input) throws NoSolutionException {
        return compute(input, new LookBackUponStrategy());
    }

    /**
     * compute by specific crack solution
     *
     * @param input    data
     * @param solution a crack solution
     */
    public static int[][] compute(int[][] input, AbstractCrackStrategy solution) throws NoSolutionException {

        SodukuMatrix sodukuMatrix = new SodukuMatrix(input);
        solution.crack(sodukuMatrix);

        // sodukuMatrix to 2d-arr
        int[][] output = new int[9][9];

        for (int i = 0; i < output.length; i++) {

            for (int j = 0; j < output[i].length; j++) {
                output[i][j] = sodukuMatrix.valueOfCell(i + 1, j + 1);
            }
        }

        return output;
    }
}
