package fje.clot.daw2.sopadeletras;

/**
 * Created by aaron on 03/12/2017.
 */

public class UtilityTimer {
    private long currentTime;


    public void start(){
        currentTime = System.currentTimeMillis();
    }

    public long stop() {
         return   System.currentTimeMillis()-currentTime;
    }
}
