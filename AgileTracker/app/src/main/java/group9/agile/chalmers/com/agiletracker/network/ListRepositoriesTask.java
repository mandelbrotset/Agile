package group9.agile.chalmers.com.agiletracker.network;

import android.os.AsyncTask;
import android.util.Log;
import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import group9.agile.chalmers.com.agiletracker.common.StateManager;
import group9.agile.chalmers.com.agiletracker.ui.CommitViewFragment;

/**
 * Created by Mikael Friederici on 05/05/15.
 */
public class ListRepositoriesTask extends AsyncTask<String, Void, List<RepositoryBranch>> {
    //@Inject
    //StateManager stateManager;
    StateManager stateManager = StateManager.getInstance();

    @Override
    protected List<RepositoryBranch> doInBackground(String... params) {
        try {
            String sha=params[0];
            RepositoryService service = new RepositoryService();
            IRepositoryIdProvider repositoryId= RepositoryId.create("mandelbrotset", "Agile");
            return service.getBranches(repositoryId);
        } catch (IOException e) {
            Log.e("network", "Network problems");
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(List<RepositoryBranch> branches) {
        if(branches == null)
            return;

        ArrayList<String> branchNames = new ArrayList<>();
        for(RepositoryBranch branch: branches){
            branchNames.add(branch.getName());
            Log.v("adding branch",branch.getName());
        }
        stateManager.setBranchList(branchNames);
        CommitViewFragment.updateBranchList();
    }
}