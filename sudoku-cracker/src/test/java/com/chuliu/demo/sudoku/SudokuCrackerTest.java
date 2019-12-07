package com.chuliu.demo.sudoku;

import com.chuliu.demo.sudoku.exception.NoSolutionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author liuchu
 * Date 2019/12/6
 * Time 15:53
 */
@RunWith(JUnit4.class)
public class SudokuCrackerTest {

    @Test
    public void compute() throws NoSolutionException {
        int[] line1Array = {2, 0, 7, 0, 0, 5, 0, 1, 0};
        int[] line2Array = {0, 0, 0, 0, 2, 8, 0, 0, 9};
        int[] line3Array = {5, 0, 0, 0, 1, 0, 7, 0, 0};
        int[] line4Array = {0, 0, 8, 0, 0, 0, 0, 5, 0};
        int[] line5Array = {0, 0, 3, 6, 0, 9, 4, 0, 0};
        int[] line6Array = {0, 2, 0, 0, 0, 0, 8, 0, 0};
        int[] line7Array = {0, 0, 4, 0, 9, 0, 0, 0, 5};
        int[] line8Array = {3, 0, 0, 5, 4, 0, 0, 0, 0};
        int[] line9Array = {0, 9, 0, 2, 0, 0, 6, 0, 3};

        int[][] input = new int[][]{
                line1Array, line2Array, line3Array,
                line4Array, line5Array, line6Array,
                line7Array, line8Array, line9Array
        };


        int[][] output = SudokuCracker.compute(input);

        printResult(output);
    }

    private void printResult(int[][] arr) {
        System.out.println("The result after calculate:");
        for (int i = 0; i < 9; i++) {

            for (int j = 0; j < 9; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }

    }
}