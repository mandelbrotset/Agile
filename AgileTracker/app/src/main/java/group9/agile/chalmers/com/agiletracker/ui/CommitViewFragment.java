package group9.agile.chalmers.com.agiletracker.ui;

import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import group9.agile.chalmers.com.agiletracker.R;
import group9.agile.chalmers.com.agiletracker.common.view.CommitViewAdapter;
import group9.agile.chalmers.com.agiletracker.network.CommitFilesTask;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CommitViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CommitViewFragment extends Fragment {

    private static final String FILENAME = "FileName";
    private static final String ADDITIONS = "Additions";
    private static final String DELETIONS = "Deletions";
    private OnFragmentInteractionListener mListener;

    // Required empty public constructor
    public CommitViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String sha = "cca5db430fcc6486765f5d9d85b0e5d1a2026215"; //Hardcoded now, will get from the savedInstance

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_commit_view, container, false);
        ListView list = (ListView) view.findViewById(R.id.tvBody);
        MatrixCursor cursor = new MatrixCursor(new String[]{"_id", FILENAME, ADDITIONS, DELETIONS});
        CommitViewAdapter adapter = new CommitViewAdapter(getActivity(), cursor);
        list.setAdapter(adapter);
        CommitFilesTask task = new CommitFilesTask(adapter, getActivity());
        task.execute(sha);

        return view;
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
