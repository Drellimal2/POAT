package bluescreen1.poat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bluescreen1.poat.Assignments.AssignmentFragment;
import bluescreen1.poat.Tests.TestsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeMain extends Fragment {


    public TimeMain() {
        // Required empty public constructor
    }


    public static final String FILTER_KEY = "filter";
    private FragmentTabHost mTabHost;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static TimeMain newInstance(int options) {
        TimeMain fragment = new TimeMain();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, options);
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

        Bundle filter = getArguments();
        int Option = filter.getInt(ARG_SECTION_NUMBER);
        View rootview = inflater.inflate(R.layout.fragment_tabs, container, false);
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.radatabcontent);
        Bundle all = new Bundle();
        all.putInt(FILTER_KEY, Option);
        mTabHost.addTab(mTabHost.newTabSpec("assignment").setIndicator("Assignments"),
                AssignmentFragment.class, all);
        Bundle due = new Bundle();
        due.putInt(FILTER_KEY, 1);
        mTabHost.addTab(mTabHost.newTabSpec("test").setIndicator("Tests"),
                TestsFragment.class, due);

        //  setAlarm();

        return mTabHost;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTabHost.setup(getActivity(), getActivity().getSupportFragmentManager());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTabHost = null;
    }


}
