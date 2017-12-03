package fje.clot.daw2.sopadeletras;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.Dictionary;

/**
 * Clase MainActivity
 * Clase que gestiona el inicio de la partida, pregunta por un nombre
 * y lo envia a la siguiente actividad. Además recuperar las mejores
 * partidas de la BD.
 */
public class MainActivity extends AppCompatActivity {
    // boton para iniciar partida
    protected Button PlayButton;
    // nombre del jugador
    protected EditText editTextNom;
    public static final String EXTRA_MISSATGE = "edu.fje.dam2.data";
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    protected TextView textViewPuntuacions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // pedimos permisos para leer los contactos
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this ,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }


        UtilityDatabase utilitatDB = new UtilityDatabase(getBaseContext());
        // seleccionem les date per a poder escriure
        SQLiteDatabase db = utilitatDB.getWritableDatabase();

        db = utilitatDB.getReadableDatabase();

        ArrayList<String> puntuacions = new ArrayList<String>();

        String[] projeccio = {
                Partida.TaulaPartida._ID,
                Partida.TaulaPartida.COLUMNA_NOM,
                Partida.TaulaPartida.COLUMNA_PUNTUACIO
        };


        String seleccio ="puntuacio >= ?";
        String[] seleccioArgs = {"5"};
        String ordre =
                Partida.TaulaPartida.COLUMNA_PUNTUACIO + " ASC";

        // recuperamos las partidas
        Cursor c = db.query(
                Partida.TaulaPartida.NOM_TAULA,         // taula
                projeccio,                              // columnes
                seleccio,                               // columnes WHERE
                seleccioArgs,                           // valors WHERE
                null,                                   // GROUP
                null,                                   // HAVING
                ordre                                   // ordre
        );

        // iteramos sobre los resultados obtenidos
        if(c != null){
            try{
                c.moveToFirst();
                while (!c.isLast()) {
                    //System.out.println(c.getString(c.getColumnIndex(Partida.TaulaPartida.COLUMNA_NOM)));
                    //System.out.println(c.getString(c.getColumnIndex(Partida.TaulaPartida.COLUMNA_PUNTUACIO)));

                    puntuacions.add(c.getString(c.getColumnIndex(Partida.TaulaPartida.COLUMNA_NOM)) + " - "+
                            c.getString(c.getColumnIndex(Partida.TaulaPartida.COLUMNA_PUNTUACIO)) + " segons");

                    c.moveToNext();

                }
            }catch (Exception e) {

            }
        }

        textViewPuntuacions = (TextView) findViewById(R.id.textViewPuntuacions);

        String text = "";
        for(int i = 0 ; i < puntuacions.size(); i++) {
            if(i > 10) return;
            text += i+1+ " " +  puntuacions.get( i ) + "\n";
        }

        textViewPuntuacions.setText(text);


        editTextNom = (EditText) findViewById(R.id.editTextNom);
        PlayButton = (Button) findViewById(R.id.ButtonPlay);

        // función anonima para cambiar de actividad
        PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Go to: TableroActivity");
                sendMessage();
            }
        });
    }

    /**
     * recuperamos el menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * funcion que se utiliza para gestionar las opciones del menú.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                obrirAjustos();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void obrirAjustos() {
        toWebview();

    }

    /**
     * realizamos un intent y passamos el nombre del jugador
     */
    public void sendMessage(){
        Intent intent = new Intent(this, TableroActivity.class);
        intent.putExtra(EXTRA_MISSATGE, editTextNom.getText().toString());
        startActivity(intent);
    }

    /**
     * función para abrir el webview
     */
    public void toWebview(){
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

}
