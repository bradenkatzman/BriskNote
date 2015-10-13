package bradenkatzman.brisknote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class BriskNote extends AppCompatActivity {

    private String firstWord;

    private final static String ext = ".txt"; //appended to first characters of note

    private EditText txtEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get intent to check if opening past note
        if(getIntent().getExtras() != null) { //opening past note
            Intent intent = getIntent();
            String fileName = intent.getStringExtra("fileName");
            Toast.makeText(this, "Opening file: " + fileName, Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_brisk_note);
            txtEditor=(EditText)findViewById(R.id.textbox);
            readFileInEditor(fileName);
        }
        else { //opening view
            Toast.makeText(this, "BriskNote starting...", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_brisk_note);
            txtEditor=(EditText)findViewById(R.id.textbox);
        }
    }

    public void saveClicked(View v) {

        try {

            //find the first word in the note to be used as the title
            String note = txtEditor.getText().toString();
            if(note.contains(" ")) {
                firstWord = note.substring(0, note.indexOf(" "));
            }
            else {
                if (note.length() < 5) {
                    firstWord = note;
                }
                else {
                    firstWord = note.substring(0, 4);
                }
            }

            //construct file name
            firstWord += ext;

            OutputStreamWriter out= new OutputStreamWriter(openFileOutput(firstWord, 0));

            out.write(note);

            out.close();

            Toast

                    .makeText(this, "The contents are saved in the file:" + firstWord, Toast.LENGTH_LONG)

                    .show();

        }

        catch (Throwable t) {
            Toast.makeText(this, "Exception: "+t.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void pastNotes(View v) {
        Toast.makeText(this, "Opening past notes...", Toast.LENGTH_LONG).show();

        //create an Intent to start a new activity
        Intent intent = new Intent(this, PastNotesList.class);

        startActivity(intent);
    }

    public void readFileInEditor(String fileName)

    {

        try {

            InputStream in = openFileInput(fileName);

            if (in != null) {

                InputStreamReader tmp=new InputStreamReader(in);

                BufferedReader reader=new BufferedReader(tmp);

                String str;

                StringBuilder buf=new StringBuilder();

                while ((str = reader.readLine()) != null) {

                    buf.append(str+"\n");

                }

                in.close();

                txtEditor.setText(buf.toString());

            }

        }

        catch (java.io.FileNotFoundException e) {}

        catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_brisk_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
