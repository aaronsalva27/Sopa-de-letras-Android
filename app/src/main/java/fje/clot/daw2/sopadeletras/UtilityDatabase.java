package fje.clot.daw2.sopadeletras;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;


/**
 * Clase que gestiona la base de datos
 */
public class UtilityDatabase extends SQLiteOpenHelper {

    private static final String TIPUS_TEXT = " TEXT";
    private static final String TIPUS_ENTER = " INT";
    private static final String SEPARADOR_COMA = ",";
    private static final String SQL_CREACIO_TAULA = "CREATE TABLE "
            + Partida.TaulaPartida.NOM_TAULA + " ("
            + Partida.TaulaPartida._ID + " INTEGER PRIMARY KEY,"
            + Partida.TaulaPartida.COLUMNA_CODI + TIPUS_TEXT
            + SEPARADOR_COMA + Partida.TaulaPartida.COLUMNA_NOM
            + TIPUS_TEXT + SEPARADOR_COMA
            + Partida.TaulaPartida.COLUMNA_PUNTUACIO + TIPUS_ENTER + " )";

    private static final String SQL_ESBORRAT_TAULA = "DROP TABLE IF EXISTS "
            + Partida.TaulaPartida.NOM_TAULA;

    // Si canviem l'esquema hem de canviar la versió
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Puntuacions.db";


    //Constructor
    public UtilityDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREACIO_TAULA);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_ESBORRAT_TAULA);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
