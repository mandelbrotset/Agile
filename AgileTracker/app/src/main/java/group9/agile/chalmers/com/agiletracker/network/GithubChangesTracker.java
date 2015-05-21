package group9.agile.chalmers.com.agiletracker.network;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;

import org.eclipse.egit.github.core.Commit;
import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryCommitCompare;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import group9.agile.chalmers.com.agiletracker.common.Resources;
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
        new ListRepositoriesTask().execute(Resources.BRANCH_SHA);//TODO: unuglyize this!
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(changesDisplayer);
        String repoOwner = prefs.getString(Resources.USER_REPO_OWNER, "");
        String repoName = prefs.getString(Resources.USER_REPO, "");
        if (repoName.isEmpty() || repoOwner.isEmpty()) return; //this makes no sense if they are not set
        android.util.Log.d("commits", "Checking for new commits...");
        try {
            List<Repository> repos = service.getRepositories(repoOwner);
            List<RepositoryCommit> commits = gsc.getCommits(RepositoryId.create(repoOwner, repoName));
            if (commits == null) {
                Log.d("commit", "service not started yet, cannot fetch commits..");
                return;
            }
            // Commit commit = commits.get(0).getCommit();//TODO: how many should we fetch?
            CommitService cs = new CommitService();
            gsc.getCommits(RepositoryId.create(repoOwner, repoName));

            RepositoryId repositoryId = RepositoryId.create(repoOwner, repoName);
            RepositoryCommit repositoryCommit = gsc.getCommits(repositoryId).get(0);
            Commit commit = repositoryCommit.getCommit();

            //Check differences between the selected branch (on settings) and master
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(changesDisplayer);
            String currentBranch = preferences.getString(Resources.BRANCH_NAME, "");
            if (!currentBranch.equals("") && !displayedCommits.contains(commit.getUrl())) {
                //Compare selected branch commits
                RepositoryCommitCompare commitCompare = cs.compare(repositoryId, "master", currentBranch);
                List<CommitFile> fileList = commitCompare.getFiles();
                List<CommitFile> branchFiles = gsc.getCommit(repositoryId, repositoryCommit.getSha()).getFiles();

                for (CommitFile branchFile : branchFiles) {
                    for (CommitFile file : fileList) {
                        if (branchFile.getFilename().equals(file.getFilename())) {
                            android.util.Log.d("commits", "displaying new commit");
                            displayCommit("New commit: " + commit.getMessage().toString());
                            displayedCommits.add(commit.getUrl());
                            return;
                        }
                    }
                }
            }
            }catch(IOException e){
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
