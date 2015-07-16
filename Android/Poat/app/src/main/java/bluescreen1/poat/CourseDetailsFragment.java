package bluescreen1.poat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import bluescreen1.poat.Contracts.CourseEntry;


/**
 * A placeholder fragment containing a simple view.
 */
public class CourseDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DETAIL_LOADER = 0;
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
    public static final int COL_ID = 0;
    public static final int COL_COURSE_CODE = 1;
    public static final int COL__TITLE = 2;
    public static final int COL__DESC = 3;
    public static final int COL_START_DATE =4;
    public static final int COL__END_DATE = 5;
    public static final int COL_IS_ACTIVE = 6;
    public static final int COL_GPA = 7;


    public CourseDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_details, container, false);

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

        Toast.makeText(getActivity(), _id, Toast.LENGTH_LONG).show();


        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                getActivity(),
                CourseEntry.CONTENT_URI,
                COURSE_COLUMNS,
                COURSE_ID_KEY + " = ?",
                new String[]{ _id },
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!data.moveToFirst()) {
            Toast.makeText(getActivity(), "You failed", Toast.LENGTH_LONG).show();
            return; }
        TextView course_code = (TextView) view.findViewById(R.id.course_details_course_code);
        course_code.setText(data.getString(COL_COURSE_CODE));
        TextView course_title = (TextView) view.findViewById(R.id.course_details_title);
        course_title.setText(data.getString(COL__TITLE));
        TextView course_desc = (TextView) view.findViewById(R.id.course_details_desc);
        course_desc.setText(data.getString(COL__DESC));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(DETAIL_LOADER, null, this);

    }
}
