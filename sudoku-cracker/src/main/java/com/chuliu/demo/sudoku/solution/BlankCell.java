package com.chuliu.demo.sudoku.solution;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Blank cell
 *
 * @author liuchu
 * Date 2019/12/3
 * Time 17:34
 */
class BlankCell extends Cell {

    /**
     * candidates of a blank cell.
     * <p>
     * key, the value
     * value, whether valid; invalid numbers can't be choose(a part of mechanism how {@link LookBackUponStrategy} work)
     */
    private Map<Integer, Boolean> candidates;

    /**
     * cells in Soduku matrix whose row, column, or block is same as current cell's
     * (excluding its self)
     */
    private Set<BlankCell> relativeBlankCells;

    /**
     * the blanks cells whose candidates were changed due to 'choose' of current cell
     */
    private Set<BlankCell> chosenAffectCells;

    BlankCell(int row, int column, int value) {
        super(row, column, value);
    }

    Map<Integer, Boolean> getCandidates() {
        return candidates;
    }

    void setCandidates(Map<Integer, Boolean> candidates) {
        this.candidates = candidates;
    }

    void makeAllCandidatesValid() {
        for (Integer key : candidates.keySet()) {
            candidates.put(key, true);
        }
    }

    Set<BlankCell> getRelativeBlankCells() {
        return relativeBlankCells;
    }

    void setRelativeBlankCells(Set<BlankCell> relativeBlankCells) {
        this.relativeBlankCells = relativeBlankCells;
    }

    Set<BlankCell> getChosenAffectCells() {
        return chosenAffectCells;
    }

    void setChosenAffectCells(Set<BlankCell> chosenAffectCells) {
        this.chosenAffectCells = chosenAffectCells;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
