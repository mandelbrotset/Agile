package group9.agile.chalmers.com.agiletracker.network;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.CommitService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import group9.agile.chalmers.com.agiletracker.common.Resources;
import group9.agile.chalmers.com.agiletracker.MainActivity;
import group9.agile.chalmers.com.agiletracker.common.view.CommitViewAdapter;
import group9.agile.chalmers.com.agiletracker.common.view.FilesViewAdapter;
import group9.agile.chalmers.com.agiletracker.exceptions.TaskNotCreatedException;

/**
 * Created by Malin and Alma on 12/05/2015.
 */
public class CommitFilesTask extends AsyncTask<String, Void, List<CommitFile>> {

    private FilesViewAdapter adapter;

    private Activity parent;
    private GithubServiceConnection gsc;

    private static CommitFilesTask singletonTask=null;

    public CommitFilesTask(FilesViewAdapter adapter, Activity parent) {
        this.adapter = adapter;
        this.parent = parent;
        gsc = ((MainActivity)parent).getGithubServiceConnection();
    }

    public static CommitFilesTask getTask () throws TaskNotCreatedException {
        if(singletonTask==null){
            throw new TaskNotCreatedException();
        }
        return singletonTask;
    }

    @Override
    protected List<CommitFile> doInBackground(String... params) {
        String sha = params[0];
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(parent);
        String repoOwner = prefs.getString(Resources.USER_REPO_OWNER, "");
        String repoName = prefs.getString(Resources.USER_REPO, "");
        IRepositoryIdProvider repositoryId = RepositoryId.create(repoOwner, repoName);
        List<CommitFile> commitFiles = new ArrayList<CommitFile>();

        RepositoryCommit commit = gsc.getCommit(repositoryId, sha);
        if (commit == null) {
            Log.d("commit", "service not started yet, cannot fetch commits..");
            return commitFiles;
        }
        commitFiles = commit.getFiles();
        return commitFiles;
    }

    protected void onPostExecute(List<CommitFile> commitFiles) {

        String[] columnNames = {"_id", Resources.FILENAME, Resources.ADDITIONS, Resources.DELETIONS};
        MatrixCursor matrixCursor = new MatrixCursor(columnNames);
        long id = 0;
        for (CommitFile file : commitFiles) {
            String fileName = file.getFilename();
            matrixCursor.addRow(new Object[]{id, fileName, file.getAdditions(), file.getDeletions()});
            id++;
        }
        adapter.updateCursor(matrixCursor);
    }
}