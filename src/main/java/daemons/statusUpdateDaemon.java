package daemons;

import frames.mainFraim;

/**
 * Created by Beta on 9/26/14.
 */
public class statusUpdateDaemon implements Runnable {
    private mainFraim fraim;

    public statusUpdateDaemon(mainFraim fraim) {
        this.fraim = fraim;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()){
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {

                System.out.println(e);
            }
            //System.out.println(1);
            this.fraim.getStatusLabel().setText(globals.GlobalStatus.getStatus());
            this.fraim.getjProgressBar1().setMaximum(globals.GlobalStatus.getMax_range());
            this.fraim.getjProgressBar1().setValue(globals.GlobalStatus.getValue());

        }
    }
}
