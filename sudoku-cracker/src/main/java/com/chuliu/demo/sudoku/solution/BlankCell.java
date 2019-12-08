package com.chuliu.demo.sudoku.solution;

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

    private Map<Integer, Candidate> candidates;

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

    public Map<Integer, Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(Map<Integer, Candidate> candidates) {
        this.candidates = candidates;
    }

    void makeAllCandidatesValid() {
        for (Candidate candidate : candidates.values()) {
            candidate.setState(true);
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
