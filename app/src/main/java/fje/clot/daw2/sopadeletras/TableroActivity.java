package fje.clot.daw2.sopadeletras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.R.attr.max;

public class TableroActivity extends AppCompatActivity {
    private String[] palabras;
    private GridView tablero;
    ArrayAdapter<String> Adapter;
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
        generarTaba();

    }

    public void generarTaba(){
        String[] letras = new String[90];

        for (int i = 0; i<letras.length;i++){
            letras[i] = String.valueOf(numbers[(int) (Math.random()*25+1)]);
        }

        generarSopaDeLetras();

        Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, letras);
        tablero.setAdapter(Adapter);

    }

    public void generarSopaDeLetras(){
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

    }

    public void rellenarArray(char[] fila,String palabra){

        // posibles combinaciones
        int posicionesPosibles = 10-fila.length+1;
        // posicion inicio
        int randomNum = (int) (Math.random()*posicionesPosibles+1);

        System.out.println(palabra.toUpperCase());
        System.out.println("POSSIBLES"+posicionesPosibles);

        // rellenamos con palabras random;
        for (int i = 0; i<fila.length;i++){
            fila[i] = numbers[(int) (Math.random()*25+1)];
        }

        for (int i = randomNum; i<fila.length;i++){
            for (int j = 0; j < palabra.length(); j++) {
                fila[i] = (palabra.toCharArray()[j]);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


}
