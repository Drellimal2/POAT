package bluescreen1.poat.Tests;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import bluescreen1.poat.Data.Contracts.AssignmentEntry;
import bluescreen1.poat.Data.Contracts.CourseEntry;
import bluescreen1.poat.Data.Contracts.TestEntry;
import bluescreen1.poat.Data.PoatDbHelper;
import bluescreen1.poat.MainActivity;
import bluescreen1.poat.R;


public class NewTest extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private Toolbar mToolbar;
    private final int COURSE_CODE_LOADER = 0;
    private SimpleCursorAdapter mCourseAdapter;
    Spinner course_code_dropdown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(Color.parseColor("#0babdd"));
        setSupportActionBar(mToolbar);
        final EditText title = (EditText) findViewById(R.id.new_test_title);
        final EditText desc = (EditText) findViewById(R.id.new_test_description);
        final EditText due_date = (EditText) findViewById(R.id.new_test_due_date);
        final EditText due_time = (EditText) findViewById(R.id.new_test_due_time);
        course_code_dropdown = (Spinner) findViewById(R.id.new_test_course_code_dropdown);
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

        due_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = DatePickerFragment.DateSet(due_date);
                newFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });
        due_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = TimePickerFragment.SetTime(due_time);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        Button save = (Button) findViewById(R.id.new_test_save_button);
        Button cancel = (Button) findViewById(R.id.new_test_cancel_button);

        final Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.ITEM_POS,1);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveTest(title.getText().toString(),
                        desc.getText().toString(),
                        ((Cursor) course_code_dropdown.getSelectedItem()).getString(1),
                        due_date.getText().toString(),
                        due_time.getText().toString()
                );
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void saveTest(String title, String desc, String course_code, String due_date, String due_time){
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
        contentValues.put(TestEntry.COLUMN_COURSE_CODE, course_code);
        contentValues.put(TestEntry.COLUMN_TITLE, title);
        contentValues.put(TestEntry.COLUMN_TOPICS, desc);
        contentValues.put(TestEntry.COLUMN_DATE, due_date);
        contentValues.put(TestEntry.COLUMN_TIME, due_time);
        contentValues.put(TestEntry.COLUMN_IS_COMPLETE, "0");


        Toast.makeText(this, "Inserted: " + ContentUris.parseId(getContentResolver().insert(AssignmentEntry.CONTENT_URI, contentValues)), Toast.LENGTH_LONG).show();
        PoatDbHelper poatDbHelper = new PoatDbHelper(this);
        final SQLiteDatabase db = poatDbHelper.getReadableDatabase();
        Toast.makeText(this, "Count: " +  poatDbHelper.getDueAssignments(db).getCount(), Toast.LENGTH_LONG).show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_test, menu);
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
    protected void onStop() {
        finish();
        super.onStop();
    }

    @Override
    protected void onPause(){
        finish();
        super.onPause();
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

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        static EditText date;

        public DatePickerFragment(){

        }
        public static DatePickerFragment DateSet(EditText view){

            date = view;
            return new DatePickerFragment();

        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String month_text =  String.format("%02d", month+1);
            String day_text = String.format("%02d", day);
            date.setText(day_text+ "/"+ month_text + "/" + (year));

            // Do something with the date chosen by the user
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        static EditText time;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public static TimePickerFragment SetTime(EditText view){
            time = view;
            return new TimePickerFragment();
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            String min_text = String.format("%02d", minute);
            String hour_text = String.format("%02d", hourOfDay);
            time.setText(hour_text + ":" + min_text);
            //setAlarmTime(hourOfDay, minute);

        }
    }
}
