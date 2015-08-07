package bluescreen1.poat.Tests;

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
public class TestMain extends Fragment {


    public static final String FILTER_KEY = "filter";
    private FragmentTabHost mTabHost;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static TestMain newInstance(int sectionNumber) {
        TestMain fragment = new TestMain();
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

        View rootview = inflater.inflate(R.layout.fragment_tabs, container, false);
        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.radatabcontent);
        Bundle all = new Bundle();
        all.putInt(FILTER_KEY, 0);
        mTabHost.addTab(mTabHost.newTabSpec("all").setIndicator("All"),
                TestsFragment.class, all);
        Bundle due = new Bundle();
        due.putInt(FILTER_KEY, 1);
        mTabHost.addTab(mTabHost.newTabSpec("upcoming").setIndicator("Upcoming"),
                TestsFragment.class, due);
        Bundle sub = new Bundle();
        sub.putInt(FILTER_KEY, 2);
        mTabHost.addTab(mTabHost.newTabSpec("past").setIndicator("Done"),
                TestsFragment.class, sub);
      //  setAlarm();

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



    public TestMain() {
        // Required empty public constructor
    }


//    public void setAlarm(){
//        String date = AssignmentEntry.COLUMN_DUE_DATE;
//        Toast.makeText(getActivity(),date,Toast.LENGTH_LONG);
//        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(getActivity().ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, date, PendingIntent.getBroadcast(this,1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT));
//        Toast.makeText(getActivity(),"AlarmReceiver set",Toast.LENGTH_LONG).show();
//
//    }


}
