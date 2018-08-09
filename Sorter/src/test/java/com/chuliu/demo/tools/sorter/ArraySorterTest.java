package com.chuliu.demo.tools.sorter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eiuhucl on 8/9/2018.
 */
public class ArraySorterTest {

    private int[] testArray;

    @Before
    public void setUp() throws Exception {
        System.out.println("set up");
        testArray = new int[]{2,1,9,5,3,7,6};
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("tear down");
        testArray = null;
    }

    @Test
    public void sortByBubble() throws Exception {
        int[] result = ArraySorter.sortByBubble(testArray);;

        if (testArray.length != result.length) {
            assertTrue("The length of origin array and target array is different!"+testArray.length+":"+result.length,false);
        }

        for (int i=0;i<result.length-1;i++){
            if (result[i] > result[i+1]) {
                assertTrue("The order is wrong, the "+i+" element is "+result[i]+", while "+(i+1)+" element is "+result[i+1],false);
            }
        }
    }

    @Test
    public void sortTest() throws Exception{
        int[] result = ArraySorter.sortTest(testArray);

        if (testArray.length != result.length) {
            assertTrue("The length of origin array and target array is different!",false);
        }

        for (int i=0;i<result.length-1;i++){
            if (result[i] > result[i+1]) {
                assertTrue("The order is wrong, the "+i+" element is "+result[i]+", while "+(i+1)+" element is "+result[i+1],false);
            }
        }
    }

}