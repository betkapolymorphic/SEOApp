package graph;

import java.util.*;

/**
 * Created by Beta on 9/26/14.
 */

public class GraphBuilder {
    static void incrementMatrix(List<String> strings,String val1,String val2,int[][] arr){
        int i = strings.indexOf(val1);
        int i1 = strings.indexOf(val2);
        arr[i1][i]++;
        //arr[i1][i]++;
    }

    public static int[][] build(List<String> requests,HashMap<String,HashSet<String>> requestname_links) {
        int[][] matrix = new int[requests.size()][requests.size()];
        int val = 0;
        globals.GlobalStatus.setStatus("building matrix...");



        Set<Map.Entry<String,HashSet<String>>> entries = requestname_links.entrySet();
        int size = requests.size();
        globals.GlobalStatus.setMax_range(size*size*
                requestname_links.get(requests.get(0)).size());

        Iterator<Map.Entry<String, HashSet<String>>> iterator = entries.iterator();


        while (iterator.hasNext()){
            Map.Entry<String, HashSet<String>> e = iterator.next();


            for(String  comparedVal : e.getValue()){
                Iterator<Map.Entry<String, HashSet<String>>> iteratorforeach = entries.iterator();
                while (iteratorforeach.hasNext()) {
                    Map.Entry<String, HashSet<String>> next = iteratorforeach.next();

                    if(next.getKey().equals(e.getKey())){
                        globals.GlobalStatus.setValue(val++);
                        continue;
                    }
                    if(next.getValue().contains(comparedVal)){
                        int i = requests.indexOf(next.getKey());
                        int i1 = requests.indexOf(e.getKey());
                        if(i1==0 && i==2){
                            int p = 2;
                        }
                        incrementMatrix(requests, next.getKey(), e.getKey(), matrix);
                    }
                    globals.GlobalStatus.setValue(val++);
                }
            }
        }
        return matrix;

    }
}
