package com.chuliu.demo.sudoku.solution;

/**
 * @author chuliu
 * Date 2019/12/8
 * Time 14:41
 */
public class Candidate {

    public Candidate(Integer value, Boolean state, Integer readyDepth) {
        this.value = value;
        this.state = state;
        this.readyDepth = readyDepth;
    }

    private Integer value;

    /**
     * state of
     */
    private Boolean state;

    /**
     * candidates ready depth of a blank cell.
     * <p>
     * 0 mean can choose, could be -1, -2, -3...(a part of mechanism how {@link LookBackUponStrategy} work)
     */
    private Integer readyDepth;

    Boolean getState() {
        return state;
    }

    void setState(Boolean state) {
        this.state = state;
    }

    Integer getReadyDepth() {
        return readyDepth;
    }

    void setReadyDepth(Integer readyDepth) {
        this.readyDepth = readyDepth;
    }

    public Integer getValue() {
        return value;
    }
}
