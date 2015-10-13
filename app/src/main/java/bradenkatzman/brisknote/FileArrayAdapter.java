package bradenkatzman.brisknote;

import android.widget.ArrayAdapter;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Braden Katzman on 10/13/15.
 */
public class FileArrayAdapter extends ArrayAdapter<Option> {
    private Context context;
    private int id;
    private List<Option> items;

    public FileArrayAdapter(Context context, int textViewResourceId, List<Option> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.id = textViewResourceId;
        this.items = objects;
    }

    public Option getItem(int i) {
        return items.get(i);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(id, null);
        }
        final Option option = items.get(pos);

        if (option != null) {
            TextView t1 = (TextView) view.findViewById(R.id.TextView01);
            TextView t2 = (TextView) view.findViewById(R.id.TextView02);
            if(t1!=null) {
                t1.setText(option.getName());
            }
            if(t2!=null) {
                t2.setText(option.getData());
            }
        }

        return view;
    }
}
