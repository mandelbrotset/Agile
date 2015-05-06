package group9.agile.chalmers.com.agiletracker.network;

import android.app.Activity;
import android.database.MatrixCursor;
import android.os.AsyncTask;

import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.CommitService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import group9.agile.chalmers.com.agiletracker.common.view.CommitViewAdapter;

/**
 * Created by Malin and Alma on 24/04/2015.
 */
public class CommitFilesTask extends AsyncTask<String, Void, List<CommitFile>> {

    private CommitViewAdapter adapter;
    private Activity parent;

    public CommitFilesTask(CommitViewAdapter adapter, Activity parent) {
        this.adapter = adapter;
        this.parent = parent;
    }

    @Override
    protected List<CommitFile> doInBackground(String... params) {

        CommitService commitService = new CommitService();
        String sha = params[0];

        IRepositoryIdProvider repositoryId = RepositoryId.create("mandelbrotset", "Agile"); //Hardcoded now, will get from the savedInstance
        List<CommitFile> commitFiles = new ArrayList<CommitFile>();
        try {
            commitFiles = commitService.getCommit(repositoryId, sha).getFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return commitFiles;
    }

    protected void onPostExecute(List<CommitFile> commitFiles) {

        String[] columnNames = {"_id", "FileName", "Additions", "Deletions"};
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
