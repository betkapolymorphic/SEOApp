import daemons.statusUpdateDaemon;
import frames.*;

import threads.MainThread;

/**
 * Created by Beta on 9/25/14.
 */
public class mainapi {
        /* List<String> requests = Arrays.asList("дубликаты номеров", "дубликаты номеров москва",
                "дубликаты автомобильных номеров", "дубликат гос номера", "сделать дубликат номера",
                "изготовить дубликат номера","номера авто восстановление","восстановление белорусских номеров",
                "Яблоки","дубликат латвийских номеров","дубликаты российских номеров");
        HashMap<String,HashSet<String>> requestname_links = (HashMap<String, HashSet<String>>) loadObj();

        //uploadDATA(requests);

       int[][]matrix = GraphBuilder.build(requests,requestname_links);
        printInfo(requests,matrix);
        System.out.println();
        JungExample2.main(AdjacencyMatrixBuilder.build(matrix,4),requests);
        int[][] build = AdjacencyMatrixBuilder.build(matrix, 3);
        for(int i = 0;i<build.length;i++) {
            for(int j=0;j<build.length;j++){
                System.out.print((build[i][j]>0? 1 : 0)+ (j <build.length-1 ? ",":""));
            }
            System.out.println();
        }
        getStronglyConnectedComponent(build,requests);*/
    public static void main(String[] args) {


        frames.mainFraim frame = new mainFraim();

        frame.setVisible(true);
        statusUpdateDaemon daemon = new statusUpdateDaemon(frame);
        Thread thread = new Thread(daemon);
        thread.setDaemon(true);
        thread.start();
        MainThread mainThread = new MainThread();
        MainThread.fraim = frame;
        //mainThread.setDaemon(true);
        //mainThread.start();
    }

}
