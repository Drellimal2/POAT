package bluescreen1.poat.Assignments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bluescreen1.poat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssignmentMain extends Fragment {


    private FragmentTabHost mTabHost;
    private static final String ARG_SECTION_NUMBER = "section_number";


    public static AssignmentMain newInstance(int sectionNumber) {
        AssignmentMain fragment = new AssignmentMain();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
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
        Bundle all = new Bundle();
        all.putString("hi", "works");
        mTabHost.addTab(mTabHost.newTabSpec("all").setIndicator("All"),
                AssignmentFragment.class,all);
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



    public AssignmentMain() {
        // Required empty public constructor
    }





}
