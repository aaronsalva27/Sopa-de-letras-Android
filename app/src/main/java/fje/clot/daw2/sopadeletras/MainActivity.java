package fje.clot.daw2.sopadeletras;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    protected Button PlayButton;
    public static final String EXTRA_MISSATGE = "edu.fje.dam2.data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        // Handle item selection
        return  true;
    }

    public void sendMessage(){
        Intent intent = new Intent(this, TableroActivity.class);
        intent.putExtra(EXTRA_MISSATGE, "New game");
        startActivity(intent);
    }

}
