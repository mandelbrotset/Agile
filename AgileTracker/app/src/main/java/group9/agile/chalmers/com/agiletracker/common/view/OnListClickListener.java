package group9.agile.chalmers.com.agiletracker.common.view;

import android.app.Activity;
import android.content.Context;
import android.database.MatrixCursor;
import android.view.View;
import android.widget.ListView;

import group9.agile.chalmers.com.agiletracker.common.Resources;
import group9.agile.chalmers.com.agiletracker.network.CommitFilesTask;
import group9.agile.chalmers.com.agiletracker.network.CommitListTask;

/**
 * Created by Alma on 12/05/2015.
 */
public class OnListClickListener implements View.OnClickListener {
    private String sha;
    private ListView list;
    private boolean visible=false;
    private boolean populated=false;
    private Context context;

    public OnListClickListener(String sha, ListView list, Context context){
        this.sha=sha;
        this.list=list;
        this.context=context;
    }
    @Override
    public void onClick(View v) {
        if(visible){
            visible=false;
            list.setVisibility(View.GONE);
        } else {
            visible=true;
            list.setVisibility(View.VISIBLE);
            if(!populated){
                MatrixCursor cursor = new MatrixCursor(new String[]{"_id", Resources.FILENAME, Resources.ADDITIONS, Resources.DELETIONS});
                FilesViewAdapter adapter = new FilesViewAdapter(context, cursor);
                list.setAdapter(adapter);
                CommitFilesTask task = new CommitFilesTask(adapter);
                task.execute(sha);
            }
        }
    }
}
