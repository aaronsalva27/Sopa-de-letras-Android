package fje.clot.daw2.sopadeletras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TableroActivity extends AppCompatActivity {
    private String[] Palabras;
    private GridLayout tablero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablero);

        System.out.println("generar tabla");

        // Recuperamos palabras
        Palabras = getResources().getStringArray(R.array.palabras);

        // Enable the Up button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // recuperamos información de la activity anterior
        Intent intent = getIntent();
        String missatge = intent.getStringExtra(MainActivity.EXTRA_MISSATGE);

        tablero = (GridLayout) findViewById(R.id.grid);
        //generarTaba();

    }

    public void generarTaba(){
        Button newButton = new Button(this);
        newButton.requestLayout();



        for(int i =0; i<9; i++){
            for(int j =0 ; j<9; j++){
                newButton.setText(String.valueOf(i));
                tablero.addView(newButton,i,j);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


}
