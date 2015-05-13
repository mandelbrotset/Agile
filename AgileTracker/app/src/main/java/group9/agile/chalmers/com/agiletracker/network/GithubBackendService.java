package group9.agile.chalmers.com.agiletracker.network;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by isak & Sarah on 5/12/15.
 */
public class GithubBackendService extends Service {

    private GithubChangesTracker githubChangesTracker;
    private Activity displayActivity;
    private boolean isStarted = false;
    private GitHubClient client;
    private CommitService commitService;
    private GithubBackendBinder binder;
    private static final int GITHUB_CHANGES_TRACKER_STARTUP_DELAY = 10000;

    public class GithubBackendBinder extends Binder {
        /**
         * Sets which activity the notifications should link to.
         * @param targetActivity
         */
        public void setNotificationTargetActivity(Activity targetActivity) {
            Log.d("commit", "targetActivity set");
            displayActivity = targetActivity;
        }

        /**
         * Start the gitHubChangesThread, which starts to fetch commits and displays notifications.
         */
        public void startGithubChangesTracker() {
            if (githubChangesTracker != null) {
                Log.d("commit","githubchangestracker already started");
            }
            Log.d("commit","creates new githubchangestracker");
            githubChangesTracker = new GithubChangesTracker(displayActivity);
            githubChangesTracker.start();
        }

        /**
         * Gets a list of commits in a specific repository.
         * @param repository
         * @return
         */
        public List<RepositoryCommit> getCommits(IRepositoryIdProvider repository) {
            if (commitService == null) {
                Log.d("commit", "cannon fetch commits, commit service is null");
                return null;
            }
            try {
                List<RepositoryCommit> commits = commitService.getCommits(repository);
                Log.d("commit", "commits fetched succesfully");
                return commits;
            } catch (IOException e) {
                Log.d("commit", e.getMessage());
                return null;
            }
        }

        /**
         * Calls getCommit on the commitService
         * @param repository
         * @param sha
         * @return the commit, null if failed
         */
        public RepositoryCommit getCommit(IRepositoryIdProvider repository, String sha) {
            if (commitService == null) return null;
            try {
                RepositoryCommit commit = commitService.getCommit(repository, sha);
                return commit;
            } catch (IOException e) {
                return null;
            }
        }

        /**
         * Sets the credentials in the GitHub client.
         * @param username
         * @param password
         */
        public void login(String username, String password) {
            client.setCredentials(username, password);
            if (commitService != null) return;
            commitService = new CommitService(client);
        }
    }

    public GithubBackendService() {
        client = new GitHubClient();
        binder = new GithubBackendBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isStarted) return START_STICKY;
        isStarted = true;
        Timer t = new Timer();
        t.schedule(new startGitHubChangesTracker(), GITHUB_CHANGES_TRACKER_STARTUP_DELAY);
        return super.onStartCommand(intent, flags, startId);
    }

    public class startGitHubChangesTracker extends TimerTask {
        @Override
        public void run() {
            binder.login("","");//TODO: read from settings here!
            binder.startGithubChangesTracker();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        githubChangesTracker.stopThread();
    }
}
