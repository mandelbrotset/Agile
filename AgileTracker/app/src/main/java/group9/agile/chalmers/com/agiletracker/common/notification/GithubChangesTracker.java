package group9.agile.chalmers.com.agiletracker.common.notification;

import android.app.Activity;
import android.graphics.Color;

import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

/**
 * Created by isak & Sarah on 4/28/15.
 */
public class GithubChangesTracker extends Thread {
//c3ce0e09bd107561527d7f104aebcee12367f2d3    --OAUTH key

    // Set in millis
    private static final int UPDATE_INTERVAL = 5*60*1000;
    private GitHubClient client;
    private RepositoryService service;
    private CommitService commitService;
    private Activity changesDisplayer;
    private HashSet<String> displayedCommits;

    public GithubChangesTracker(Activity changesDisplayer) {
        displayedCommits = new HashSet<String>();
        android.util.Log.d("commits", "Starting GitHub changes tracker");
        client = new GitHubClient();
        authenticate();
        commitService = new CommitService(client);
        service = new RepositoryService();
        this.changesDisplayer = changesDisplayer;
    }

    private void authenticate() {
        client.setCredentials("", ""); //insert username and password here!!
    }

    private void checkChanges() {
        android.util.Log.d("commits", "Checking for new commits...");
        try {
            List<Repository> repos=service.getRepositories("mandelbrotset");
            for (Repository repo : repos) {
                Commit commit=commitService.getCommits(RepositoryId.create("mandelbrotset", repo.getName())).get(0).getCommit();
                if (!displayedCommits.contains(commit.getUrl())) {
                    android.util.Log.d("commits", "displaying new commit");
                    displayCommit("Repo: " + repo.getName() + " - " + commit.getMessage().toString());
                    displayedCommits.add(commit.getUrl());
                }
             }
        } catch (IOException e) {
            android.util.Log.d("commits", e.getMessage());
            e.printStackTrace();
        }
    }

    protected void displayCommit(String text) {
        android.util.Log.d("commits", text);
        Notificator.displayNotification("New Commit", text, Color.RED, Color.BLUE, changesDisplayer);
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                Thread.sleep(UPDATE_INTERVAL);
                checkChanges();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
