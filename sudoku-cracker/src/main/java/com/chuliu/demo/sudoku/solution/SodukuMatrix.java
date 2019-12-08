package com.chuliu.demo.sudoku.solution;

import com.chuliu.demo.sudoku.exception.NoSolutionException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 数独棋盘的抽象
 *
 * @author liuchu
 * Date 2019/12/3
 * Time 16:24
 */
public class SodukuMatrix {

    private static final List<Integer> NUMBERS = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    private Cell[][] data;

    /**
     * when init, load all blank cells
     */
    private List<BlankCell> allBlankCells;

    /**
     * when init, load all cells, group by rowId
     */
    private Map<Integer, Set<Cell>> cellsOfRowMap;

    /**
     * when init, load all cells, group by columnId
     */
    private Map<Integer, Set<Cell>> cellsOfColumnMap;

    /**
     * when init, load all cells, group by blockId
     */
    private Map<Integer, Set<Cell>> cellsOfBlockMap;

    public SodukuMatrix(int[][] inputArr) throws NoSolutionException {
        initData(inputArr);
    }

    /**
     * load #data, #allBlankCells, #allCells, #cellsOfBlockMap
     *
     * @param inputArr input array
     */
    private void initData(int[][] inputArr) throws NoSolutionException {

        data = new Cell[9][9];

        /*
         * when init, load all cells
         */
        Set<Cell> allCells = new HashSet<>();
        allBlankCells = new ArrayList<>();

        // fill all cells
        for (int i = 0; i < inputArr.length; i++) {
            for (int j = 0; j < inputArr[i].length; j++) {
                int val = inputArr[i][j];

                Cell cell;
                if (val == 0) {
                    // blank cell
                    cell = new BlankCell(i + 1, j + 1, val);
                    allBlankCells.add((BlankCell) cell);
                } else {
                    // clue cell
                    cell = new ClueCell(i + 1, j + 1, val);
                }

                data[i][j] = cell;
                allCells.add(cell);
            }
        }

        // init cellsOfRowMap, cellsOfColumnMap, cellsOfBlockMap
        cellsOfRowMap = allCells.stream().collect(Collectors.groupingBy(Cell::getRow, Collectors.toSet()));
        cellsOfColumnMap = allCells.stream().collect(Collectors.groupingBy(Cell::getColumn, Collectors.toSet()));
        cellsOfBlockMap = allCells.stream().collect(Collectors.groupingBy(Cell::getBlock, Collectors.toSet()));

        // init candidates, affectCells of each blank cell
        for (BlankCell item : allBlankCells) {

            Set<Cell> affectCells = new HashSet<>();
            affectCells.addAll(cellsOfRow(item.getRow()));
            affectCells.addAll(cellsOfColumn(item.getColumn()));
            affectCells.addAll(cellsOfBlock(item.getBlock()));

            // affectCells of a cell(exclude itself)
            item.setRelativeBlankCells(affectCells.stream()
                    .filter(c -> c.getValue() == 0)
                    .filter(c -> !(c.getRow() == item.getRow() && c.getColumn() == item.getColumn()))
                    .map(c -> (BlankCell) c)
                    .collect(Collectors.toSet()));

            // in using numbers of affect rows
            Set<Integer> usingNumbers =
                    affectCells.stream().filter(c -> c.getValue() != 0).map(Cell::getValue).collect(Collectors.toSet());

            Map<Integer, Candidate> candidates = new HashMap<>();

            NUMBERS.stream()
                    .filter(n -> !usingNumbers.contains(n))
                    .map(n -> new Candidate(n, true, 0))
                    .forEach(c -> candidates.put(c.getValue(), c));

            if (candidates.size() == 0) {
                throw new NoSolutionException(String.format("This sudoku can't be solved, per cell[%s, %s]", item.getRow(), item.getRow()));
            }

            item.setCandidates(candidates);
        }

    }

    private Cell[][] getRawData() {
        return data;
    }

    List<BlankCell> getAllBlankCells() {
        return allBlankCells;
    }

    private Set<Cell> cellsOfRow(int row) {
        return cellsOfRowMap.get(row);
    }

    private Set<Cell> cellsOfColumn(int column) {
        return cellsOfColumnMap.get(column);
    }

    private Set<Cell> cellsOfBlock(int block) {
        return cellsOfBlockMap.get(block);
    }

    public int valueOfCell(int row, int column) {
        return getRawData()[row - 1][column - 1].getValue();
    }

}
