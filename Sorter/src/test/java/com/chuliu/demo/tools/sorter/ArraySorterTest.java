package com.chuliu.demo.tools.sorter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by eiuhucl on 8/9/2018.
 * Unit test for com.chuliu.demo.tools.sorter.ArraySorter
 */
public class ArraySorterTest {

    private int[] testArray;
    private long startTime = 0L;
    private long endTime = 0L;

    private String sortMethodName="";

    @Before
    public void setUp() throws Exception {
        testArray = new int[]{10,93,2,1,9,5,3,7,6,45,12,65,32,11,33,44,55,66,124,77,54,98,45,21,30};
        startTime = System.nanoTime();
    }

    @After
    public void tearDown() throws Exception {
        testArray = null;
        Logger.getLogger("ArraySorterTest").info("Sort method "+sortMethodName+" took "+(endTime -startTime)+" nanos");
    }

    @Test
    public void sortByBubble() throws Exception {
        sortMethodName="sortByBubble";
        int[] result = ArraySorter.sortByBubble(testArray);

        commonValidateSort(testArray,result);
    }

    @Test
    public void sortBySelection() throws Exception {
        sortMethodName="sortBySelection";
        int[] result = ArraySorter.sortBySelection(testArray);

        commonValidateSort(testArray,result);
    }

    @Test
    public void sortByInsertion() throws Exception {
        sortMethodName="sortByInsertion";
        int[] result = ArraySorter.sortByInsertion(testArray);

        commonValidateSort(testArray,result);
    }

    @Test
    public void sortByMerge() throws Exception {
        sortMethodName="sortByMerge";
        int[] result = ArraySorter.sortByMerge(testArray);

        commonValidateSort(testArray,result);
    }

    @Test
    public void sortByQuick() throws Exception{
        sortMethodName="sortByQuick";
        int[] result = ArraySorter.sortByQuick(testArray);

        commonValidateSort(testArray,result);
    }

    private void commonValidateSort(int[] input, int[] output){
        endTime = System.nanoTime();

        if (input.length != output.length) {
            assertTrue("The length of origin array and target array is different!"+input.length+":"+output.length,false);
        }

        for (int i=0;i<output.length-1;i++){
            if (output[i] > output[i+1]) {
                assertTrue("The order is wrong, the "+i+" element is "+output[i]+", while "+(i+1)+" element is "+output[i+1],false);
            }
        }
    }

}