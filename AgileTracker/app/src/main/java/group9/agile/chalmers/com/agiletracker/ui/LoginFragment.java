package group9.agile.chalmers.com.agiletracker.ui;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import group9.agile.chalmers.com.agiletracker.MainActivity;
import group9.agile.chalmers.com.agiletracker.R;
import group9.agile.chalmers.com.agiletracker.common.Resources;
import group9.agile.chalmers.com.agiletracker.network.GithubServiceConnection;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CommitViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class LoginFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    // Required empty public constructor
    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String sha = "30650ff0479fc71a04ff9692ad02e30402d84257"; //Hardcoded now, will get from the savedInstance

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button acceptButton=(Button)view.findViewById(R.id.button_accept);
        final String userName=((EditText)view.findViewById(R.id.input_username)).getText().toString();
        final String password=((EditText)view.findViewById(R.id.input_password)).getText().toString();
        final String repoName=((EditText)view.findViewById(R.id.input_reponame)).getText().toString();
        acceptButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(Resources.LOGGED_IN, true);
                editor.putString(Resources.USER_NAME, userName);
                editor.putString(Resources.USER_PASSWORD, password);
                editor.putString(Resources.USER_REPO, repoName);
                editor.commit();
                GithubServiceConnection gsc = ((MainActivity)getActivity()).getGithubServiceConnection();
                gsc.login(userName, password);
            }
        });

        //Put a listener on the accept button
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