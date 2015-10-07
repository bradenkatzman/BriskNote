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

    private final static String STORETEXT="BriskNote.txt";

    private String firstWord;

    private final static String txt = ".txt"; //appended to first characters of note

    private EditText txtEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_brisk_note);

        txtEditor=(EditText)findViewById(R.id.textbox);

        readFileInEditor();
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

            Toast.makeText(this, note, Toast.LENGTH_LONG).show();


            //construct file name
            firstWord += txt;

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
        //create an Intent to start a new activity
        Intent intent = new Intent(this, PastNotesList.class);

        startActivity(intent);
    }

    public void readFileInEditor()

    {

        try {

            InputStream in = openFileInput("hello.txt");

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

        catch (java.io.FileNotFoundException e) {

// that's OK, we probably haven't created it yet

        }

        catch (Throwable t) {

            Toast

                    .makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG)

                    .show();

        }

    }
}
