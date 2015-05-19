package group9.agile.chalmers.com.agiletracker.network;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryCommit;

import java.util.List;

/**
 * Created by isak & Sarah on 5/12/15.
 */
public class GithubServiceConnection implements ServiceConnection {
    private Activity targetActivity;
    private GithubBackendService.GithubBackendBinder binder;
    private boolean isConnected = false;

    public GithubServiceConnection(Activity targetActivity) {
        this.targetActivity = targetActivity;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        GithubBackendService.GithubBackendBinder binder = (GithubBackendService.GithubBackendBinder) service;
        this.binder = binder;
        binder.setNotificationTargetActivity(targetActivity);
        isConnected = true;
    }

    public void startGithubChangesTracker() {
        if (isConnected) {
            binder.startGithubChangesTracker();
        } else {
            Log.d("commit", "cannot start GithubChangesTracker, not connected yet..");
        }

    }

    public void login(String username, String password) {
        if (isConnected) {
            binder.login(username, password);
        } else {
            Log.d("commit", "cannot login, not connected yet..");
        }
    }

    public List<RepositoryCommit> getCommits(IRepositoryIdProvider repository) {
        if (isConnected) return binder.getCommits(repository);
        return null;
    }

    public RepositoryCommit getCommit(IRepositoryIdProvider repository, String sha) {
        if (isConnected) return binder.getCommit(repository, sha);
        return null;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
