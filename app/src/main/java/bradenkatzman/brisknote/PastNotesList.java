package bradenkatzman.brisknote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import android.content.Intent;

public class PastNotesList extends AppCompatActivity {

    private File currentDirectory;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_notes_list);

        //get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        //get the directory that stores the .xt files
        currentDirectory = this.getFilesDir();
        fill(currentDirectory);
    }

        private void fill(File f) {
            //make array of file names to list
            File[] file = f.listFiles();
            String[] files = new String[file.length];
            for (int i = 0; i < file.length; ++i) {
                files[i] = file[i].getName();
            }

            //define a new adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, files);

            //assignAdapter to ListView
            listView.setAdapter(adapter);

            //set ListView item click listener
            setClickListener();

        }

    private void setClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //clicked item index
                //int filePosition = position;

                //click item value
                String fileName = (String) listView.getItemAtPosition(position);

                //open note
                openFile(fileName);
            }
        });
    }

    private void openFile(String fileName) {
        //debug
        Toast.makeText(getApplicationContext(),
                "Opening file: " + fileName, Toast.LENGTH_LONG).show();

        //create an Intent to start a new activity
        Intent intent = new Intent(this, BriskNote.class);

        //assign file name to intent
        intent.putExtra("fileName", fileName);

        startActivity(intent);

    }



//    //create an Intent to start a new activity
//    Intent intent = new Intent(this, PastNotesList.class);
//
//    startActivity(intent);

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
