/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.quickselectalgorithm;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author stipanmadzar
 */
public class QuickSelectAlgorithm {

    static Random rand = new Random();
    static int groupSize;
    static int comparisons = 0;
    static int exchanges = 0;
  
    private static int findPivotIndex(int[] arr, int pivot) {
        
        for(int i = 0; i < arr.length; ++i) {
            comparisons++;
            if(arr[i] == pivot) {
                return i;
            }
            
        }
        return 0;
    }
    
    
    private static int partitioning(int[] arr, int left, int right, int pivot) {
        
        int pivotIndex = findPivotIndex(arr, pivot);
        arr[pivotIndex] = arr[right];
        arr[right] = pivot;
        
        int start = left;
        
        for(int i = left; i < right; i++) {
            
            if(arr[i] <= pivot) {
                int temp = arr[i];
                arr[i] = arr[start];
                arr[start] = temp;
                start++;
                exchanges++;
            }   
            comparisons++;
        }
       
        int temp2 = arr[start];
        arr[start] = arr[right];
        arr[right] = temp2;
        exchanges++;
        
        return start; 
    }
    
    private static int randomizedPartitioning(int[] arr, int left, int right) {
        int medianOfMedians = getPivotValue(arr, left, right);
        return partitioning(arr, left, right, medianOfMedians);
    }
    
    private static int randomizedSelect(int[] arr, int left, int right, int k) {
        
        comparisons++;
        if(left == right) {
            return arr[left];   
        }
        
        int pivotIndex = randomizedPartitioning(arr, left, right);
        
        comparisons++;
        if(k == pivotIndex + 1) {
            return arr[pivotIndex];           
        }
        else if(k < pivotIndex + 1) {
            comparisons++;
            return randomizedSelect(arr, left, pivotIndex - 1, k);
        }
        else {
            comparisons++;
            return randomizedSelect(arr, pivotIndex + 1, right, k);
        }
    }
    
    private static int getPivotValue(int[] arr, int left, int right) {
        
        comparisons++;
        if(right == left) {
            return arr[left];
        }
        
        int[] temp = null;
        int medians[] = new int[(int) Math.ceil((double)(right - left + 1)/groupSize)];
        int medianIndex = 0;
        
        while(right >= left) {
            temp = new int[Math.min(groupSize, right-left+1)];
            
            for(int i = 0; i < temp.length && left <= right; i++) {
                temp[i] = arr[left];
                left++;
                comparisons++;
            }

        Arrays.sort(temp);
        medians[medianIndex] = temp[temp.length/2];
        medianIndex++;
        comparisons++;
        
        }
        return getPivotValue(medians, 0, medians.length - 1);   
    }

    public static void main(String[] args) {
       
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter array size: ");
        int size = scan.nextInt();
        
        System.out.print("Enter Kth smallest element: ");
        int k = scan.nextInt();
        
        System.out.print("Enter group size: ");
        groupSize = scan.nextInt();
        
        if(groupSize > 2 && groupSize <= size) {
        
        int[] arr = new int[size];       
        for(int i = 0; i < size; ++i) {
            arr[i] = rand.nextInt(100) + 1;
        }
        System.out.println("\nKth smallest element is: " + randomizedSelect(arr, 0, arr.length - 1, k));
        System.out.println("Number of operations: " + (exchanges + comparisons));
        }
        else {
            System.out.println("Error! Group size must be greater than 2 and less or equal"
                    + " to the array size.");
        }
    }
}