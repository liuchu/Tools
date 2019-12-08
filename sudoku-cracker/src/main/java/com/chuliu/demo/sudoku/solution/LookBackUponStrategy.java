package com.chuliu.demo.sudoku.solution;

import com.chuliu.demo.sudoku.exception.NoSolutionException;

import java.util.*;

/**
 * Crack by look back upon way
 *
 * @author liuchu
 * Date 2019/12/3
 * Time 16:55
 */
public class LookBackUponStrategy extends AbstractCrackStrategy {

    private static final int ZERO = 0;

    @Override
    public void crack(SodukuMatrix originMatrix) throws NoSolutionException {

        int sum = 0;

        if (!solvable(originMatrix)) {
            throw new NoSolutionException("This sudoku can't be solved");
        }

        final List<BlankCell> blankCells = originMatrix.getAllBlankCells();

        ListIterator<BlankCell> iter = blankCells.listIterator();

        while (iter.hasNext()) {

            sum++;

            BlankCell current = iter.next();

            boolean chosen = chooseOne(current);

            if (!chosen) {

                // if first cell can't choose a value, the puzzle has so solution
                if (!iter.hasPrevious()) {
                    throw new NoSolutionException("This sudoku can't be solved");
                }

                //
                iter.previous();

                BlankCell prev;
                try {
                    prev = iter.previous();
                } catch (NoSuchElementException e) {
                    throw new NoSolutionException("This sudoku can't be solved");
                }

                // last cell have to give up its current val
                giveUp(blankCells, blankCells.indexOf(prev));
            }
        }

        System.out.println("times: " + sum);
    }

    /**
     * blankCell choose a number
     *
     * @param blankCell blankCell
     * @return whether success
     */
    private boolean chooseOne(BlankCell blankCell) {

        Map<Integer, Candidate> candidates = blankCell.getCandidates();

        for (Candidate candidate : candidates.values()) {

            int val = candidate.getValue();
            boolean state = candidate.getState();
            int readyDepth = candidate.getReadyDepth();

            // only valid number can be chosen
            if (state && readyDepth == 0) {

                // choose
                blankCell.setValue(val);

                // once chosen, make it invalid(a number could be attempted 1 times, unless previous Cell giveUp a number)
                candidate.setState(false);

                // update relative cells' candidates, and record to chosenAffectCells
                final Set<BlankCell> relativeBlankCells = blankCell.getRelativeBlankCells();
                Set<BlankCell> changedCells = new HashSet<>();
                for (BlankCell item : relativeBlankCells) {

                    Candidate itemCandidate = item.getCandidates().get(val);

                    if (itemCandidate != null) {

                        itemCandidate.setReadyDepth(itemCandidate.getReadyDepth() - 1);

                        changedCells.add(item);
                    }

                }

                blankCell.setChosenAffectCells(changedCells);

                return true;
            }
        }

        return false;
    }

    private void giveUp(List<BlankCell> blankCells, int index) {

        BlankCell blankCell = blankCells.get(index);

        // giveUp number
        Integer giveUpVal = blankCell.getValue();
        // candidates

        // reset to 0
        blankCell.setValue(ZERO);

        // restore the giveUpVal to chosenAffectCells, per blankCell gave up it
        Set<BlankCell> chosenAffectCells = blankCell.getChosenAffectCells();
        for (BlankCell item : chosenAffectCells) {
            Candidate candidate = item.getCandidates().get(giveUpVal);

            candidate.setReadyDepth(candidate.getReadyDepth() + 1);

        }

        // make all candidates valid(blank cells after current cell)
        for (int i = index + 1; i < blankCells.size(); i++) {
            blankCells.get(i).makeAllCandidatesValid();
        }
    }
}
