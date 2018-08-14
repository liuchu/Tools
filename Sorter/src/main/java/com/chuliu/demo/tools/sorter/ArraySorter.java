package com.chuliu.demo.tools.sorter;

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

    public static void main(String[] args) {
        int[] testArray = new int[]{2,1,9,5,3,7,6};
        int[] result = sortByInsertion(testArray);

        for (int num:result)
        System.out.print(num+":");
    }
}
