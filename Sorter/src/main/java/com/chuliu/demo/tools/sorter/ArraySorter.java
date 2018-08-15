package com.chuliu.demo.tools.sorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by eiuhucl on 8/9/2018.
 */
public class ArraySorter {

    //冒泡
    public static int[] sortByBubble(int[] input){

        //创建一个副本，并操作副本，不改变输入数组
        int[] inputCopy = new int[input.length];
        System.arraycopy(input, 0, inputCopy, 0, input.length);

        //每一次遍历，
        for (int i=0;i<inputCopy.length-1;i++) {

            for (int j=0;j<inputCopy.length-1;j++) {
                if (inputCopy[j]>inputCopy[j+1]) {
                    int temp = inputCopy[j];
                    inputCopy[j] = inputCopy[j+1];
                    inputCopy[j+1] = temp;
                }
            }
        }

        return inputCopy;
    }

    //排序
    public static int[] sortBySelection(int[] input){
        //创建一个副本，并操作副本，不改变输入数组
        int[] inputCopy = new int[input.length];
        System.arraycopy(input, 0, inputCopy, 0, input.length);

        //每一次遍历，找到一个最小数，放在未排序列表的最前面
        for (int i=0;i<inputCopy.length-1;i++) {

            int minNumber = inputCopy[i];
            int indexOfMinNumber = i;

            //找到未排序列表里最小的数字的索引
            for (int j=i;j<inputCopy.length;j++){

                if (inputCopy[j] < minNumber) {
                    minNumber = inputCopy[j];
                    indexOfMinNumber = j;
                }

            }

            //将最小的数字和未排序列表第一个元素交换位置
            int temp = inputCopy[i];
            inputCopy[i] = inputCopy[indexOfMinNumber];
            inputCopy[indexOfMinNumber] = temp;

        }

        return inputCopy;
    }

    //插入
    public static int[] sortByInsertion(int[] input){

        //创建一个副本，并操作副本，不改变输入数组
        int[] inputCopy = new int[input.length];
        System.arraycopy(input, 0, inputCopy, 0, input.length);

        //每一次遍历，将未排序列表的第一个元素 插入到 已排序列表的正确位置
        for (int i=1;i<inputCopy.length;i++) {

            int insertNum = inputCopy[i];
            int currentIndex = i - 1;

            //While实现，待插入元素和已排序列表的元素 从后往前比较，待插入元素较小则 被比较的数后移一位，并插入到当前被比较数的位置
            while (currentIndex >= 0 && insertNum < inputCopy[currentIndex]) {

                inputCopy[currentIndex+1] = inputCopy[currentIndex]; // 后移一位
                inputCopy[currentIndex] = insertNum;
                currentIndex --;
            }

            //For实现
            /*for (int j=i-1;j>=0;j--){

                if (insertNum < inputCopy[j]) {
                    inputCopy[j+1] = inputCopy[j];
                    inputCopy[j] = insertNum;
                }
            }*/
        }

        return inputCopy;

    }

    //归并
    public static int[] sortByMerge(int[] input){

        return input;
    }

    //将两个有序数组合并成一个
    private static int[] mergeOrderedArrays(int[] orderedArray1, int[] orderedArray2){

        //int[] result = new int[orderedArray1.length+orderedArray2.length];
        ArrayList<Integer> result = new ArrayList<>();


        int pos1 = 0;
        int pos2 = orderedArray2.length;

        outter_loop:for (int i=0; i<orderedArray2.length; i++) {

            for (int j=pos1;j<orderedArray1.length;j++) {
                //System.out.println("Comparing "+orderedArray2[i]+" ? "+orderedArray1[j]);
                if (orderedArray2[i] > orderedArray1[j]) {
                    result.add(orderedArray1[j]);
                    pos1 = j+1;
                } else {
                    result.add(orderedArray2[i]);
                    break;
                }

                //走到这个逻辑，说明最大的数在orderedArray2中
                if (j == orderedArray1.length-1) {
                    pos2 = i;
                    break outter_loop;
                }
            }

        }

        /*System.out.println("pos1:"+pos1+" pos2:"+pos2);*/

        //for循环结束以后最后的部分元素并未进入List，额外添加
        for (int m=pos1; m<orderedArray1.length; m++){
            result.add(orderedArray1[m]);
        }

        for (int n=pos2; n<orderedArray2.length; n++){
            result.add(orderedArray2[n]);
        }

        //把List转化为Array
        Integer[] resultArray = new Integer[result.size()];
        result.toArray(resultArray);

        int[] resultArray2 = new int[resultArray.length];

        System.arraycopy(resultArray, 0, resultArray2, 0, resultArray.length);

        return resultArray2;
    }

    private static void divideAndMerge(int[] array){

        /*int[] inputCopy = new int[array.length];
        int[] subArray1 = System.arraycopy(input, 0, inputCopy, 0, input.length);*/

    }


    public static void main(String[] args) {
        int[] testArray1 = new int[]{2};
        int[] testArray2 = new int[]{1};

        int[] result = mergeOrderedArrays(testArray1,testArray2);

        for (int num:result)
        System.out.print(num+":");
    }
}
