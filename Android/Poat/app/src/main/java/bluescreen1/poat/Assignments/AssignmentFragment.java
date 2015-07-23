package bluescreen1.poat.Assignments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import bluescreen1.poat.Contracts.AssignmentEntry;
import bluescreen1.poat.Contracts.CourseEntry;
import bluescreen1.poat.R;
import bluescreen1.poat.utils.Utility;

/**
 * Created by Dane on 7/14/2015.
 */
public class AssignmentFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{


    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    private SimpleCursorAdapter mAssignmentAdapter;
    public static String[] ASSIGNMENT_COLUMNS = new String[]{
            AssignmentEntry.TABLE_NAME + '.' + CourseEntry._ID,
            AssignmentEntry.COLUMN_COURSE_CODE,
            AssignmentEntry.COLUMN_TITLE,
            AssignmentEntry.COLUMN_DESC,
            AssignmentEntry.COLUMN_GIVEN_DATE,
            AssignmentEntry.COLUMN_DUE_DATE,
            AssignmentEntry.COLUMN_DUE_TIME,
            AssignmentEntry.COLUMN_IS_COMPLETE,
            AssignmentEntry.COLUMN_IS_SUBMITTED,
            AssignmentEntry.COLUMN_PRIORITY
    };

    public static final int COL_ID = 0;
    public static final int COL_COURSE_CODE = 1;
    public static final int COL__TITLE = 2;
    //public static final int COL__DESC = 3;
    public static final int COL_GIVEN_DATE =4;
    public static final int COL__DUE_DATE = 5;
    public static final int COL_DUE_TIME = 6;
    public static final int COL_IS_COMPLETE = 7;
    public static final int COL_IS_SUBMITTED = 8;
    public static final int COL_PRIORITY = 9;
    private Bundle filter;

    private AssignmentAdapter adap;
    public static final int ASSIGNMENT_LOADER = 0;
    private ListView assignment_list;

    public static AssignmentFragment newInstance(int sectionNumber) {
        AssignmentFragment fragment = new AssignmentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AssignmentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        assignment_list = (ListView) rootView.findViewById(R.id.main_list);

        adap = new AssignmentAdapter(getActivity(), null, 0);
        mAssignmentAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_item_assignment,
                null,
                new String[]{
                        AssignmentEntry.COLUMN_TITLE,
                        AssignmentEntry.COLUMN_COURSE_CODE,
                        AssignmentEntry.COLUMN_GIVEN_DATE,
                        AssignmentEntry.COLUMN_DUE_DATE
                },
                new int[]{
                        R.id.assignment_list_item_title,
                        R.id.assignment_list_item_course_code,
                        R.id.assignment_list_item_due_date,
                        R.id.assignment_list_item_days_remaining
                },
                0);

        mAssignmentAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                switch (columnIndex) {
                    case COL__TITLE:
                        ((TextView) view).setText(cursor.getString(columnIndex));
                        return true;
                    case COL_COURSE_CODE:
                        ((TextView) view).setText(cursor.getString(columnIndex));
                        return true;
                    case COL_GIVEN_DATE:
                        ((TextView) view).setText("2 days");
                        return true;

                    case COL__DUE_DATE:
                        ((TextView) view).setText(cursor.getString(columnIndex));
                        return true;

                }

                return false;
            }
        });

//        assignment_list.setAdapter(mAssignmentAdapter);
        assignment_list.setAdapter(adap);


        final Intent intent = new Intent(getActivity(), AssignmentDetailsActivity.class);

        assignment_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = mAssignmentAdapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {
                    intent.putExtra(AssignmentEntry._ID,cursor.getString(COL_ID) );
                    startActivity(intent);
                }


            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Toast.makeText(getActivity(), ""+id, Toast.LENGTH_LONG).show();
        filter = getArguments();
        int filte1r = filter.getInt(AssignmentMain.FILTER_KEY, 0);

        switch(filte1r){
            case 0:
                CursorLoader CL = new CursorLoader(getActivity(),
                        AssignmentEntry.CONTENT_URI,
                        ASSIGNMENT_COLUMNS,
                        null,
                        null,
                        null);

                return CL;
            case 1:
                return new CursorLoader(getActivity(),
                        AssignmentEntry.CONTENT_URI,
                        ASSIGNMENT_COLUMNS,
                        AssignmentEntry.COLUMN_IS_SUBMITTED + " = ?",
                        new String[]{"0"},
                        null);

            case 2:
                return new CursorLoader(getActivity(),
                        AssignmentEntry.CONTENT_URI,
                        ASSIGNMENT_COLUMNS,
                        AssignmentEntry.COLUMN_IS_SUBMITTED + " = ?",
                        new String[]{"1"},
                        null);
            default:
                return new CursorLoader(getActivity(),
                        AssignmentEntry.CONTENT_URI,
                        ASSIGNMENT_COLUMNS,
                        null,
                        null,
                        null);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(ASSIGNMENT_LOADER, null, this);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAssignmentAdapter.swapCursor(data);
//        assignment_list.setAdapter(mAssignmentAdapter);
        adap.swapCursor(data);
        assignment_list.setAdapter(adap);
        int filte1r = filter.getInt(AssignmentMain.FILTER_KEY, 0);

        switch(filte1r) {
            case 1:
                Utility.ASSIGNMENTS_NO = mAssignmentAdapter.getCount();
                Toast.makeText(getActivity(), "" + Utility.ASSIGNMENTS_NO, Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAssignmentAdapter.swapCursor(null);
        adap.swapCursor(null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.add_assignment:
                Intent intent = new Intent(getActivity(), NewAssignment.class);
                startActivity(intent);
                return true;

        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.add_assignment, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }
}
