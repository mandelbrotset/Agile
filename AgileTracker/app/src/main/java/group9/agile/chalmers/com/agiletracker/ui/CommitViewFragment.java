package group9.agile.chalmers.com.agiletracker.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.CommitService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import group9.agile.chalmers.com.agiletracker.R;
import group9.agile.chalmers.com.agiletracker.network.CommitFilesTask;
import group9.agile.chalmers.com.agiletracker.network.GitHub;
import group9.agile.chalmers.com.agiletracker.network.ListRepositoriesTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CommitViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CommitViewFragment extends Fragment {
    private Spinner dropDownList;
    private static String SHA = "58dae94fbc9cc37fa1056b127297ab596ece4cd3";
    private static List<String> spinnerList = new ArrayList<>();
    private static ArrayAdapter<String> dataAdapter;

    private OnFragmentInteractionListener mListener;

    public CommitViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_commit_view, container, false);

        setupSpinner(view);

        return view;
    }

    /**
     * Setup spinner (the drop down list)
     * @param view
     */
    private void setupSpinner(View view){
        dropDownList = (Spinner) view.findViewById(R.id.spinner);
        dataAdapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_spinner_item, spinnerList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDownList.setAdapter(dataAdapter);
        new ListRepositoriesTask().execute(SHA);

        dropDownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                new ListRepositoriesTask().execute(SHA);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    /**
     * Update the spinner with the branch list.
     */
    public static void updateBranchList(ArrayList<String> branches){
        if(branches == null) {
            Log.e("branch","GUI: error branchlist is null");
            return;
        }

        spinnerList.clear();
        spinnerList.addAll(branches);
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