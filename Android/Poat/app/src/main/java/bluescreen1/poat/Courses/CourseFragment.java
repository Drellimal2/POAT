package bluescreen1.poat.Courses;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.ContextMenu;
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

import bluescreen1.poat.Contracts.CourseEntry;
import bluescreen1.poat.R;

/**
 * Created by Dane on 7/14/2015.
 */
public class CourseFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private SimpleCursorAdapter mCourseAdapter;
    private static final int COURSE_LOADER = 0;
    ListView courseList;
    private String[] COURSE_COLUMNS = new String[]{
            CourseEntry.TABLE_NAME + '.' + CourseEntry._ID,
            CourseEntry.COLUMN_COURSE_CODE,
            CourseEntry.COLUMN_TITLE,
            CourseEntry.COLUMN_DESC,
            CourseEntry.COLUMN_START_DATE,
            CourseEntry.COLUMN_END_DATE,
            CourseEntry.COLUMN_IS_ACTIVE,
            CourseEntry.COLUMN_GRADE,
            CourseEntry.COLUMN_CREDITS
    };

    public static final int COL_ID = 0;
    public static final int COL_COURSE_CODE = 1;
    public static final int COL__TITLE = 2;
    //public static final int COL__DESC = 3;
    public static final int COL_START_DATE =4;
    public static final int COL__END_DATE = 5;
    //public static final int COL_IS_ACTIVE = 6;
    //public static final int COL_GPA = 7;


    public static CourseFragment newInstance(int sectionNumber) {
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public CourseFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mCourseAdapter = new SimpleCursorAdapter(getActivity(),
                R.layout.list_item_course,
                null,
                new String[]{
                        CourseEntry.COLUMN_COURSE_CODE,
                        CourseEntry.COLUMN_TITLE

                },
                new int[]{
                        R.id.course_list_item_course_code,
                        R.id.course_list_item_title,

                },
                0);

        mCourseAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                switch(columnIndex){
                    case COL_COURSE_CODE:
                        ((TextView) view).setText(cursor.getString(COL_COURSE_CODE));
                        return true;
                    case COL__TITLE:
                        ((TextView) view).setText(cursor.getString(columnIndex));
                        return true;
                    case COL_START_DATE:
                        Toast.makeText(getActivity(), "test", Toast.LENGTH_LONG).show();
                        return true;
                    case COL__END_DATE:
                        ((TextView) view).setText(cursor.getString(columnIndex));
                        return true;

                }
                return false;
            }
        });

//        mCourseAdapter = new CourseAdapter(getActivity(), null, 0);


        courseList = (ListView) rootView.findViewById(R.id.main_list);
        registerForContextMenu(courseList);

        courseList.setAdapter(mCourseAdapter);
        final Intent detailIntent = new Intent(getActivity(), CourseDetailsActivity.class);
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = mCourseAdapter.getCursor();
                if (cursor != null && cursor.moveToPosition(position)) {
                    detailIntent.putExtra(CourseEntry.COLUMN_COURSE_CODE,cursor.getString(COL_COURSE_CODE) );
                    detailIntent.putExtra(CourseEntry._ID, cursor.getString(COL_ID));
                    startActivity(detailIntent);
                }
            }
        });


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        activity.onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(
                getActivity(),
                CourseEntry.CONTENT_URI,
                COURSE_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCourseAdapter.swapCursor(data);
        courseList.setAdapter(mCourseAdapter);

        //Toast.makeText(getActivity(), "Count " + data.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(COURSE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCourseAdapter.swapCursor(null);
    }

    @Override
    public void onResume() {
        super.onResume();
            getLoaderManager().restartLoader(COURSE_LOADER, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.add_course, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.add_course:
                intent = new Intent(getActivity(), NewCourse.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.add_course, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterViewCompat.AdapterContextMenuInfo info = (AdapterViewCompat.AdapterContextMenuInfo) item.getMenuInfo();
        Toast.makeText(getActivity(), "WOOOOOOOOOOOOOOOH", Toast.LENGTH_LONG).show();
        return true;
    }

}

