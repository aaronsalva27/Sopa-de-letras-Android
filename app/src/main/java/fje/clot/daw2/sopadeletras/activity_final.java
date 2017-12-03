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

public class activity_final extends AppCompatActivity {

    protected Button buttonAtras;
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
