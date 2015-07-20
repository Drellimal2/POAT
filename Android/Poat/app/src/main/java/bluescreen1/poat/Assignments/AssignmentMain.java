package bluescreen1.poat.Assignments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import bluescreen1.poat.Contracts.AssignmentEntry;
import bluescreen1.poat.R;
import bluescreen1.poat.utils.Alarm;

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
