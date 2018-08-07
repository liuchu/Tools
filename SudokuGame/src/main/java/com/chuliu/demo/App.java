package com.chuliu.demo;

import com.chuliu.demo.main.SudoCalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{

    private static int[][] sudokuTable;

    private static void initData(){

        int[] line1Array = {2,0,7,0,0,5,0,1,0};
        int[] line2Array = {0,0,0,0,2,8,0,0,9};
        int[] line3Array = {5,0,0,0,1,0,7,0,0};
        int[] line4Array = {0,0,8,0,0,0,0,5,0};
        int[] line5Array = {0,0,3,6,0,9,4,0,0};
        int[] line6Array = {0,2,0,0,0,0,8,0,0};
        int[] line7Array = {0,0,4,0,9,0,0,0,5};
        int[] line8Array = {3,0,0,5,4,0,0,0,0};
        int[] line9Array = {0,9,0,2,0,0,6,0,3};

        sudokuTable = new int[][]{
                line1Array, line2Array, line3Array,
                line4Array, line5Array, line6Array,
                line7Array, line8Array, line9Array
        };
    }

    public static void main( String[] args )
    {
        initData();
        //System.out.println(sudokuTable);
        SudoCalculator.figure(sudokuTable);
    }
}
