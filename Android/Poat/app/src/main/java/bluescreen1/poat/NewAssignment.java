package bluescreen1.poat;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import bluescreen1.poat.Contracts.AssignmentEntry;
import bluescreen1.poat.Contracts.CourseEntry;


public class NewAssignment extends ActionBarActivity  implements LoaderManager.LoaderCallbacks<Cursor>{

    private final int COURSE_CODE_LOADER = 0;
    private SimpleCursorAdapter mCourseAdapter;
    Spinner course_code_dropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_assignment);
        final EditText title = (EditText) findViewById(R.id.new_assignment_title);
        final EditText desc = (EditText) findViewById(R.id.new_assignment_description);
        final EditText given_date = (EditText) findViewById(R.id.new_assignment_given_date);
        final EditText due_date = (EditText) findViewById(R.id.new_assignment_due_date);
        final EditText priority = (EditText) findViewById(R.id.new_assignment_priority);
        course_code_dropdown = (Spinner) findViewById(R.id.new_assignment_couse_code_dropdown);
        mCourseAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                null,
                new String[]{CourseEntry.COLUMN_COURSE_CODE},
                new int[]{android.R.id.text1},
                0);

        mCourseAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                ((TextView) view).setText(cursor.getString(columnIndex));
                return true;
            }
        });
        getSupportLoaderManager().initLoader(COURSE_CODE_LOADER, null, this);

        course_code_dropdown.setAdapter(mCourseAdapter);
        Button save = (Button) findViewById(R.id.new_assignment_save_button);
        Button cancel = (Button) findViewById(R.id.new_assignment_cancel_button);

        final Intent intent = new Intent(this, MainActivity.class);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveAssignment( title.getText().toString(),
                                desc.getText().toString(),
                        ((Cursor)course_code_dropdown.getSelectedItem()).getString(1),
                                given_date.getText().toString(),
                                due_date.getText().toString(),
                                priority.getText().toString()
                                );
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_assignment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void saveAssignment(String title, String desc, String course_code, String given_date, String due_date, String priority){
        Cursor cursor = getContentResolver().query(
                AssignmentEntry.CONTENT_URI,
                new String[]{AssignmentEntry._ID},
                null,
                null,
                null);
        if(cursor != null) {
            cursor.moveToFirst();
            cursor.close();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(AssignmentEntry.COLUMN_COURSE_CODE, course_code);
        contentValues.put(AssignmentEntry.COLUMN_TITLE, title);
        contentValues.put(AssignmentEntry.COLUMN_DESC, desc);
        contentValues.put(AssignmentEntry.COLUMN_GIVEN_DATE, given_date);
        contentValues.put(AssignmentEntry.COLUMN_DUE_DATE, due_date);
        contentValues.put(AssignmentEntry.COLUMN_PRIORITY, priority);

        Toast.makeText(this, "Inserted: " + ContentUris.parseId(getContentResolver().insert(AssignmentEntry.CONTENT_URI, contentValues)), Toast.LENGTH_LONG).show();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                CourseEntry.CONTENT_URI,
                new String[]{CourseEntry._ID,CourseEntry.COLUMN_COURSE_CODE},
                null,
                null,
                null
                );
    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCourseAdapter.swapCursor(data);
        course_code_dropdown.setAdapter(mCourseAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCourseAdapter.swapCursor(null);
    }


}
