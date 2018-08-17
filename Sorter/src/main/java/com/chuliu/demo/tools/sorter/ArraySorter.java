package com.chuliu.demo.tools.sorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

        int[] inputCopy = new int[input.length];
        System.arraycopy(input, 0, inputCopy, 0, input.length);

        sort(inputCopy, 0, inputCopy.length-1);

        return inputCopy;
    }

    private static void sort(int[] array, int start, int end){

        //分割 到 只有一个元素，不用再分
        if (start == end) {
            return;
        }

        int mid = (end + start)/2;

        //将左右 子数组 排序
        sort(array, start, mid); //左 子数组 排序
        sort(array, mid+1, end);//右 子数组 排序

        //合并左右 子数组
        merge(array,start,end,mid);//合并 左右 子数组

    }

    //在一个数组内的两段有序数组， 合并成一段有序数组
    //array[start,mid]为第一段，array[mid+1,end]为第二段
    private static void merge(int[] array, int start, int end, int mid){

        //使用ArrayList完成合并过程中值的存储
        ArrayList<Integer> mergedList = new ArrayList<>();

        int lengthOfSubArray1 =  mid - start + 1;
        int lengthOfSubArray2 =  end - mid;

        int pos1 = 0;
        int pos2 = lengthOfSubArray2;

        outter_loop:for (int i=0; i<lengthOfSubArray2; i++) {

            for (int j=pos1;j<lengthOfSubArray1;j++) {

                if (array[mid+i+1] > array[start+j]) {
                    mergedList.add(array[start+j]);
                    pos1 = j+1;
                } else {
                    mergedList.add(array[mid+i+1]);
                    break;
                }

                //走到这个逻辑，说明最大的数在orderedArray2中
                if (j == lengthOfSubArray1-1) {
                    pos2 = i;
                    break outter_loop;
                }
            }

        }

        //for循环结束以后最后的部分元素并未进入List，额外添加
        for (int m=pos1; m<lengthOfSubArray1; m++){
            mergedList.add(array[start+m]);
        }

        for (int n=pos2; n<lengthOfSubArray2; n++){
            mergedList.add(array[mid+n+1]);
        }

        //将合并后的结果 赋值给 传入的数组, 完成合并
        for (int p=0;p<mergedList.size();p++) {
            array[start+p] = mergedList.get(p);
        }

        //释放List
        mergedList = null;

    }

    //快速排序
    public static int[] sortByQuick(int[] input){
        int[] inputCopy = new int[input.length];
        System.arraycopy(input, 0, inputCopy, 0, input.length);

        quickSort(inputCopy,0,inputCopy.length-1);

        return inputCopy;
    }

    private static void quickSort(int[] array, int start, int end){

        //当 基元素 恰好是 最大或最小 的数时，下一层的递归调用 会 超出数组范围，
        if (start >= end) {//即 只有一个数
            return;
        }

        //将 array[start, end]进行 快速排序
        int indexOfBaseElement = start; //随着交换位置会变化
        final int baseElement = array[start]; //不变

        for (int i = start+1;i<end+1;i++) {

            //将这个元素放到 基元素 的左边
            //一旦元素比 基元素 小，(即实现)
            if (baseElement > array[i]) {

                //如果这个元素 就在 基元素 的右边，交换位置即可
                if (i == indexOfBaseElement+1) {
                    int temp1 = array[i];
                    array[i] = baseElement;
                    array[indexOfBaseElement] = temp1;

                    //更新 base元素的角标
                    indexOfBaseElement++;
                    continue;
                }

                //如果这个元素 和 基元素 中间相隔了其他元素，将 基元素 和 后一位的元素 交换位置；再将 "后一位的元素" 和 这个元素交换元素
                //将 基元素 和 后一位的元素 交换位置
                int temp = array[indexOfBaseElement+1];
                array[indexOfBaseElement+1] = baseElement;
                //array[IndexOfBaseElement] = temp; //多余的步骤，放在这里方便理解

                //将 "后一位的元素" 和 这个元素交换元素
                array[indexOfBaseElement] = array[i];
                array[i] = temp;

                //更新 base元素的角标
                indexOfBaseElement++;
            }
        }

        //将当前 子数组 快速排序后，再将 基元素 左右的子数组 递归的进行一样的处理
        if (indexOfBaseElement != start) {//如果 基元素 在最左边(最小)，无需排序 左子数组
            quickSort(array,start,indexOfBaseElement-1);
        }

        if (indexOfBaseElement != end) {//如果 基元素 在最右边(最大)，无需排序 右子数组
            quickSort(array,indexOfBaseElement+1,end);
        }

    }

    public static void main(String[] args) {
        /*int[] testArray1 = new int[]{2};
        int[] testArray2 = new int[]{1};

        int[] result = sortByMerge(testArray1,testArray2);

        for (int num:result)
        System.out.print(num+":");*/

        int[] input = new int[]{2,1,9,3,5,4};
        int[] result = sortByQuick(input);

        for (int num:result)
            System.out.print(num+":");

        /*List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(1);
        list.add(3);
        for (int p=0;p<list.size();p++) {
            System.out.print(list.get(p)+">>");
        }*/
    }
}
