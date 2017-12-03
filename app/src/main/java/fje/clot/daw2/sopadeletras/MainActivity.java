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

public class MainActivity extends AppCompatActivity {

    protected Button PlayButton;
    protected EditText editTextNom;
    public static final String EXTRA_MISSATGE = "edu.fje.dam2.data";
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        ///////////////////////////////////////////////////////////////////////////
        UtilityDatabase utilitatDB = new UtilityDatabase(getBaseContext());
        // seleccionem les date per a poder escriure
        SQLiteDatabase db = utilitatDB.getWritableDatabase();
        // creem un mapa de valors on les columnes són les claus
        // ContentValues valors = new ContentValues();
        // valors.put(Partida.TaulaPartida.COLUMNA_CODI, "1237");
        // valors.put(Partida.TaulaPartida.COLUMNA_NOM, "joan");
        // valors.put(Partida.TaulaPartida.COLUMNA_PUNTUACIO, 8);

        // afegim una fila i retorna la clau primària
        // long id;
        // id = db.insert(Partida.TaulaPartida.NOM_TAULA,
                // Partida.TaulaPartida.COLUMNA_NULL, valors);

        db = utilitatDB.getReadableDatabase();

        String[] projeccio = {
                Partida.TaulaPartida._ID,
                Partida.TaulaPartida.COLUMNA_NOM,
                Partida.TaulaPartida.COLUMNA_PUNTUACIO
        };

        String seleccio ="puntuacio >= ?";
        String[] seleccioArgs = {"5"};
        String ordre =
                Partida.TaulaPartida.COLUMNA_PUNTUACIO + " DESC";


        Cursor c = db.query(
                Partida.TaulaPartida.NOM_TAULA,         // taula
                projeccio,                              // columnes
                seleccio,                               // columnes WHERE
                seleccioArgs,                           // valors WHERE
                null,                                   // GROUP
                null,                                   // HAVING
                ordre                                   // ordre
        );

        if(c != null){
            try{
                c.moveToFirst();
                while (!c.isLast()) {
                    System.out.println(c.getString(c.getColumnIndex(Partida.TaulaPartida.COLUMNA_NOM)));
                    System.out.println(c.getString(c.getColumnIndex(Partida.TaulaPartida.COLUMNA_PUNTUACIO)));
                    c.moveToNext();
                }
            }catch (Exception e) {

            }

        }



///////////////////////////////////////////////////////////////////////////////////

        editTextNom = (EditText) findViewById(R.id.editTextNom);
        PlayButton = (Button) findViewById(R.id.ButtonPlay);
        PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Go to: TableroActivity");
                sendMessage();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

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
        //setContentView(R.layout.html_layout);
    }
    public void sendMessage(){
        Intent intent = new Intent(this, TableroActivity.class);
        intent.putExtra(EXTRA_MISSATGE, editTextNom.getText().toString());
        startActivity(intent);
    }

    public void toWebview(){
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

}
