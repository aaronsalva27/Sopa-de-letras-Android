package fje.clot.daw2.sopadeletras;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by aaron on 02/12/2017.
 */

/**
 * Clase utilidad que gestiona los contactos
 */
public class UtilityContactos extends AppCompatActivity {
    // ArrayList con los contactos
    public ArrayList<String> palabras =  new ArrayList<>();

    /**
     * Funición que coge nueve contactos y los devuelve
     * @param contactosCursor cursors con todos los contactos
     * @return array con los contactos parseados
     */
    public String[] gestionarContactos(Cursor contactosCursor){
        // movemos el cursor al inicio
        contactosCursor.moveToFirst();
        // itera sobre los contactos
        while(!contactosCursor.isAfterLast()) {
            // un nuevo contacto
            String newPalabra = contactosCursor.getString(contactosCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)).split(" ")[0].toUpperCase();
            // si ya existe o tu tamaño es mas grande de 9 no lo añade al array
            if(!Arrays.asList(palabras).contains(newPalabra)){
                if(newPalabra.length()<9){
                    palabras.add(newPalabra);
                }
            }
            contactosCursor.moveToNext();
        }
        // devuelve un array con 9 posiciones
        return Arrays.copyOfRange(palabras.toArray(new String[palabras.size()]), 0, 9);
    }

}
