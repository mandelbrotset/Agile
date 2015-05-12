package group9.agile.chalmers.com.agiletracker.common.view;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import group9.agile.chalmers.com.agiletracker.R;

/**
 * Created by Malin and Alma on 05/05/15.
 */
public class CommitViewAdapter extends CursorAdapter {

    private static int SIZE = 18;
    private static int MARGIN = 10;

    /**
     * Constructor for CommitViewAdapter
     *
     * @param context Application context
     * @param c       Cursor for list of file names and their additions and deletions
     */
    public CommitViewAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.commit_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name = (TextView) view.findViewById(R.id.tvPriorityTitle);
        name.setText(cursor.getString(1));

        TextView info = (TextView) view.findViewById(R.id.tvPriorityInfo);
        info.setText(cursor.getString(2));

        TextView date = (TextView) view.findViewById(R.id.tvPriorityDate);
        date.setText(cursor.getString(3));

       // text.setText(cursor.getString(1) + "\n" + cursor.getString(2) + " ++ / " + cursor.getString(3) + " --");
    }

    public void updateCursor(Cursor cursor) {
        changeCursor(cursor);
        notifyDataSetChanged();
    }
}
