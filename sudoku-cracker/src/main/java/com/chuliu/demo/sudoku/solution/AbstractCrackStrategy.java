package com.chuliu.demo.sudoku.solution;

import com.chuliu.demo.sudoku.exception.NoSolutionException;

/**
 * 解法基类
 *
 * @author liuchu
 * Date 2019/12/3
 * Time 16:19
 */
public abstract class AbstractCrackStrategy {

    /**
     * crack input SodukuMatrix, and return a SodukuMatrix after cracked.
     *
     * @param originMatrix origin SodukuMatrix
     */
    public abstract void crack(SodukuMatrix originMatrix) throws NoSolutionException;

    /**
     * Whether input SodukuMatrix is solvable.
     * TODO
     *
     * @param originMatrix input SodukuMatrix
     * @return whether
     */
    boolean solvable(SodukuMatrix originMatrix) {

        // 1. should have 17 clue numbers at least

        // 2. current clue numbers should satisfy Soduku basic rules

        // 3. validate whether it is resolvable(actually, this item can't be known before we tried to crack, lol)
        return true;
    }
}
