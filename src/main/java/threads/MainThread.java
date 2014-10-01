package threads; /**
 * Created by Beta on 9/26/14.
 */

import frames.mainFraim;
import graph.AdjacencyMatrixBuilder;
import graph.GraphBuilder;

import searcher.GoogleSearcher;
import searcher.WebSearcher;
import searcher.YandexSearcher;
import visualizer.ExcelSaver;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class MainThread extends Thread {
    public static mainFraim fraim = null;

    static WebSearcher searcher = new YandexSearcher();
    public static void saveObj(Object o){
        ObjectOutputStream oos = null;
        try {
            oos
                    = new ObjectOutputStream(new FileOutputStream("requestname_links.ser"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Object loadObj(){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("requestname_links.ser"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object o = null;
        try {
            o =ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static List<String>getRequests(){
        List<String> list = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(new FileInputStream(fraim.filename));
            while (scanner.hasNext()){
                String s = scanner.nextLine().trim().replace("\"", "");

                System.out.println(s);
                list.add(s);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void uploadDATA(List<String> requests){
        HashMap<String,HashSet<String>> requestname_links =
                new HashMap<String, HashSet<String>>();
        int  count = 0;
        globals.GlobalStatus.setMax_range(requests.size());
        Iterator<String> iterator = requests.iterator();
        while (iterator.hasNext()){
            String s = iterator.next();
            globals.GlobalStatus.setValue(count);
            globals.GlobalStatus.setStatus("Searching... " +
                    "( "+count+" / "+requests.size()+" )");
            count++;
            if(s.contains("region")){
                iterator.remove();
               JOptionPane.showMessageDialog(null,"Вы выбрали : "+s.split("=")[1]+" регион");
                try{
                    searcher.setRegion(s.split("=")[1]);
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,"bad region!");
                }
            } else{

                ArrayList<String> searh = searcher.searh(s, 19);
                boolean flag = false;
                for(String i : searh){
                    if(!flag) {
                        requestname_links.put(s,new HashSet<String>());
                        flag = true;
                    }
                    requestname_links.get(s).add(i);
                }
            }
            //System.out.println(s);
        }
        saveObj(requestname_links);
    }
    public static void printInfo( List<String> requests,int[][]matrix){
        int ca = 1;
        for(String s:requests) {
            System.out.println(ca+++" : "+s);
        }
        for(String s : requests) {
            System.out.print(s + "|");
        }
        System.out.println();
        for(int i =0 ;i<matrix.length;i++) {
            System.out.println();
            for(int j=0;j<matrix.length;j++){
                System.out.print(matrix[i][j]+" ");
            }
        }

       /* try {
            ExcelSaver.save(requests, matrix);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
    public static int getNumberLinks(int[] row){
        int count = 0;
        for(int i:row){
            count+=i;
        }
        return count;
    }
    public static List<Integer> getAllPaths(int s_index,int[][] arr) {
        int goodval = (int)(arr.length/5.0);
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i=0;i<arr.length;i++) {
            if(arr[s_index][i]>0 /*&& getNumberLinks(arr[i])>goodval*/){

                list.add(i);
            }
        }
        //if(list.size()>=goodval)
            return list;

        //return new ArrayList<Integer>();
    }

    public static List BFS(List<Integer> allPaths,boolean[] used,int[][]arr,LinkedList<Integer> stack){
        ArrayList<Integer> list = new ArrayList<Integer>();
        while (!allPaths.isEmpty()){

            int a = allPaths.get(0);
            used[a] = true;
            list.add(a);
            List<Integer> paths = getAllPaths(a, arr);
            for(Integer i : paths) {
                if(!used[i]){
                    allPaths.add(i);
                    used[i] = true;
                }
            }
            allPaths.remove(0);
            stack.remove(new Integer(a));
        }
        return list;
    }
    public static List<List<String>> getStronglyConnectedComponent(int [][]arr,List<String> requests){
        ArrayList<List<String>> lists = new ArrayList<List<String>>();
        boolean[] used = new boolean[arr.length];
        LinkedList<Integer> stack = new LinkedList<Integer>();
        for(int i=0;i<arr.length;i++) {
            stack.addLast(i);
        }

        while (!stack.isEmpty()){
            Integer integer = stack.getLast();
            //System.out.print(requests.get(integer) + " -> ");
            List<Integer> allPaths = getAllPaths(integer, arr);
            if(!allPaths.isEmpty()){
                allPaths.clear();
                allPaths.add(0,integer);
                List<Integer> bfs = BFS(allPaths, used, arr, stack);
                ArrayList<String> strings = new ArrayList<String>();
                for(Integer i1 :bfs){
                    strings.add(requests.get(i1));
                    //System.out.println(requests.get(i1));
                }
                lists.add(strings);
            }else{
                used[integer] = true;
                stack.removeLast();
                ArrayList<String> strings = new ArrayList<String>();
                strings.add(requests.get(integer));
                lists.add(strings);
            }


        }
        return lists;
    }
    public static int getLinks(int node,int[][] matrix,List<String> requests
            ,List<String> group){
        int count = 0;
        for(String nodeName:group){
            if(matrix[node][requests.indexOf(nodeName)] > 0){
                count++;
            }
        }
        return count;
    }
    public static int[][] removeColRow(int sourcearr[][],int REMOVE_ROW
            ,int REMOVE_COLUMN){

            int rows = sourcearr.length;
            int columns = sourcearr.length;
            int destinationarr[][] = new int[rows-1][columns-1];

            int p = 0;
            for( int i = 0; i < rows; ++i)
            {
                if ( i == REMOVE_ROW)
                    continue;


                int q = 0;
                for( int j = 0; j < columns; ++j)
                {
                    if ( j == REMOVE_COLUMN)
                        continue;

                    destinationarr[p][q] = sourcearr[i][j];
                    ++q;
                }

                ++p;
            }
        return destinationarr;
    }
    public static boolean cutBadLink(List<List<String>> groups,
                                  List<String> requests,int[][]matrix){
        boolean modefied = false;
        boolean flag = true;
        Iterator<List<String>> groupsIterato = groups.iterator();

        while(groupsIterato.hasNext()){
            if(!flag){
                groupsIterato = groups.iterator();
                if(!groupsIterato.hasNext()){
                    break;
                }
            }
            flag = true;
            List<String> group = groupsIterato.next();

            Iterator<String> iterator = group.iterator();
            while (iterator.hasNext()){
                float goodVal = group.size()/4f;
                String nodeName = iterator.next();
                int node = requests.indexOf(nodeName);
                int links = getLinks(node,matrix,requests,group);
                if(links < goodVal && group.size() > 1){
                    iterator.remove();
                    List<String> newGroup = new ArrayList<String>();
                    newGroup.add(nodeName);
                    groups.add(newGroup);
                    flag = false;
                    modefied = true;
                    break;
                }
            }
        }
        return modefied;

    }
     @Override
    public void run() {

         globals.GlobalStatus.setStatus("upload data from file...");
         List<String> requests =getRequests();
         if(requests.get(0).length()<2){
             requests.remove(0);
         }
         globals.GlobalStatus.setStatus("get search info from web...");
       //uploadDATA(requests);
         HashMap<String,HashSet<String>> requestname_links = (HashMap<String, HashSet<String>>) loadObj();


         try {
             PrintWriter domainsWritter = new PrintWriter(new FileOutputStream("dom.txt"));
             Set<Map.Entry<String, HashSet<String>>> ent = requestname_links.entrySet();
             for(Map.Entry<String, HashSet<String>> m : ent){
                 domainsWritter.println("~~~"+m.getKey()+"~~~");
                 HashSet<String> strings = requestname_links.get(m.getKey());
                 for(String s : strings){
                     domainsWritter.println(s);
                 }
             }
             domainsWritter.close();
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }



         //printInfo(requests,matrix);
        //System.out.println();

         PrintWriter pw = null;
         try {
             pw = new PrintWriter(new FileOutputStream("out.txt"));
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }

         List<List<String>> stronglyConnectedComponent = new ArrayList<List<String>>();
         int[][]matrix = GraphBuilder.build(requests, requestname_links);
         {
             try {
                 PrintWriter printWriter
                         = new PrintWriter(new FileOutputStream("cluster_in.txt"));
                 printWriter.println(matrix.length);
                 for(int i = 0;i<matrix.length;i++){
                     printWriter.println(requests.get(i));
                 }
                 for(int i =0;i<matrix.length;i++){
                     for(int j = 0;j<matrix.length;j++){
                         printWriter.print(matrix[i][j]+" ");
                     }
                     printWriter.println();
                 }
                 printWriter.close();
             } catch (FileNotFoundException e) {
                 e.printStackTrace();
             }

         }
         for(int j = 0;j<1;j++){

             int[][] build = AdjacencyMatrixBuilder.build(matrix, 2);
             globals.GlobalStatus.setStatus("building strong component...");

             stronglyConnectedComponent        = getStronglyConnectedComponent(build, requests);
             globals.GlobalStatus.setStatus("cutting badLinks...");

             //if(!cutBadLink(stronglyConnectedComponent,requests,
             //        AdjacencyMatrixBuilder.build(matrix, 2))){
             //    break;
            // }


             globals.GlobalStatus.setStatus("saving info to file...");
             Iterator<List<String>> it = stronglyConnectedComponent.iterator();
             while (it.hasNext()){
                 List<String> list = it.next();
                 //if(list.size() > 1){
                     pw.write("~~~\n");
                     for(String s : list){
                         pw.write(s+"\n");
                         int index= requests.indexOf(s);
                     //   matrix= removeColRow(matrix,index,index);
                     //    requestname_links.remove(s);
                     //   requests.remove(s);
                     }
                   //  it.remove();
                 //}
             }
         }
         Iterator<List<String>> it = stronglyConnectedComponent.iterator();
     /*while (it.hasNext()){
         List<String> list = it.next();

             pw.write("~~~\n");
             for(String s : list){
                 pw.write(s+"\n");
             }

         }*/

         pw.close();/*
         try {
             ExcelSaver.save(requests,AdjacencyMatrixBuilder.build(matrix, 2));
         } catch (IOException e) {
             e.printStackTrace();
         }*/
         globals.GlobalStatus.setStatus("DONE!");
     }
}
