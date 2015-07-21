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


    public static final String FILTER_KEY = "filter";
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
        all.putInt(FILTER_KEY, 0);
        mTabHost.addTab(mTabHost.newTabSpec("all").setIndicator("All"),
                AssignmentFragment.class, all);
        Bundle due = new Bundle();
        due.putInt(FILTER_KEY, 1);
        mTabHost.addTab(mTabHost.newTabSpec("due").setIndicator("Due"),
                AssignmentFragment.class, due);
        Bundle sub = new Bundle();
        sub.putInt(FILTER_KEY, 2);
        mTabHost.addTab(mTabHost.newTabSpec("submit").setIndicator("Submitted"),
                AssignmentFragment.class, sub);
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



    public AssignmentMain() {
        // Required empty public constructor
    }


//    public void setAlarm(){
//        String date = AssignmentEntry.COLUMN_DUE_DATE;
//        Toast.makeText(getActivity(),date,Toast.LENGTH_LONG);
//        Intent alarmIntent = new Intent(this, Alarm.class);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(getActivity().ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, date, PendingIntent.getBroadcast(this,1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT));
//        Toast.makeText(getActivity(),"Alarm set",Toast.LENGTH_LONG).show();
//
//    }


}
