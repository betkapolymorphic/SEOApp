package alg;

/**
 * Created by Beta on 10/1/14.
 */
public class Clustering {
    static class Pair
    {
        Pair(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        int x;
        int y;
        int distance;

        @Override
        public String toString() {
            return "Pair{" +
                    "x=" + x +
                    ", y=" + y +
                    ", distance=" + distance +
                    '}';
        }
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
    static Pair getMinDistance(int [][]arr){
        Pair p = new Pair(-1,-1,100500);
        for(int i=0;i<arr.length;i++) {
            for(int j = i;j<arr.length ;j++){
                if(arr[i][j] < p.distance && arr[i][j]!=0) {
                    p.y = i;
                    p.x = j;
                    p.distance = arr[i][j];
                }
            }
        }
        return  p;
    }
    public static void main(String[] args) {
        int[][] arr = {
                {0,662,877,255,412,966},
                {662,0,295,468,268,400},
                {877,295,0,754,564,138},
                {255,468,754,0,219,869},
                {412,268,564,219,0,669},
                {966,400,138,869,699,0}};
        while(arr.length>0){
            Pair minDistance = getMinDistance(arr);
            System.out.println(minDistance);
            if(minDistance.x!=-1)
                arr = removeColRow(arr,minDistance.x,minDistance.x);
            else
                break;
        }
    }
}
