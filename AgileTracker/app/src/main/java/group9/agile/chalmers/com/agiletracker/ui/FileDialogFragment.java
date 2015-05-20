package group9.agile.chalmers.com.agiletracker.ui;

import android.annotation.SuppressLint;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import group9.agile.chalmers.com.agiletracker.R;
import group9.agile.chalmers.com.agiletracker.common.Resources;
import group9.agile.chalmers.com.agiletracker.common.view.CommitViewAdapter;
import group9.agile.chalmers.com.agiletracker.common.view.FilesViewAdapter;
import group9.agile.chalmers.com.agiletracker.network.CommitFilesTask;
import group9.agile.chalmers.com.agiletracker.network.CommitListTask;

/**
 * Created by malinanker on 19/05/15.
 */

public class FileDialogFragment extends DialogFragment { //implements OnItemClickListener {

    ListView fileList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_fragment, null, false);
        fileList = (ListView) view.findViewById(R.id.dialogList);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        MatrixCursor cursor = new MatrixCursor(new String[]{"_id", Resources.FILENAME, Resources.ADDITIONS, Resources.DELETIONS});

        FilesViewAdapter adapter = new FilesViewAdapter(getActivity(), cursor);
        fileList.setAdapter(adapter);
        CommitFilesTask task = new CommitFilesTask(adapter, getActivity());

        task.execute(this.getArguments().getString("sha"));

        //This will trigger the onItemClick below :D
        //fileList.setOnItemClickListener(this);

        return view;
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        dismiss();
//        Toast.makeText(getActivity(), fileList[position], Toast.LENGTH_SHORT).show();
//    }


}

