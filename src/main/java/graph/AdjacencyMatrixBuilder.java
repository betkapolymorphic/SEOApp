package graph;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Beta on 9/26/14.
 */
public class AdjacencyMatrixBuilder {
    static ArrayList<Integer> getMaxElementIndex(int[] arr){
        ArrayList<Integer> integers = new ArrayList<Integer>();


        int max = 0;
        int ind = -1;
        for(int i =0 ;i<arr.length;i++){
            if(arr[i]>max){
                max= arr[i];
                ind = i;
            }
        }
        if(ind == -1){
            return new ArrayList<Integer>();
        }
        for(int i =0 ;i<arr.length;i++){
            if(arr[i] >= max - (max >=4 ? 0 : 0)){
                integers.add(i);
            }

        }
        return integers;
    }
    public static int[][] build(int[][] matrix,int goodVal) {
        int[][] arr = new int[matrix.length][matrix.length];
        for(int j = 0;j<2;j++){
            for(int i =0 ;i<matrix.length;i++) {
                //System.out.println();
                ArrayList<Integer> maxElementIndex = getMaxElementIndex(matrix[i]);
                for(Integer index : maxElementIndex){
                    if(matrix[i][index] >= 3*j + 2){
                    matrix[i][index] = 0;
                    arr[i][index]=1;
                    }
                }
        }
            /*
            for(int j=0;j<matrix.length;j++){

                arr[i][j]= matrix[i][j] >= goodVal? 1 : 0;
            }*/
        }
        return arr;
    }
}
