package bradenkatzman.brisknote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import android.content.Intent;

public class PastNotesList extends AppCompatActivity { //AppCompatActivity

    private File currentDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_notes_list);

        //get the directory that stores the .xt files
        currentDirectory = this.getFilesDir();
        fill(currentDirectory);
    }

        private void fill(File f) {
            //make array of file names to list
            String files = "";
            File file[] = f.listFiles();
            for (int i = 0; i < file.length; ++i) {
                files += file[i].getName();
                files += ", ";
            }
            Toast.makeText(this, files, Toast.LENGTH_LONG).show();

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
