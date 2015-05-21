package group9.agile.chalmers.com.agiletracker.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import group9.agile.chalmers.com.agiletracker.MainActivity;
import group9.agile.chalmers.com.agiletracker.R;
import group9.agile.chalmers.com.agiletracker.common.Resources;
import group9.agile.chalmers.com.agiletracker.common.StateManager;
import group9.agile.chalmers.com.agiletracker.common.view.CommitViewAdapter;
import group9.agile.chalmers.com.agiletracker.exceptions.TaskNotCreatedException;
import group9.agile.chalmers.com.agiletracker.network.CommitListTask;
import group9.agile.chalmers.com.agiletracker.network.ListRepositoriesTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CommitViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CommitViewFragment extends Fragment {
    private Spinner dropDownList;
    private MainActivity gui;
    //private static String SHA = "58dae94fbc9cc37fa1056b127297ab596ece4cd3";
    //@Inject
    //StateManager stateManager;
    StateManager stateManager = StateManager.getInstance();
    private static ArrayAdapter<String> dataAdapter;



    private OnFragmentInteractionListener mListener;

    // Required empty public constructor
    public CommitViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String sha = "30650ff0479fc71a04ff9692ad02e30402d84257"; //Hardcoded now, will get from the savedInstance

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_commit_view, container, false);
        ListView list = (ListView) view.findViewById(R.id.tvBody);

        MatrixCursor cursor = new MatrixCursor(new String[]{"_id", Resources.COMMIT_MESSAGE, Resources.COMMIT_AUTHOR, Resources.COMMIT_DATE, Resources.COMMIT_SHA});
        CommitViewAdapter adapter = new CommitViewAdapter(getActivity(), cursor);
        list.setAdapter(adapter);

        CommitListTask task = new CommitListTask(adapter, getActivity());
        task.execute(sha);
        setupSpinner(view);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> viewAdapter, View clickedListItem, int index, long dunno) {

                FragmentManager manager = getFragmentManager();
                MatrixCursor c = (MatrixCursor) viewAdapter.getItemAtPosition(index);
                String sha = c.getString(4);


                FileDialogFragment dialog = new FileDialogFragment();

                Bundle args = new Bundle();
                args.putString("sha", sha);

                dialog.setArguments(args);

                dialog.show(manager, "dialog");
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        gui = (MainActivity) activity;
    }

    /**
     * Setup the drop down list(spinner) that displays all the branches
     * @param view
     */
    private void setupSpinner(View view){

        dropDownList = (Spinner) view.findViewById(R.id.spinner);
        dataAdapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_spinner_item, stateManager.getBranchList());

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownList.setAdapter(dataAdapter);
        new ListRepositoriesTask().execute(Resources.BRANCH_SHA);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String branchName = preferences.getString(Resources.BRANCH_NAME, "");
        if(!branchName.equals("")){
            for (int i = 0 ; i<dropDownList.getCount() ; i++){
                if (dropDownList.getItemAtPosition(i).equals(branchName)){
                    dropDownList.setSelection(i);
                }
            }
        }

        dropDownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // Is called each time the current item is changed
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new ListRepositoriesTask().execute(Resources.BRANCH_SHA); //?


                //Store the branch name in the preferences
                TextView textView = (TextView) view;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Resources.BRANCH_NAME, textView.getText().toString());
                editor.commit();

                //Update the view
                try {
                    Log.d("branch_name", Resources.BRANCH_NAME);

                    CommitListTask.getTask().execute();


                } catch (TaskNotCreatedException e) {
                    e.printStackTrace();
                    //See if we need to throw it further
                }
            }


            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    /**
     * Update the spinner with the branch list.
     */
    public static void updateBranchList(){
        dataAdapter.notifyDataSetChanged();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}