package fje.clot.daw2.sopadeletras;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.sql.Time;

/**
 * Created by arand on 02/12/2017.
 */

/**
 * Clase que tiene las propiedades de la partida
 */
public class Partida {
    // Nombre del jugador
    public String Nom;
    // tiempo que ha tardado
    public long Puntuacion;

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public long getPuntuacion() {
        return Puntuacion;
    }

    public void setPuntuacion(long puntuacion) {
        Puntuacion = puntuacion;
    }

    /**
     * Clase con atributos estaticos que representan valores
     * de la tabla de partida
     */
    public static abstract class TaulaPartida implements BaseColumns {
        public static final String NOM_TAULA = "partida";
        public static final String COLUMNA_CODI = "codi";
        public static final String COLUMNA_NOM = "nom";
        public static final String COLUMNA_PUNTUACIO = "puntuacio";
        public static final String COLUMNA_NULL = "null";
    }
}
