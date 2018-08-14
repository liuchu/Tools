package com.chuliu.demo.tools.sorter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eiuhucl on 8/9/2018.
 * Unit test for com.chuliu.demo.tools.sorter.ArraySorter
 */
public class ArraySorterTest {

    private int[] testArray;

    @Before
    public void setUp() throws Exception {
        System.out.println("set up test array");
        testArray = new int[]{2,1,9,5,3,7,6};
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("tear down test array");
        testArray = null;
    }

    @Test
    public void sortByBubble() throws Exception {
        int[] result = ArraySorter.sortByBubble(testArray);

        commonValidateSort(testArray,result);
    }

    @Test
    public void sortBySelection() throws Exception {
        int[] result = ArraySorter.sortBySelection(testArray);

        commonValidateSort(testArray,result);
    }

    @Test
    public void sortByInsertion() throws Exception {
        int[] result = ArraySorter.sortByInsertion(testArray);

        commonValidateSort(testArray,result);
    }

    private void commonValidateSort(int[] input, int[] output){
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