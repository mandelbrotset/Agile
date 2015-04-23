package group9.agile.chalmers.com.agiletracker.network;

import android.os.AsyncTask;

import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malin and Alma on 20/04/2015.
 */
public class GitHub extends AsyncTask<String, String, Integer> {
   /* protected Integer doInBackground(String... name) {
        publishProgress("HELLO");
        RepositoryService service = new RepositoryService();
        publishProgress("HELLO2");

        try {
            publishProgress(name[0]);
            List<Repository> repos=service.getRepositories(name[0]);
            publishProgress("Length: "+repos.size());
            for (Repository repo : repos) {
                publishProgress(repo.getName() + " Watchers: " + repo.getWatchers());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }*/

    protected Integer doInBackground(String... name) {
        RepositoryService service = new RepositoryService();
        try {
            CommitService commitService = new CommitService();
            List<Repository> repos=service.getRepositories(name[0]);
            int i=0;
            publishProgress("Trying");
            publishProgress("Length: "+repos.size());
            for (Repository repo : repos) {
                Commit commit=commitService.getCommits(RepositoryId.create(name[0], repo.getName())).get(i++).getCommit();
                publishProgress("Repo: " + repo.getName() + " Message: " + commit.getMessage().toString() + " Name: " + commit.getAuthor().getName()+" Number: "+(i-1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    protected void onPreExecute(){

    }

    protected void onProgressUpdate(String... progress) {
        System.out.println(progress[0]);
    }

    protected void onPostExecute(Integer result) {

    }
}