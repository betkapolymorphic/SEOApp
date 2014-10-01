package globals;

/**
 * Created by Beta on 9/26/14.
 */
public class GlobalStatus {
    private static  String status= "Без статуса";
    private static int min_range =0;
    private static int max_range =0;

    public static synchronized int getMax_range() {
        return max_range;
    }

    public static  synchronized  void setMax_range(int max_range) {
        GlobalStatus.max_range = max_range;
    }

    public static  synchronized  int getValue() {
        return min_range;
    }

    public static synchronized  void setValue(int min_range) {
        GlobalStatus.min_range = min_range;
    }

    public static synchronized String getStatus() {
        return status;
    }

    public static synchronized void setStatus(String status) {
        GlobalStatus.status = status;
    }
}
