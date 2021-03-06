package group9.agile.chalmers.com.agiletracker.common.view;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import group9.agile.chalmers.com.agiletracker.R;

/**
 * Created by Malin and Alma on 12/05/15.
 */
public class FilesViewAdapter extends CursorAdapter {

    /**
     * Constructor for CommitViewAdapter
     *
     * @param context Application context
     * @param c       Cursor for list of file names and their additions and deletions
     */
    public FilesViewAdapter (Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.commit_files_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView text = (TextView) view.findViewById(R.id.tvPriority);
        text.setText(cursor.getString(1) + "\n" + cursor.getString(2) + " ++ / " + cursor.getString(3) + " --");
    }

    public void updateCursor(Cursor cursor) {
        changeCursor(cursor);
        notifyDataSetChanged();
    }
}
