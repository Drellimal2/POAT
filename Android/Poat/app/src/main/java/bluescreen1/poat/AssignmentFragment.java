package bluescreen1.poat;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import bluescreen1.poat.Contracts.AssignmentEntry;
import bluescreen1.poat.Contracts.CourseEntry;

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
    private String[] ASSIGNMENT_COLUMNS = new String[]{
            AssignmentEntry.TABLE_NAME + '.' + CourseEntry._ID,
            AssignmentEntry.COLUMN_COURSE_CODE,
            AssignmentEntry.COLUMN_TITLE,
            AssignmentEntry.COLUMN_DESC,
            AssignmentEntry.COLUMN_GIVEN_DATE,
            AssignmentEntry.COLUMN_DUE_DATE,
            AssignmentEntry.COLUMN_IS_COMPLETE,
            AssignmentEntry.COLUMN_IS_SUBMITTED,
            AssignmentEntry.COLUMN_PRIORITY
    };

    public static final int COL_ID = 0;
    public static final int COL_COURSE_CODE = 1;
    public static final int COL__TITLE = 2;
    public static final int COL__DESC = 3;
    public static final int COL_GIVEN_DATE =4;
    public static final int COL__DUE_DATE = 5;
    public static final int COL_IS_COMPLETE = 6;
    public static final int COL_IS_SUBMITTED = 7;
    public static final int COL_PRIORITY = 8;

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
        Button add = (Button) rootView.findViewById(R.id.add_button);
        assignment_list = (ListView) rootView.findViewById(R.id.main_list);
        mAssignmentAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_item_assignment,
                null,
                new String[]{},
                new int[]{},
                0);

        mAssignmentAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                return true;
            }
        });

        assignment_list.setAdapter(mAssignmentAdapter);
        add.setText("Add Assignments");
        final Intent intent = new Intent(getActivity(), NewAssignment.class);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                AssignmentEntry.CONTENT_URI,
                ASSIGNMENT_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(ASSIGNMENT_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAssignmentAdapter.swapCursor(data);
        assignment_list.setAdapter(mAssignmentAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAssignmentAdapter.swapCursor(null);
    }
}
