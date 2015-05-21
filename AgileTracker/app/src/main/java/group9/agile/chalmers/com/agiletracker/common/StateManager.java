package group9.agile.chalmers.com.agiletracker.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

/**
 * @author Mikael Friedrici
 */
@Singleton
public class StateManager implements Serializable{
    private static StateManager instance = null;
    private static List<String> branchList = new ArrayList<>();
    private String userName = "";
    private String password = "";
    private String currentRepoName = "";

    public static StateManager getInstance() {
        if(instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if(userName != null)
            this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password != null)
            this.password = password;
    }

    public String getCurrentRepoName() {
        return currentRepoName;
    }

    public void setCurrentRepoName(String currentRepoName) {
        if(currentRepoName != null)
            this.currentRepoName = currentRepoName;
    }

    public static List<String> getBranchList() {
        return branchList;
    }

    public static void setBranchList(List<String> branchList) {
        StateManager.branchList = branchList;
    }
}