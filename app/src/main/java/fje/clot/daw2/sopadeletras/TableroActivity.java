package fje.clot.daw2.sopadeletras;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import java.util.Arrays;

import static android.R.attr.max;

/**
 * Clase que gestiona el juego
 */
public class TableroActivity extends AppCompatActivity {
    // array con las palabras
    private String[] palabras;
    // instancia de la clase partida
    private Partida partida = new Partida();
    // tablero de juego
    private GridView tablero;
    // lista de palabras
    private GridView gridPalabras;
    // instancia de la clase timer
    private UtilityTimer timer = new UtilityTimer();
    // puntos inciales (palabras respondidas)
    private int puntos = 0;
    // palabra actual
    private String palabraActual="";
    // boton para resetear palabraActul
    private Button btReset;
    // array con las letras del tablero
    String[] letras;
    public static final String EXTRA_MISSATGE = "edu.fje.dam2.data";
    // instancia de la clase UtilityContactos
    private UtilityContactos uc = new UtilityContactos();
    // adaptador del tablero
    ArrayAdapter<String> Adapter;
    // adaptador de la lista de palabras
    ArrayAdapter<String> PalabrasAdapter;
    // label con la palabra actua
    private TextView lbActual;
    // array con los index de la palabra actual
    private ArrayList<Integer> palabrasIndex = new ArrayList<Integer>();
    // array con los index de las palabras correctas ya respondidas
    private ArrayList<Integer> palabrasIndexCorrectas = new ArrayList<Integer>();
    // abc
    static final char[] numbers = new char[] {
            'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablero);


        // obtenemos los contactos
        Cursor mCursor = obtenirContactes();
        // gestionamos los contactos
        palabras = uc.gestionarContactos(mCursor);

        // Recuperamos palabras
        //palabras = getResources().getStringArray(R.array.palabras);

        // Enable the Up button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // recuperamos información de la activity anterior
        Intent intent = getIntent();
        String missatge = intent.getStringExtra(MainActivity.EXTRA_MISSATGE);
        partida.Nom = missatge;

        tablero = (GridView) findViewById(R.id.grid);
        lbActual = (TextView) findViewById(R.id.lbActual);
        btReset = (Button) findViewById(R.id.btReset);
        gridPalabras = (GridView) findViewById(R.id.gridPalabras);

        // añadimos la lista de palabras al adaptador Palabras Adapter
        PalabrasAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, palabras);
        gridPalabras.setAdapter(PalabrasAdapter);


        // funcion anonima que se ejecuta cuando pulsamos una posicion del tablero
        tablero.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println( letras[position]);
                // añade la posicion de la letra
                palabrasIndex.add(position);
                // cambia la posicion pulsada for un fondo gris
                view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                // añade una letra mas a la palabra actual
                palabraActual += letras[position];

                lbActual.setText(palabraActual);
                // comprueba que la palabra actual es correcta
                checkPalabra(palabraActual);
            }
        });

        // funcion anonima que resetea la palabra actual
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ENtra en el boton");
                // vuelve a pintar la casilla de color blanco
                for(Integer item : palabrasIndex ){
                    tablero.getChildAt(item).setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
                // vacia el array de palabrasIndex
                palabrasIndex.clear();
                // settea palabraActual a un string empty
                palabraActual = "";
                lbActual.setText(palabraActual);

            }
        });

        // generamos la tabla
        generarTaba();

        // empezamos a contar el tiempo
        timer.start();
    }

    /**
     *  Funcion que guarda en la BD una nueba partida
     * @param nombre del jugador
     * @param puntuacion tiempo que ha tadado en resolverlo
     */
    private void savePartida(String nombre, long puntuacion) {
        UtilityDatabase utilitatDB = new UtilityDatabase(getBaseContext());
        // seleccionem les date per a poder escriure
        SQLiteDatabase db = utilitatDB.getWritableDatabase();
        // creem un mapa de valors on les columnes són les claus
        ContentValues valors = new ContentValues();
        valors.put(Partida.TaulaPartida.COLUMNA_CODI, nombre);
        valors.put(Partida.TaulaPartida.COLUMNA_NOM, nombre);
        valors.put(Partida.TaulaPartida.COLUMNA_PUNTUACIO, puntuacion);

        // afegim una fila i retorna la clau primària
        long id;
        id = db.insert(Partida.TaulaPartida.NOM_TAULA,
                Partida.TaulaPartida.COLUMNA_NULL, valors);
    }
    // funcion que devuelve los contactos de la BD contactos
    private Cursor obtenirContactes() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projeccio = new String[]{ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME};
        String seleccio = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '"
                + ("1") + "'";
        String[] argsSeleccio = null;
        String ordre = ContactsContract.Contacts.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";

        return getContentResolver().query(uri, projeccio, null, null, null);
    }

    /**
     * Función que comprueba que la palabra actual esta en la sopa de letras.
     * @param palabra
     */
    public void checkPalabra(String palabra){
        System.out.println("checkeando");
        // entra si contiene la palabra
        if(Arrays.asList(palabras).contains(palabra)) {
            // añade las posiciones de la palabra dentro de todas las posiciones correctas
            joinArray(palabrasIndex,palabrasIndexCorrectas);
            palabrasIndex.clear();
            palabraActual = "";
            lbActual.setText(palabraActual);
            // si existe pinta las posiciones de la pabra de color verde.
            for(Integer item : palabrasIndexCorrectas ){
                tablero.getChildAt(item).setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            }

            // mostramos un toast con un mensaje
            Context context = getApplicationContext();
            CharSequence text = "Correct!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            // sumamos un punto
            puntos += 1;
            // si ha respondido todas las palabras
            if (puntos == 9) {
                // el jugador es enviado a activity_final
                Intent intent = new Intent(this, activity_final.class);
                // paramos el contador
                partida.Puntuacion = timer.stop()/1000;
                // guardamos la partida
                this.savePartida(partida.Nom,partida.Puntuacion);
                intent.putExtra(EXTRA_MISSATGE, String.valueOf(timer.stop()/1000));
                startActivity(intent);
            }

        }
    }

    /**
     * Función que mezcla dos arrays
     * @param palabrasIndex indices de las letras de la palabra actual
     * @param palabrasIndexCorrectas indices de las palabras ya respondidas
     */
    public void joinArray(ArrayList<Integer> palabrasIndex, ArrayList<Integer> palabrasIndexCorrectas){
        for (int i = 0 ;i<palabrasIndex.size();i++) {
            palabrasIndexCorrectas.add(palabrasIndex.get(i));
        }
    }

    /**
     * Función que genera la tabla de la sopa de letras.
     */
    public void generarTaba(){
        // array con las filas de la tabla ya parseadas
        ArrayList<char[]> filasParsed;
        // array list con las letras de la tabla
        ArrayList<String> sopa = new ArrayList<String>();

        filasParsed = generarSopaDeLetras();

        System.out.println("TAMAÑO: "+filasParsed.size());
        System.out.println(filasParsed.get(0)[0]);

        // juntamos todas las arrays dentro de sopa
        for(int i = 0; i<filasParsed.size();i++) {
            for (int j = 0 ; j<10 ;j++) {
                sopa.add(String.valueOf(filasParsed.get(i)[j]));
            }
        }
        // devolvemos el contenido de sopa dentro de letras
        letras = sopa.toArray(new String[sopa.size()]);

        Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, letras);
        tablero.setAdapter(Adapter);

    }

    /**
     * Funció que crea un array multidimensional con las filas de la tabla
     * y les añade valores.
     * @return filas
     */
    public ArrayList<char[]> generarSopaDeLetras(){
        ArrayList<char[]> filas = new ArrayList<char[]>();


        // creamos 9 arrays
        for(int i = 0 ; i<9; i++){
            filas.add(new char[10]);
        }

        // comprueba si la palabra es mas grande de 9 no la añade,
        // si es igual a 9 la añade directamente en la fila
        // si es mas pequeña que 9 le envia a rellenar array
        for (int i = 0; i< 9; i++) {
            if(palabras[i].length()>9){
                for (int j = 0; j < palabras[i].length(); j++) {
                    filas.get(i)[j] = (palabras[i].toCharArray()[j]);
                }
            }else {
                rellenarArray(filas.get(i),palabras[i]);
            }
        }

        for (int i = 0; i < filas.size(); i++){
            for (int j = 0 ; j < filas.get(i).length ;j++){
                System.out.print(filas.get(i)[j]);
                System.out.print(" - ");
            }
            System.out.println(" ");
        }

        return filas;
    }

    /**
     * Función que añade palabras dentro de una fila en un indice aleatorio y rellena los bordes
     * @param fila
     * @param palabra
     */
    public void rellenarArray(char[] fila,String palabra){

        // posibles combinaciones (indices)
        int posicionesPosibles = 10-palabra.length()+1;
        // posicion inicio
        int randomNum = (int) (Math.random()*posicionesPosibles+1);

        // rellenamos con palabras random;
        for (int i = 0; i<fila.length;i++){
            fila[i] = numbers[(int) (Math.random()*25+1)];
        }
        // añadimos la palabra a partir del indice randomNum
        for (int i = 0; i< palabra.length(); i++) {
            fila[randomNum-1+i] = palabra.toCharArray()[i];
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


}
