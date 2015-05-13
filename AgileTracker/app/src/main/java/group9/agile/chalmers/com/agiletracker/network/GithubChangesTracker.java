package group9.agile.chalmers.com.agiletracker.network;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;

import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import group9.agile.chalmers.com.agiletracker.MainActivity;
import group9.agile.chalmers.com.agiletracker.common.notification.Notificator;

/**
 * Created by isak & Sarah on 4/28/15.
 */
public class GithubChangesTracker extends Thread {
//c3ce0e09bd107561527d7f104aebcee12367f2d3    --OAUTH key

    // Set in millis
    private static final int UPDATE_INTERVAL = 5*60*1000;

    private RepositoryService service;

    private Activity changesDisplayer;
    private HashSet<String> displayedCommits;
    private boolean isRunning = false;
    private GithubServiceConnection gsc;

    public GithubChangesTracker(Activity changesDisplayer) {
        displayedCommits = new HashSet<String>();
        service = new RepositoryService();
        this.changesDisplayer = changesDisplayer;
        gsc = ((MainActivity)changesDisplayer).getGithubServiceConnection();
    }

    private void checkChanges() {
        android.util.Log.d("commits", "Checking for new commits...");
        try {
            List<Repository> repos=service.getRepositories("mandelbrotset");//TODO: Read from settings
            for (Repository repo : repos) {
                List<RepositoryCommit> commits = gsc.getCommits(RepositoryId.create("mandelbrotset", repo.getName()));
                if (commits == null) {
                    Log.d("commit", "service not started yet, cannot fetch commits..");
                    return;
                }
                Commit commit = commits.get(0).getCommit();//TODO: how many should we fetch?
                CommitService cs = new CommitService();
                cs.getCommits(RepositoryId.create("mandelbrotset", "Agile"));//TODO: Read from settings

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

    public void stopThread() {
        isRunning = false;
    }

    @Override
    public void run() {
        android.util.Log.d("commit", "starting githubchangestracker..");
        isRunning = true;
        while (!this.isInterrupted() && isRunning) {
            try {
                checkChanges();
                Thread.sleep(UPDATE_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
