package com.chuliu.demo.main;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by eiuhucl on 7/2/2018.
 * Tool can be used to calculate the
 */
public class SudoCalculator {

    private static final Integer[] fullNumbers = {1,2,3,4,5,6,7,8,9};

    //sudoku takes
    private int[][] sudokuTable;
    private Map<Integer,List<Integer>> sudokuAvailableNumberMap;
    private List<Grid> emptyGridList;

    //The numbers already attempted before
    private Map<Integer,List<Integer>> sudokuAttemptedNumberMap;

    private long attemptedTimes = 0;

    private void setSudokuTable(int[][] input){
        this.sudokuTable = input;
    }

    private void init(int[][] input){

        setSudokuTable(input);
        initEmptyGridList();
        initAvailableNumbersMap();
        initAttemptedNumbersMap();

        int[][] array = getSudokuTable();

        System.out.println("The input table: ");
        for (int i=0; i<9; i++) {

            for (int j=0; j<9; j++) {
                System.out.print(array[i][j]+" ");
            }
            System.out.println();
        }
    }

    //generate the result
    private void calculate(){

        Map<Integer,List<Integer>> map = this.getAvailableNumbersMap();

        List<Grid> list = this.getEmptyGridList();
        Map<Integer,List<Integer>> attemptedMap = this.getAttemptedNumberMap();

        for (int i=0; i<9; i++) {

            jfor: for (int j=0; j<9; j++) {

                attemptedTimes++;
                //int number = sudokuTable[i][j];

                //When the number is 0(empty grid)
                //after assigned a number to a grid, the available number is changed by the time
                assert map != null;
                Grid pair = GridInstanceFactory.getSingletonGrid(i,j);

                if (!list.contains(pair)) {
                    //System.out.println("skipped because of fixed");
                    continue;
                }

                for (Integer pairId : map.keySet()) {
                    List<Integer> listTemp = map.get(pairId);

                    if (listTemp.size() == 0) {

                        if (pair.getId() == 0){
                            System.out.println("No solution for input, exit");
                            System.exit(1);
                        }

                        //Available list is empty, previous Pair should change a number
                        Grid previousPair = previousPair(pair);

                        //System.out.println("previousPair: "+previousPair.getId());

                        int previousPairValue = sudokuTable[previousPair.getX()][previousPair.getY()];
                        this.removeNumberFromAnIndexPair(previousPair, previousPairValue);

                        i = previousPair.getX();
                        j = previousPair.getY() - 1;

                        continue jfor;
                    }

                }

                //chosen number
                List<Integer> availableNumbers = map.get(pair.getId());
                int chosenNumber = availableNumbers.get(0);

                //If attempted number is chosen number, previous Pair should change a number
                if (attemptedMap.get(pair.getId()).contains(chosenNumber)) {

                    if (pair.getId() == 0){
                        System.out.println("No solution for input, exit");
                        System.exit(1);
                    }

                    Grid previousPair = previousPair(pair);

                    //System.out.println("previousPair: "+previousPair.getId());

                    int previousPairValue = sudokuTable[previousPair.getX()][previousPair.getY()];
                    this.removeNumberFromAnIndexPair(previousPair, previousPairValue);
                    this.updateAttemptedMap(pair);

                    i = previousPair.getX();
                    j = previousPair.getY() - 1;

                    continue;
                }

                sudokuTable[i][j] = chosenNumber;

                //System.out.println("Choose "+chosenNumber);

                //Update AvailableNumbersMap
                updateAvailableNumbersMap(pair, chosenNumber);

                //Add into attempted Map
                addIntoAttemptedMap(pair, chosenNumber);

            }

            /*if (attemptedTimes == 500)
                System.exit(0);*/
        }

        //Logger.getLogger("Sudoku").info(String.format("After %s times attempts, we finally got the answer: ", attemptedTimes));

    }

    private void printResult(){
        System.out.println("The result after calculate:");
        for (int i=0; i<9; i++) {

            for (int j=0; j<9; j++) {
                System.out.print(sudokuTable[i][j]+" ");
            }
            System.out.println();
        }

    }

    //For a empty Grid, when an available number is removed from it, other affected Grid will be affected.
    private void removeNumberFromAnIndexPair(Grid grid, int removedValue){
        Map<Integer,List<Integer>> map = this.getAvailableNumbersMap();
        map.get(grid.getId()).remove((Integer) removedValue);

        List<Grid> affectedPairs = getAffectedGrids(grid);

        //remove the chosen number from affected Grid
        for (Grid grid1 : affectedPairs) {
            List<Integer> availableNumbers = this.getAvailableNumbersMap().get(grid1.getId());
            availableNumbers.add(removedValue);
        }
    }

    //
    private void updateAttemptedMap(Grid grid){

        List<Grid> emptyGridList = this.getEmptyGridList();
        Map<Integer,List<Integer>> attemptedMap = this.getAttemptedNumberMap();

        //Clear the attempted lists of grids which the previous grid was assigned with a new number
        for (Grid tempPair : emptyGridList){

            if (tempPair.getId() >= grid.getId()) {
                attemptedMap.get(tempPair.getId()).clear();
            }

        }

    }

    //Affected grids after a grid changed
    private List<Grid> getAffectedGrids(Grid grid){

        List<Grid> affectedPairs = new ArrayList<Grid>();

        List<Grid> allPairs = getEmptyGridList();

        for (Grid tempPair : allPairs) {

            //Pairs in same line, same column, same 3*3 block
            if (tempPair.getX() == grid .getX() || tempPair.getY() == grid .getY() || tempPair.getBlockId() == grid .getBlockId()) {
                if (tempPair == grid) {
                    continue;
                }
                affectedPairs.add(tempPair);

            }
        }

        return affectedPairs;
    }

    //
    private Grid previousPair(Grid grid){
        List<Grid> gridList = this.getEmptyGridList();

        for (int i = 0; i < gridList.size(); i++) {

            if (gridList.get(i) == grid) {
                if (i==0) {
                    Logger.getLogger("Sudoku").warning("Tried to find previous grid of array[0], it's wrong!");
                    System.exit(1);
                }
                return gridList.get(i-1);
            }
        }

        Logger.getLogger("Sudoku").warning("Can't previous grid, something wrong! the grid is: "+ grid);
        System.exit(1);

        return null;
    }

    private List<Integer> getAvailableNumbers(Grid grid){
        int gridLine = grid.getX();
        int gridColumn = grid.getY();
        int gridBlockId = grid.getBlockId();

        List<Integer> availableList = new ArrayList<>(Arrays.asList(fullNumbers));

        for (int k=0; k<9; k++) {
            int number1 = sudokuTable[gridLine][k];
            if (number1 != 0) {
                availableList.remove((Integer) number1);
            }

            int number2 = sudokuTable[k][gridColumn];
            if (number2 != 0) {
                availableList.remove((Integer) number2);
            }
        }

        int x = gridBlockId/10;
        int y = gridBlockId - x*10;

        for (int m=0; m<3; m++) {

            for (int n=0; n<3; n++) {
                //(3x,3y) is the beginning Grid, loop the 9 grids in this 3*3 block
                int number = sudokuTable[3*x+m][3*y+n];
                if (number != 0) {
                    availableList.remove((Integer) number);
                }
            }
        }

        return availableList;
    }

    //
    private Map<Integer,List<Integer>> getAvailableNumbersMap(){
        return this.sudokuAvailableNumberMap;
    }

    //
    private Map<Integer,List<Integer>> getAttemptedNumberMap(){
        return this.sudokuAttemptedNumberMap;
    }


    private List<Grid> getEmptyGridList(){
        return this.emptyGridList;

    }

    //Load the Grids which are empty originally
    private void initEmptyGridList(){

        if (emptyGridList == null) {
            emptyGridList = new ArrayList<Grid>();
        }

        for (int i=0; i<9; i++) {

            for (int j=0; j<9; j++) {

                int number = sudokuTable[i][j];

                if (number != 0) {
                    continue;
                }

                Grid pair = GridInstanceFactory.getSingletonGrid(i,j);

                emptyGridList.add(pair);

            }
        }
    }

    //According to origin data, generate origin sudokuAvailableNumberMap
    private void initAvailableNumbersMap(){

        if (sudokuAvailableNumberMap == null) {
            sudokuAvailableNumberMap = new HashMap<Integer,List<Integer>>();
        }

        List<Grid> emptyGridList = this.getEmptyGridList();

        for (Grid grid : emptyGridList){
            List<Integer> availableNumbers = getAvailableNumbers(grid);
            sudokuAvailableNumberMap.put(grid.getId(),availableNumbers);
        }


    }

    //
    private void initAttemptedNumbersMap(){

        if (sudokuAttemptedNumberMap == null) {
            sudokuAttemptedNumberMap = new HashMap<Integer,List<Integer>>();
        }

        List<Grid> emptyGridList = this.getEmptyGridList();

        for (Grid pair : emptyGridList){
            List<Integer> attemptedNumbers = new ArrayList<>();
            sudokuAttemptedNumberMap.put(pair.getId(),attemptedNumbers);
        }

    }

    //After a number is assigned to a Grid, update the available numbers map
    private void updateAvailableNumbersMap(Grid grid, int chosenNumber){

        List<Grid> affectedPairs = getAffectedGrids(grid);

        //remove the chosen number from affected Grid
        for (Grid grid1 : affectedPairs) {
            List availableNumbers = this.getAvailableNumbersMap().get(grid1.getId());
            availableNumbers.remove((Integer)chosenNumber);
        }

    }

    private void addIntoAttemptedMap(Grid grid, int chosenNumber){
        int gridId = grid.getId();

        getAttemptedNumberMap().get(gridId).add(chosenNumber);
    }

    private int[][] getSudokuTable(){
        return this.sudokuTable;
    }

    //A Sudoku table should be a 9*9 array
    private void validateInput(int[][] input){



        if (input.length != 9) {
            System.out.println("ERROR! Input array lines amount must be 9");
            System.exit(1);
        }

        /*for (int i=0; i<9; i++) {

            int[] tempArray = sudokuTable[i];
            if (tempArray.length != 9) {
                System.out.println("ERROR! Input array columns amount must be 9");
                System.exit(1);
            }

        }*/
    }

    public static void figure(int[][] input){
        SudoCalculator calculator = new SudoCalculator();
        calculator.validateInput(input);
        calculator.init(input);
        calculator.calculate();
        calculator.printResult();
    }

}


class GridInstanceFactory{

    private static Map<Integer,Grid> gridJar;

    static {
        GridInstanceFactory.fillGridJar();
    }

    //Contain all singleton Grid


    private static void fillGridJar(){

        if (gridJar==null) {
            gridJar = new HashMap<Integer,Grid>();

            for (int i=0; i<9; i++) {

                for (int j=0; j<9; j++) {

                    Grid pair = new Grid(i,j);
                    int pairId = pair.getId();

                    gridJar.put(pairId,pair);

                }
            }
        }

    }

    public static Grid getSingletonGrid(int x, int y){
        return (Grid)gridJar.get(x*10+y);
    }
}

class Grid implements Comparable{

    private int x;
    private int y;

    /*
    *   0,5,0,0,0,0,0,2,0 ==> blockId: 00 01 02 (every blockId has a 3*3 block, 9 numbers)
        0,3,4,0,7,0,1,8,0              10 11 12
        7,0,0,1,0,3,0,0,4              20 21 22
        0,0,8,7,0,6,3,0,0
        0,0,0,0,0,0,0,0,0
        0,0,7,4,0,8,9,0,0
        5,0,0,9,0,7,0,0,8
        0,6,9,0,2,0,4,7,0
        0,7,0,0,0,0,0,1,0
    * */

    int getId(){
        return this.getX()*10 + this.getY();
    }

    int getBlockId(){
        int blockId = this.getX() / 3 * 10 + this.getY() / 3;
        return blockId;
    }

    Grid(int x, int y){
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        return this.getX()*10 + this.getY();
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int compareTo(Object o) {

        Grid pair;
        pair = (Grid)o;

        if (this.getX() == pair.getX()) {
            return this.getY() - pair.getY();
        }

        return this.getX() - pair.getX();
    }

    @Override
    public String toString() {
        return "Grid{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}