package bradenkatzman.brisknote;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PastNotesList extends ListActivity { //AppCompatActivity

    private File currentDirectory;
    private FileArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_notes_list);

        //get the directory that stores the .xt files
        currentDirectory = this.getFilesDir();
        fill(currentDirectory);
    }

        private void fill(File f) {
//            //make array of file names to list
//            String files = "";
//            File file[] = f.listFiles();
//            Log.d("Files", "Size: " + file.length);
//            for (int i = 0; i < file.length; ++i) {
//                files += file[i].getName();
//                files += ", ";
//            }
//            Toast.makeText(this, files, Toast.LENGTH_LONG).show();

            File[] files = f.listFiles();
            List<Option> fls = new ArrayList<Option>();
            try {
                for (File ff: files) {
                    fls.add(new Option(ff.getName(), "File Size: " + ff.length(), ff.getAbsolutePath()));
                }
            }
            catch (Exception e) {

            }

            Collections.sort(fls); //sort the files in the ArrayList

            //create file adapter for collection
            adapter = new FileArrayAdapter(PastNotesList.this, R.layout.activity_past_notes_list, fls);
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
