package fje.clot.daw2.sopadeletras;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.sql.Time;

/**
 * Created by arand on 02/12/2017.
 */

public class Partida {

    public String Nom;
    public long Puntuacion;

    public static abstract class TaulaPartida implements BaseColumns {
        public static final String NOM_TAULA = "partida";
        public static final String COLUMNA_CODI = "codi";
        public static final String COLUMNA_NOM = "nom";
        public static final String COLUMNA_PUNTUACIO = "puntuacio";
        public static final String COLUMNA_NULL = "null";
    }
}
