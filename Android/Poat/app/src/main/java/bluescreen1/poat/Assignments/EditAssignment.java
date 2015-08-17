package bluescreen1.poat.Assignments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
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
import bluescreen1.poat.MainActivity;
import bluescreen1.poat.R;
import bluescreen1.poat.utils.Utility;

/**
 * Created by Dane on 7/27/2015.
 */
public class EditAssignment extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private final int COURSE_CODE_LOADER = 0;
    private final int ASSIGNMENT_ID_LOADER = 1;
    private SimpleCursorAdapter mCourseAdapter;
    private String _id;
    EditText title;
    EditText desc;
//    EditText given_date;
    EditText due_date;
    EditText due_time;
//    EditText priority;
    Spinner course_code_dropdown;
//    public static final int COL_ID = 0;
//    public static final int COL_COURSE_CODE = 1;
    public static final int COL__TITLE = 2;
    public static final int COL__DESC = 3;
//    public static final int COL_GIVEN_DATE =4;
    public static final int COL__DUE_DATE = 5;
    public static final int COL_DUE_TIME = 6;
//    public static final int COL_DUE_DATETIME = 4;
//    public static final int COL_IS_COMPLETE = 7;
//    public static final int COL_IS_SUBMITTED = 8;
//    public static final int COL_PRIORITY = 9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_assignment);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(Color.parseColor("#0babdd"));
        setSupportActionBar(mToolbar);
        title = (EditText) findViewById(R.id.new_assignment_title);
//        priority = (EditText) findViewById(R.id.new_assignment_priority);
        desc = (EditText) findViewById(R.id.new_assignment_description);
//        given_date = (EditText) findViewById(R.id.new_assignment_given_date);
        due_date = (EditText) findViewById(R.id.new_assignment_due_date);
        due_time = (EditText) findViewById(R.id.new_assignment_due_time);
//        final EditText title = (EditText) findViewById(R.id.new_assignment_title);
//        final EditText desc = (EditText) findViewById(R.id.new_assignment_description);
//        final EditText given_date = (EditText) findViewById(R.id.new_assignment_given_date);
//        final EditText due_date = (EditText) findViewById(R.id.new_assignment_due_date);
//        final EditText due_time = (EditText) findViewById(R.id.new_assignment_due_time);
//        final EditText priority = (EditText) findViewById(R.id.new_assignment_priority);
//        course_code_dropdown = (Spinner) findViewById(R.id.new_assignment_couse_code_dropdown);
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
        getSupportLoaderManager().initLoader(ASSIGNMENT_ID_LOADER, null, this);

        course_code_dropdown.setAdapter(mCourseAdapter);
//        given_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment newFragment = DatePickerFragment.DateSet(given_date);
//                newFragment.show(getSupportFragmentManager(), "datePicker");
//            }
//        });

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
        Button save = (Button) findViewById(R.id.new_assignment_save_button);
        Button cancel = (Button) findViewById(R.id.new_assignment_cancel_button);

        final Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.ITEM_POS,1);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateAssignment(title.getText().toString(),
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

    private void updateAssignment(String title, String desc, String course_code, String due_date, String due_time){
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
        contentValues.put(AssignmentEntry.COLUMN_TITLE, title);
        contentValues.put(AssignmentEntry.COLUMN_DESC, desc);
//        contentValues.put(AssignmentEntry.COLUMN_GIVEN_DATE, given_date);
        contentValues.put(AssignmentEntry.COLUMN_DUE_DATE, due_date);
        contentValues.put(AssignmentEntry.COLUMN_DUE_TIME, due_time);
//        contentValues.put(AssignmentEntry.COLUMN_PRIORITY, priority);

        int updated = getContentResolver().update(AssignmentEntry.CONTENT_URI, contentValues, AssignmentEntry._ID + " = ?", new String[]{_id});
        Toast.makeText(this, "Inserted: " + updated, Toast.LENGTH_LONG).show();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        _id = getIntent().getStringExtra(AssignmentEntry._ID);
        switch(id){
            case COURSE_CODE_LOADER:
                return new CursorLoader(this,
                        CourseEntry.CONTENT_URI,
                        new String[]{CourseEntry._ID,CourseEntry.COLUMN_COURSE_CODE},
                        null,
                        null,
                        null);
            case ASSIGNMENT_ID_LOADER:
                return new CursorLoader(this,
                        AssignmentEntry.CONTENT_URI,
                        Utility.ASSIGNMENT_COLUMNS,
                        AssignmentEntry._ID + " = ?",
                        new String[]{_id},
                        null);
            default:
                return new CursorLoader(this,
                        CourseEntry.CONTENT_URI,
                        new String[]{CourseEntry._ID,CourseEntry.COLUMN_COURSE_CODE},
                        null,
                        null,
                        null);
        }


    }

    public void setData(Cursor data){
        title.setText(data.getString(COL__TITLE));
        desc.setText(data.getString(COL__DESC));
//        priority.setText(data.getString(COL_PRIORITY));
//        given_date.setText(data.getString(COL_GIVEN_DATE));
        due_date.setText(data.getString(COL__DUE_DATE));
        due_time.setText(data.getString(COL_DUE_TIME));

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
        if (!data.moveToFirst()) {
            Toast.makeText(this, "You failed", Toast.LENGTH_LONG).show();
            return; }
        switch(loader.getId()){
            case COURSE_CODE_LOADER:
                mCourseAdapter.swapCursor(data);
                course_code_dropdown.setAdapter(mCourseAdapter);
                break;
            case ASSIGNMENT_ID_LOADER:
                Toast.makeText(this,"This is " + data.getString(1),Toast.LENGTH_LONG).show();
                setData(data);
                break;

        }

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

        @NonNull
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

        @NonNull
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
