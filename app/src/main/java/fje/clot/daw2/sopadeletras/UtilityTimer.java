package fje.clot.daw2.sopadeletras;

/**
 * Created by aaron on 03/12/2017.
 */

/**
 * Clase utilidad que hace la funcion de cronometro
 */
public class UtilityTimer {
    private long currentTime;

    /**
     * Función para iniciar el cronometro
     */
    public void start(){
        currentTime = System.currentTimeMillis();
    }

    /**
     * Función para parar el cronometro
     * @return el tiempo transcurrido en milisegundos
     */
    public long stop() {
         return   System.currentTimeMillis()-currentTime;
    }
}
