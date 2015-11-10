package bradenkatzman.brisknote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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

    //this detects double tap for sharing purposes
    GestureDetection gD;

    private static View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
            CREATE GESTURE DETECTION
         */
        //save current view
        rootView = this.findViewById(android.R.id.content).getRootView();

        //set listener for taps, implement on touch method that accesses gesture detection class
        gD = new GestureDetection(rootView.getContext(),
                (AttributeSet) rootView.getLayoutParams());

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("tap detected - ","sending to gesture detection");

                boolean result = gD.onTouchEvent(event); //fire the gesture detection method

                if (result == true) {
                    //gesture detected
                    sendText(rootView);
                }

                return result;
            }
        });

        /* END GESTURE DETECTION SEGMENT */


        //get intent to check if opening past note or deep link
        if(getIntent().getExtras() != null) { //opening note
            Intent intent = getIntent();
            String fileName;

            //let's check if this is coming from a deep link
            String action = intent.getAction();
            String data = intent.getDataString();
            if(Intent.ACTION_VIEW.equals(action) && data != null) { //a deep link
                Toast.makeText(this, "processing deep link...", Toast.LENGTH_SHORT).show();
                //extract file name
                fileName = data.substring(data.lastIndexOf("/") + 1);
                if (fileName.equals(".txt")) { //opening the app for a new note
                    initializeNewNote();
                }
                else {
                    initializeWithFile(fileName);
                }
            }
            else  { //opening note from Past Notes view
                Toast.makeText(this, "Opening file from Past Notes View", Toast.LENGTH_SHORT).show();
                fileName = intent.getStringExtra("fileName");
                initializeWithFile(fileName);
            }

        }
        else { //opening view
            initializeNewNote();
        }
    }

    public void initializeNewNote() {
        Toast.makeText(this, "BriskNote starting...", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_brisk_note);
        txtEditor=(EditText)findViewById(R.id.textbox);
    }

    public void initializeWithFile(String fileName) {
        Toast.makeText(this, "Opening file: " + fileName, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_brisk_note);
        txtEditor=(EditText)findViewById(R.id.textbox);
        readFileInEditor(fileName);
    }

    //saves the note in the text editor, writes to output files
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

            Toast.makeText(this, "The contents are saved in the file:" + firstWord, Toast.LENGTH_SHORT).show();
            Log.d("saved file: ", firstWord);

        }
        catch (Throwable t) {
            Toast.makeText(this, "Exception: "+t.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    //opens past notes view to select .txt files
    public void pastNotes(View v) {
        Toast.makeText(this, "Opening past notes...", Toast.LENGTH_SHORT).show();

        //create an Intent to start a new activity
        Intent intent = new Intent(this, PastNotesList.class);

        startActivity(intent);
    }


    //send note data to other app
    public void sendText(View v) {
        String note = txtEditor.getText().toString();
        sendNote(note);
    }

    private void sendNote(String note) {
        Toast.makeText(this, "sending text to other apps...", Toast.LENGTH_SHORT).show();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND); //will identify compatible receiving activities
        sendIntent.putExtra("data", note);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Send Note To Other App:"));
    }


    //this method reads files that are passed as intents from the past notes list
    public void readFileInEditor(String fileName) {
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
        catch (java.io.FileNotFoundException e) {
            Toast.makeText(this, "Exception: the file was not found on the system", Toast.LENGTH_SHORT).show();
        }
        catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_SHORT).show();
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