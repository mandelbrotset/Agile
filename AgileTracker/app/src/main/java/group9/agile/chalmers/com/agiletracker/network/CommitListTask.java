package group9.agile.chalmers.com.agiletracker.network;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

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
import group9.agile.chalmers.com.agiletracker.common.view.CommitViewAdapter;
import group9.agile.chalmers.com.agiletracker.exceptions.TaskNotCreatedException;

/**
 * Created by Malin and Alma on 24/04/2015.
 */
public class CommitListTask extends AsyncTask<String, Void, List<RepositoryCommit>> {

    private CommitViewAdapter adapter;

    private static CommitListTask singletonTask=null;
    private Activity activity;

    public CommitListTask(CommitViewAdapter adapter, Activity activity) {
        this.adapter = adapter;
        this.activity = activity;
    }

    public static CommitListTask getTask () throws TaskNotCreatedException {
        if(singletonTask==null){
            throw new TaskNotCreatedException();
        }
        return singletonTask;
    }

    @Override
    protected List<RepositoryCommit> doInBackground(String... params) {

        CommitService commitService = new CommitService();
        String sha = params[0];
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        String repoOwner = prefs.getString(Resources.USER_REPO_OWNER, "");
        String repoName = prefs.getString(Resources.USER_REPO, "");
        if (repoOwner.isEmpty() || repoName.isEmpty()) return null;
        IRepositoryIdProvider repositoryId = RepositoryId.create(repoOwner, repoName); //Hardcoded now, will get from the savedInstance
        List<RepositoryCommit> commitFiles = new ArrayList<RepositoryCommit>();
        try {
            commitFiles = commitService.getCommits(repositoryId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return commitFiles;
    }

    protected void onPostExecute(List<RepositoryCommit> commitList) {
        if (commitList == null) return;
        String[] columnNames = {"_id", Resources.COMMIT_MESSAGE, Resources.COMMIT_AUTHOR, Resources.COMMIT_DATE, Resources.COMMIT_SHA};
        MatrixCursor matrixCursor = new MatrixCursor(columnNames);
        long id = 0;
        for (RepositoryCommit commit : commitList) {
            String commitMessage = commit.getCommit().getMessage();
            String author = commit.getAuthor().getLogin();

            Date date = commit.getCommit().getAuthor().getDate();
            SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM");

            matrixCursor.addRow(new Object[]{id, commitMessage, author, dt1.format(date), commit.getSha()});
           // matrixCursor.addRow(new Object[]{id, fileName, file.getAdditions(), file.getDeletions()});
            id++;
        }
        adapter.updateCursor(matrixCursor);
    }
}