package com.chuliu.demo.tools.sorter;

/**
 * Created by eiuhucl on 8/9/2018.
 */
public class ArraySorter {

    public static int[] sortByBubble(int[] input){

        for (int i=0;i<input.length-1;i++) {

            for (int j=0;j<input.length-1;j++) {
                if (input[j]>input[j+1]) {
                    int temp = input[j];
                    input[j] = input[j+1];
                    input[j+1] = temp;
                }
            }
        }

        return input;
    }

    public static int[] sortTest(int[] input){
        return input;
    }

    public static void main(String[] args) {
        int[] testArray = new int[]{2,1,9,5,3,7,6};
        sortByBubble(testArray);

        System.out.println(testArray);
    }
}
