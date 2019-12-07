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

            BlankCell current = iter.next();

            boolean chosen = chooseOne(current);

            if (!chosen) {

                // if first cell can't choose a value, the puzzle has so solution
                if (iter.hasPrevious()) {
                    throw new NoSolutionException("This sudoku can't be solved");
                }

                BlankCell prev = iter.previous();

                // last cell have to give up its current val
                giveUp(blankCells, blankCells.indexOf(prev));

            }
        }

        for (int i = 0; i < blankCells.size(); i++) {
            sum ++;

            BlankCell blankCell = blankCells.get(i);

            boolean chosen = chooseOne(blankCell);

            if (!chosen) {

                // if first cell can't choose a value, the puzzle has so solution
                if (i == 0) {
                    throw new NoSolutionException("This sudoku can't be solved");
                }

                i = i - 1;

                // last cell have to give up its current val
                giveUp(blankCells, i);

                i = i - 1;
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

        Map<Integer, Boolean> candidates = blankCell.getCandidates();

        for (Map.Entry<Integer, Boolean> firstPick : candidates.entrySet()) {

            int val = firstPick.getKey();
            boolean valid = firstPick.getValue();

            // only valid number can be chosen
            if (valid) {

                // choose
                blankCell.setValue(val);

                // once chosen, make it invalid(a number could be attempted 1 times, unless previous Cell giveUp a number)
                candidates.put(val, false);

                // update relative cells' candidates, and record to chosenAffectCells
                final Set<BlankCell> relativeBlankCells = blankCell.getRelativeBlankCells();
                Set<BlankCell> changedCells = new HashSet<>();
                for (BlankCell item : relativeBlankCells) {

                    Map<Integer, Boolean> itemCandidates = item.getCandidates();

                    if (itemCandidates.get(val) != null && itemCandidates.get(val)) {

                        // make val invalid in affect cells
                        itemCandidates.put(val, false);

                        changedCells.add(item);
                    }

                }

                blankCell.setChosenAffectCells(changedCells);

/*                System.out.println(blankCell);
                System.out.println("---");
                affectBlanksCells.forEach(System.out::println);
                System.out.println("---");
                changedCells.forEach(System.out::println);*/

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
        //Map<Integer, Boolean> candidates = blankCell.getCandidates();

        // give up this number from candidates
        //candidates.put(giveUpVal, false);

        // reset to 0
        blankCell.setValue(ZERO);

        // restore the giveUpVal to chosenAffectCells, per blankCell gave up it
        Set<BlankCell> chosenAffectCells = blankCell.getChosenAffectCells();
        for (BlankCell item : chosenAffectCells) {
            item.getCandidates().put(giveUpVal, true);
        }

        // make all candidates valid(blank cells after current cell)
        for (int i = index + 1; i < blankCells.size(); i++) {
            blankCells.get(i).makeAllCandidatesValid();
        }
    }
}
