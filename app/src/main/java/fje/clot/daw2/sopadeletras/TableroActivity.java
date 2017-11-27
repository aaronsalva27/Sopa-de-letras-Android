package fje.clot.daw2.sopadeletras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import java.util.Arrays;

import static android.R.attr.max;

public class TableroActivity extends AppCompatActivity {
    private String[] palabras;
    private GridView tablero;
    private String palabraActual="";
    private Button btReset;
    String[] letras;
    ArrayAdapter<String> Adapter;
    private TextView lbActual;
    private ArrayList<Integer> palabrasIndex = new ArrayList<Integer>();
    private ArrayList<Integer> palabrasIndexCorrectas = new ArrayList<Integer>();
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

        System.out.println("generar tabla");

        // Recuperamos palabras
        palabras = getResources().getStringArray(R.array.palabras);

        // Enable the Up button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // recuperamos información de la activity anterior
        Intent intent = getIntent();
        String missatge = intent.getStringExtra(MainActivity.EXTRA_MISSATGE);

        tablero = (GridView) findViewById(R.id.grid);
        lbActual = (TextView) findViewById(R.id.lbActual);
        btReset = (Button) findViewById(R.id.btReset);

        tablero.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println( letras[position]);
                palabrasIndex.add(position);

                view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

                palabraActual += letras[position];
                lbActual.setText(palabraActual);
                checkPalabra(palabraActual);
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ENtra en el boton");
                for(Integer item : palabrasIndex ){
                    tablero.getChildAt(item).setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
                palabrasIndex.clear();
                palabraActual = "";
                lbActual.setText(palabraActual);

            }
        });
        generarTaba();
    }

    public void checkPalabra(String palabra){
        System.out.println("checkeando");
        if(Arrays.asList(palabras).contains(palabra)) {
            joinArray(palabrasIndex,palabrasIndexCorrectas);
            palabrasIndex.clear();
            palabraActual = "";
            lbActual.setText(palabraActual);

            for(Integer item : palabrasIndexCorrectas ){
                tablero.getChildAt(item).setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            }

        }
    }

    public void joinArray(ArrayList<Integer> palabrasIndex, ArrayList<Integer> palabrasIndexCorrectas){
        for (int i = 0 ;i<palabrasIndex.size();i++) {
            palabrasIndexCorrectas.add(palabrasIndex.get(i));
        }
    }

    public void generarTaba(){
        ArrayList<char[]> filasParsed;
        ArrayList<String>  sopa = new ArrayList<String>();

        filasParsed = generarSopaDeLetras();

        System.out.println("TAMAÑO: "+filasParsed.size());
        System.out.println(filasParsed.get(0)[0]);

        for(int i = 0; i<filasParsed.size();i++) {
            for (int j = 0 ; j<10 ;j++) {
                sopa.add(String.valueOf(filasParsed.get(i)[j]));
            }
        }

        letras = sopa.toArray(new String[sopa.size()]);

        Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, letras);
        tablero.setAdapter(Adapter);

    }

    public ArrayList<char[]> generarSopaDeLetras(){
        ArrayList<char[]> filas = new ArrayList<char[]>();


        // creamos 9 arrays
        for(int i = 0 ; i<9; i++){
            filas.add(new char[10]);
        }

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

    public void rellenarArray(char[] fila,String palabra){

        // posibles combinaciones
        int posicionesPosibles = 10-palabra.length()+1;
        // posicion inicio
        int randomNum = (int) (Math.random()*posicionesPosibles+1);

        // rellenamos con palabras random;
        for (int i = 0; i<fila.length;i++){
            fila[i] = numbers[(int) (Math.random()*25+1)];
        }

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
