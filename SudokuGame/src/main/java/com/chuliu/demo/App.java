package com.chuliu.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Integer[] fullNumbers = {1,2,3,4,5,6,7,8,9};
        List<Integer> fullNumberList = new ArrayList<>(Arrays.asList(fullNumbers));

        fullNumberList.remove((Integer) 1);

        for (Integer integer : fullNumberList) {
            System.out.print(integer+" ");
        }

        for (Integer integer : fullNumbers) {
            System.out.print(integer+" ");
        }
    }
}
