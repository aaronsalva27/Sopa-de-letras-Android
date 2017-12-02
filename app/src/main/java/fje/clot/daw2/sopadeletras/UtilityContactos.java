package fje.clot.daw2.sopadeletras;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by aaron on 02/12/2017.
 */

public class UtilityContactos extends AppCompatActivity {
    public ArrayList<String> palabras =  new ArrayList<>();

    public String[] gestionarContactos(Cursor contactosCursor){
        contactosCursor.moveToFirst();

        while(!contactosCursor.isAfterLast()) {
            System.out.println(contactosCursor.getString(contactosCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)).split(" ")[0]);
            String newPalabra = contactosCursor.getString(contactosCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)).split(" ")[0].toUpperCase();

            if(!Arrays.asList(palabras).contains(newPalabra)){
                if(newPalabra.length()<9){
                    palabras.add(newPalabra);
                }
            }
            contactosCursor.moveToNext();
        }

        return palabras.toArray(new String[palabras.size()]);
    }

}
