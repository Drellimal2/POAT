package bluescreen1.poat.Assignments;

import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import bluescreen1.poat.Contracts.AssignmentEntry;
import bluescreen1.poat.MainActivity;
import bluescreen1.poat.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class AssignmentDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static int ASSIGNMENT_ITEM_LOADER = 0;
    public AssignmentDetailsFragment() {
    }

    public static final int COL_ID = 0;
    public static final int COL_COURSE_CODE = 1;
    public static final int COL__TITLE = 2;
    public static final int COL__DESC = 3;
    public static final int COL_GIVEN_DATE =4;
    public static final int COL__DUE_DATE = 5;
    public static final int COL_DUE_TIME = 6;
    public static final int COL_IS_COMPLETE = 7;
    public static final int COL_IS_SUBMITTED = 8;
    public static final int COL_PRIORITY = 9;

    public static String submit, complete;
    View view;
    Button sub, comp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_assignment_details, container, false);
        sub = (Button) view.findViewById(R.id.assignment_details_submit);
        comp = (Button) view.findViewById(R.id.assignment_details_complete);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(ASSIGNMENT_ITEM_LOADER, null, this);

        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                AssignmentEntry.CONTENT_URI,
                AssignmentFragment.ASSIGNMENT_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        data.moveToFirst();

        final ContentValues contentValues = new ContentValues();
        final String _id = data.getString(COL_ID);
        String course_code= data.getString(COL_COURSE_CODE);
        String title= data.getString(COL__TITLE);
        String desc = data.getString(COL__DESC);
        String given_date= data.getString(COL_GIVEN_DATE);
        String due_date= data.getString(COL__DUE_DATE);
        String due_time= data.getString(COL_DUE_TIME);
        complete = data.getString(COL_IS_COMPLETE);
        submit = data.getString(COL_IS_SUBMITTED);
        String priority = data.getString(COL_PRIORITY);
        data.close();
        contentValues.put(AssignmentEntry.COLUMN_COURSE_CODE, _id);
        contentValues.put(AssignmentEntry.COLUMN_COURSE_CODE, course_code);
        contentValues.put(AssignmentEntry.COLUMN_TITLE, title);
        contentValues.put(AssignmentEntry.COLUMN_DESC, desc);
        contentValues.put(AssignmentEntry.COLUMN_GIVEN_DATE, given_date);
        contentValues.put(AssignmentEntry.COLUMN_DUE_DATE, due_date);
        contentValues.put(AssignmentEntry.COLUMN_DUE_TIME, due_time);
        contentValues.put(AssignmentEntry.COLUMN_PRIORITY, priority);

        final Intent intent = new Intent(getActivity(), MainActivity.class);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit= "1";
                complete = "1";
                contentValues.put(AssignmentEntry.COLUMN_IS_SUBMITTED, submit);
                contentValues.put(AssignmentEntry.COLUMN_IS_COMPLETE, complete);
                int updated = getActivity().getContentResolver().update(AssignmentEntry.CONTENT_URI, contentValues, AssignmentEntry._ID + " = ?", new String[]{_id});
                Toast.makeText(getActivity(), "" + updated, Toast.LENGTH_LONG).show();

                startActivity(intent);

            }
        });

        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complete = "1";
                contentValues.put(AssignmentEntry.COLUMN_IS_SUBMITTED, submit);
                contentValues.put(AssignmentEntry.COLUMN_IS_COMPLETE, complete);
                int updated = getActivity().getContentResolver().update(AssignmentEntry.CONTENT_URI, contentValues, AssignmentEntry._ID + " = ?", new String[]{_id});
                Toast.makeText(getActivity(), "" + updated, Toast.LENGTH_LONG).show();

                startActivity(intent);

            }
        });

        TextView update = (TextView) view.findViewById(R.id.assignment_details_title);
        update.setText(submit + "___ " + complete);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
