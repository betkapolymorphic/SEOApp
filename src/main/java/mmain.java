import com.apporiented.algorithm.clustering.*;
import com.apporiented.algorithm.clustering.visualization.DendrogramPanel;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Beta on 10/1/14.
 */
public class mmain {
    static int editdist(String S1, String S2) {
        int m = S1.length(), n = S2.length();
        int[] D1;
        int[] D2 = new int[n + 1];

        for(int i = 0; i <= n; i ++)
            D2[i] = i;

        for(int i = 1; i <= m; i ++) {
            D1 = D2;
            D2 = new int[n + 1];
            for(int j = 0; j <= n; j ++) {
                if(j == 0) D2[j] = i;
                else {
                    int cost = (S1.charAt(i - 1) != S2.charAt(j - 1)) ? 1 : 0;
                    if(D2[j - 1] < D1[j] && D2[j - 1] < D1[j - 1] + cost)
                        D2[j] = D2[j - 1] + 1;
                    else if(D1[j] < D1[j - 1] + cost)
                        D2[j] = D1[j] + 1;
                    else
                        D2[j] = D1[j - 1] + cost;
                }
            }
        }
        return D2[n];
    }
    public static List<List<String>> splitToGloups(Cluster cluster,int height){
        List<List<String>> lists = new ArrayList<List<String>>();
        Stack<Cluster> stack = new Stack<Cluster>();
        stack.push(cluster);
        while (!stack.isEmpty()){
            Cluster pop = stack.pop();
            if(pop.getChildren() == null || pop.getDistance()==null){
                ArrayList<String> group = new ArrayList<String>();
                String[] split = pop.getName().split("&");
                for(String s : split){
                    group.add(s);
                }
                lists.add(group);
            }else if(-pop.getDistance() >=height){
                ArrayList<String> group = new ArrayList<String>();
                String[] split = pop.getName().split("&");
                for(String s : split){
                    group.add(s);
                }
                lists.add(group);
            }else {
                for(Cluster child : pop.getChildren()){
                    stack.push(child);
                }
            }

        }

        return lists;
    }

    static  int getDistanceWord(String s1,String s2,double normval){
        int sum = 0;
        String[] split = s1.split(" ");
        String[] split1 = s2.split(" ");
        for(String str : split) {
            int min = Integer.MAX_VALUE;
            for(String str1 : split1){

                int cost =editdist(str,str1);
                if(cost < min) {
                    min = cost;
                }
            }
            sum+=min;
            if(sum>normval){
                return -1;
            }
        }

        return sum;
    }
    public static double[][] getDistanceMatrix(String[] words){
        double goodVal = 12;
        double[][] matrix = new double[words.length][words.length];
        for(int i = 0 ;i<matrix.length;i++){
            String mainWodr = words[i];
            for(int j = 0;j<matrix.length;j++){

                matrix[i][j] = getDistanceWord(mainWodr, words[j],goodVal);
                if(matrix[i][j]!=-1)
                    matrix[i][j] = Math.abs(goodVal - matrix[i][j])/3.0;
                else
                    matrix[i][j]=0;
            }
        }

        return matrix;
    }
    static double[][]sumToMatrix(double[][]A,double[][]B){
        double[][]C = new double[A.length][A.length];
        for(int i =0 ;i<A.length;i++){
            for(int j=0;j<A.length;j++) {

                C[i][j] = A[i][j]+B[i][j];
            }
        }
        return C;
    }
    public static void main(String[] args) {
        String [] names
                = new String[] { "BA", "FI", "MI", "NA", "RM", "TO" };
        double[][] distances = new double[][] {
                {0,662,877,255,412,966},
                {662,0,295,468,268,400},
                {877,295,0,754,564,138},
                {255,468,754,0,219,869},
                {412,268,564,219,0,669},
                {966,400,138,869,699,0}};
        try {
            Scanner sc = new Scanner(new FileInputStream("cluster_in.txt"));
            int l  = Integer.parseInt(sc.nextLine());
            distances = new double[l][l];
            names = new String[l];
            for(int i = 0;i< l;i++) {
                names[i] = sc.nextLine();
            }
            for(int i = 0;i < l;i++){
                for(int j = 0;j <l;j++) {
                        distances[i][j] = sc.nextInt();
                }
            }
            distances = sumToMatrix(distances,getDistanceMatrix(names));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int i = 0;i<distances.length;i++) {
            for(int j =0;j<distances.length;j++) {
                distances[i][j] = - distances[i][j];
            }
        }
        ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
        Cluster cluster = alg.performClustering(distances, names,
                new CompleteLinkageStrategy());
        List<List<String>> lists = splitToGloups(cluster,2);
        int ci = 0;
        for(List<String> ls : lists){
            System.out.println("~~~~~~~~~~~~~~~");
            for(String s : ls){
                System.out.println(s);
            }

        }
        int distanceWord = getDistanceWord("восстановление казахстанских номеров", "восстановление казахских номеров", 9.0);
        System.out.println(distanceWord);
    }
}
