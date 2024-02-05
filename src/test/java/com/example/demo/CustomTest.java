package com.example.demo;

public class CustomTest {

    public CustomTest() {
        int ia [][] = {{4, 5, 6},{1, 2, 3}};
        System.out.print("Base");
    }

    public static void main(String[] args) {
        int[] a = new int[]{2,8,4,3,9,1};
        bubbleSort(a);
        System.out.println(a);
    }
    /**
     * 排序实现
     */
    public static void bubbleSort(int[] arr){
        for (int i = 0 ; i < arr.length ; i++){
            for (int j = 0 ; j < arr.length - i - 1; j++){
                if (arr[j] > arr[j + 1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }
}
