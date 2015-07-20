package bluescreen1.poat.Assignments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bluescreen1.poat.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class AssignmentDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private FragmentTabHost mTabHost;

    public AssignmentDetailsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.radatabcontent);
        Bundle bund = new Bundle();
        bund.putString("hi", "works");
        mTabHost.addTab(mTabHost.newTabSpec("all").setIndicator("All"),
                AssignmentFragment.class,bund);
        mTabHost.addTab(mTabHost.newTabSpec("due").setIndicator("Due"),
                AssignmentFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("submit").setIndicator("Submitted"),
                AssignmentFragment.class, null);


        return mTabHost;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            mTabHost.setup(getActivity(), getActivity()
                    .getSupportFragmentManager());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}