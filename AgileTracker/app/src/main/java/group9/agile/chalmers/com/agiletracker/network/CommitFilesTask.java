package group9.agile.chalmers.com.agiletracker.network;

import android.os.AsyncTask;
import android.util.Log;

import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.CommitService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malin and Alma on 24/04/2015.
 */
public class CommitFilesTask extends AsyncTask<String, Void, List<CommitFile>> {


    @Override
    protected List<CommitFile> doInBackground(String... params) {
        CommitService commitService = new CommitService();
        String sha=params[0];

        IRepositoryIdProvider repositoryId= RepositoryId.create("mandelbrotset", "Agile"); //Hardcoded now, will get from the savedInstance
        List<CommitFile> commitFiles=new ArrayList<CommitFile>();
        try {
            commitFiles=commitService.getCommit(repositoryId, sha).getFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commitFiles;
    }

    protected void onPostExecute(List<CommitFile> commitFiles) {
        for(CommitFile file: commitFiles){
            System.out.println("FILE NAME: "+file.getFilename());
            Log.e("repo", file.getFilename());
        }
    }
}
