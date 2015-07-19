package bluescreen1.poat.Courses;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import bluescreen1.poat.Contracts.AssignmentEntry;
import bluescreen1.poat.Contracts.CourseEntry;
import bluescreen1.poat.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class CourseDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DETAIL_LOADER = 0;
    private static final int ASS_DET_LOADER =1;
    private static final String COURSE_CODE_KEY = CourseEntry.COLUMN_COURSE_CODE;
    private static final String COURSE_ID_KEY = CourseEntry._ID;
    private View view;
    private String[] COURSE_COLUMNS = new String[]{
            CourseEntry.TABLE_NAME + '.' + CourseEntry._ID,
            CourseEntry.COLUMN_COURSE_CODE,
            CourseEntry.COLUMN_TITLE,
            CourseEntry.COLUMN_DESC,
            CourseEntry.COLUMN_START_DATE,
            CourseEntry.COLUMN_END_DATE,
            CourseEntry.COLUMN_IS_ACTIVE,
            CourseEntry.COLUMN_GPA
    };


//    public static final int COL_ID = 0;
    public static final int COL_COURSE_CODE = 1;
    public static final int COL__TITLE = 2;
    public static final int COL__DESC = 3;
//    public static final int COL_START_DATE =4;
//    public static final int COL__END_DATE = 5;
//    public static final int COL_IS_ACTIVE = 6;
//    public static final int COL_GPA = 7;

    private SimpleCursorAdapter mAssignmentAdapter;
    private String[] ASSIGNMENT_COLUMNS = new String[]{
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

    //public static final int ASSIGNMENT_COL_ID = 0;
    public static final int ASSIGNMENT_COL_COURSE_CODE = 1;
    public static final int ASSIGNMENT_COL__TITLE = 2;
    //public static final int ASSIGNMENT_COL__DESC = 3;
    public static final int ASSIGNMENT_COL_GIVEN_DATE =4;
    //public static final int ASSIGNMENT_COL__DUE_DATE = 5;
    public static final int ASSIGNMENT_COL_DUE_TIME = 6;
    //public static final int ASSIGNMENT_COL_IS_COMPLETE = 7;
    //public static final int ASSIGNMENT_COL_IS_SUBMITTED = 8;
    //public static final int ASSIGNMENT_COL_PRIORITY = 9;
    ListView assignment_list;
    public CourseDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_details, container, false);

        assignment_list = (ListView) rootView.findViewById(R.id.course_details_assignments);
        mAssignmentAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_item_assignment,
                null,
                new String[]{
                        AssignmentEntry.COLUMN_TITLE,
                        AssignmentEntry.COLUMN_COURSE_CODE,
                        AssignmentEntry.COLUMN_GIVEN_DATE,
                        AssignmentEntry.COLUMN_DUE_TIME
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
                    case ASSIGNMENT_COL__TITLE:
                        ((TextView) view).setText(cursor.getString(columnIndex));
                        return true;
                    case ASSIGNMENT_COL_COURSE_CODE:
                        ((TextView) view).setText(cursor.getString(columnIndex));
                        return true;
                    case ASSIGNMENT_COL_GIVEN_DATE:
                        ((TextView) view).setText(cursor.getString(columnIndex));
                        return true;

                    case ASSIGNMENT_COL_DUE_TIME:
                        ((TextView) view).setText(cursor.getString(columnIndex));
                        return true;

                }

                return false;
            }
        });

        assignment_list.setAdapter(mAssignmentAdapter);
        view = rootView;
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Intent intent = getActivity().getIntent();
        if (intent == null || !intent.hasExtra(COURSE_CODE_KEY) || !intent.hasExtra(COURSE_ID_KEY)) {
            return null;
        }
        String courseCode = intent.getStringExtra(COURSE_CODE_KEY);
        String _id = intent.getStringExtra(COURSE_ID_KEY);
        CursorLoader CL;

        switch (id){
            case ASS_DET_LOADER:

                CL = new CursorLoader(
                        getActivity(),
                        AssignmentEntry.CONTENT_URI,
                        ASSIGNMENT_COLUMNS,
                        AssignmentEntry.COLUMN_COURSE_CODE + " = ?",
                        new String[]{ courseCode },
                        null
                );
                break;
            case DETAIL_LOADER:
                CL = new CursorLoader(
                        getActivity(),
                        CourseEntry.CONTENT_URI,
                        COURSE_COLUMNS,
                        COURSE_ID_KEY + " = ?",
                        new String[]{ _id },
                        null
                );
                break;

            default:
                CL = new CursorLoader(
                        getActivity(),
                        CourseEntry.CONTENT_URI,
                        COURSE_COLUMNS,
                        COURSE_ID_KEY + " = ?",
                        new String[]{ _id },
                        null
                );
                break;
        }
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return CL;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!data.moveToFirst()) {
            Toast.makeText(getActivity(), "You failed", Toast.LENGTH_LONG).show();
            return; }
        switch(loader.getId()){
            case ASS_DET_LOADER:


                Toast.makeText(getActivity(), "did it", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "" + mAssignmentAdapter.getCount(), Toast.LENGTH_LONG).show();
                mAssignmentAdapter.swapCursor(data);

                assignment_list.setAdapter(mAssignmentAdapter);
                break;
            case DETAIL_LOADER:
                TextView course_code = (TextView) view.findViewById(R.id.course_details_course_code);
                course_code.setText(data.getString(COL_COURSE_CODE));
                TextView course_title = (TextView) view.findViewById(R.id.course_details_title);
                course_title.setText(data.getString(COL__TITLE));
                TextView course_desc = (TextView) view.findViewById(R.id.course_details_desc);
                course_desc.setText(data.getString(COL__DESC));
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAssignmentAdapter.swapCursor(null);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        getLoaderManager().initLoader(ASS_DET_LOADER,null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(DETAIL_LOADER, null, this);
        getLoaderManager().initLoader(ASS_DET_LOADER,null, this);


    }
}
