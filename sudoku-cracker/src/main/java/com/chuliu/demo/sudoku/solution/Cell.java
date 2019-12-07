package com.chuliu.demo.sudoku.solution;

/**
 * @author liuchu
 * Date 2019/12/3
 * Time 17:30
 */
class Cell {

    /**
     * column, row, block that Cell belongs to
     */
    private int row, column, block;

    /**
     * value
     */
    private int value;

    Cell(int row, int column, int value) {
        this.column = column;
        this.row = row;
        this.value = value;

        this.block = calcBlock(row, column);
    }

    /**
     * calculate block id by column, row
     *
     * @param column column id
     * @param row    row id
     * @return block id
     */
    private int calcBlock(int row, int column) {

        int floor = (row - 1) / 3 + 1;
        int tower = (column - 1) / 3 + 1;

        return 3 * floor + tower - 3;
    }

    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }

    int getBlock() {
        return block;
    }

    int getValue() {
        return value;
    }

    void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Cell)) {
            return false;
        }
        Cell cell = (Cell) o;
        return cell.row == row && cell.column == column;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + row;
        result = 31 * result + column;
        return result;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "row=" + row +
                ", column=" + column +
                ", block=" + block +
                ", value=" + value +
                '}';
    }
}
