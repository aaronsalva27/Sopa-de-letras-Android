package fje.clot.daw2.sopadeletras;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Actividad que sale al final de una partida con tu puntuación.
 */
public class activity_final extends AppCompatActivity {
    // boton para volver al inicio
    protected Button buttonAtras;
    // textView con los segundos que el jugador a tardado
    protected TextView textViewPuntSegundos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        // recuperamos información de la activity anterior
        Intent intent = getIntent();
        String missatge = intent.getStringExtra(TableroActivity.EXTRA_MISSATGE);


        textViewPuntSegundos = (TextView) findViewById(R.id.textViewPuntSegundos);
        textViewPuntSegundos.setText(missatge +" segons");
        buttonAtras = (Button) findViewById(R.id.buttonAtras);
        // función anonima para volver al inicio
        buttonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Go to: MainActivity");
                toMainActivity();
            }
        });
    }

    //Mètode per anar al Main Activity
    public void toMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
