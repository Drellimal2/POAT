package bluescreen1.poat.Tests;


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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import bluescreen1.poat.Data.Contracts.TestEntry;
import bluescreen1.poat.R;
import bluescreen1.poat.utils.Utility;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    SimpleCursorAdapter mTestAdapter;

    public static final int COL_ID = 0;
    public static final int COL_COURSE_CODE = 1;
    public static final int COL__TITLE = 2;
    public static final int COL_TOPIC = 3;
    public static final int COL_DATE = 4;
    public static final int COL_TIME = 5;
    public static final int COL_IS_COMPLETE = 6;

    private final int TEST_LOADER = 0;
    ListView tests_list;
    public static TestsFragment newInstance(int sectionNumber) {
        TestsFragment fragment = new TestsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =inflater.inflate(R.layout.fragment_main, container,false);



        //AssignmentAdapter adap = new AssignmentAdapter(getActivity(), null, 0);
        mTestAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_item_course,
                null,
                new String[]{
                        TestEntry.COLUMN_TITLE,
                        TestEntry.COLUMN_COURSE_CODE

                },
                new int[]{
                        R.id.course_list_item_course_code,
                        R.id.course_list_item_title,

                },
                0);
//                R.layout.list_item_assignment,
//                null,
//                new String[]{
//                        TestEntry.COLUMN_TITLE,
//                        TestEntry.COLUMN_COURSE_CODE,
//                        TestEntry.COLUMN_TIME,
//                        TestEntry.COLUMN_DATE
//                },
//                new int[]{
//                        R.id.assignment_list_item_title,
//                        R.id.assignment_list_item_course_code,
//                        R.id.assignment_list_item_due_date,
//                        R.id.assignment_list_item_days_remaining
//                },
//                0);
        mTestAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                switch (columnIndex) {
                    case COL__TITLE:
                        ((TextView) view).setText(cursor.getString(columnIndex));
                        return true;
                    case COL_COURSE_CODE:
                        ((TextView) view).setText(cursor.getString(columnIndex));
                        return true;
                    case COL_TIME:
                        ((TextView) view).setText("2 days");
                        return true;

                    case COL_DATE:
                        ((TextView) view).setText(cursor.getString(columnIndex));
                        return true;

                }

                return false;
            }
        });


        tests_list = (ListView) rootView.findViewById(R.id.main_list);
//        tests_list.setEmptyView(inflater.inflate(R.layout.empty_list, container));
        tests_list.setAdapter(mTestAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        activity.onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }


    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(TEST_LOADER, null, this);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.add_test, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.add_test:
                intent = new Intent(getActivity(), NewTest.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(TEST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(
                getActivity(),
                TestEntry.CONTENT_URI,
                Utility.TEST_COLUMNS,
                null,
                null,
                null);



    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Toast.makeText(getActivity(), ""+data.getCount(), Toast.LENGTH_LONG).show();
        mTestAdapter.swapCursor(data);
//        assignment_list.setAdapter(mAssignmentAdapter);
        tests_list.setAdapter(mTestAdapter);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mTestAdapter.swapCursor(null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

}
