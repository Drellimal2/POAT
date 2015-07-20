package bluescreen1.poat.Courses;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import bluescreen1.poat.Contracts.CourseEntry;
import bluescreen1.poat.MainActivity;
import bluescreen1.poat.R;


public class NewCourse extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);
        final EditText course_code = (EditText) findViewById(R.id.new_course_course_code);
        final EditText title = (EditText) findViewById(R.id.new_course_title);
        final EditText desc = (EditText) findViewById(R.id.new_course_desc);
        final EditText start_date = (EditText) findViewById(R.id.new_course_start_date);
        final EditText end_date = (EditText) findViewById(R.id.new_course_end_date);
        Button save = (Button) findViewById(R.id.new_course_save_button);
        Button cancel = (Button) findViewById(R.id.new_course_cancel_button);
        final Intent intent = new Intent(this, MainActivity.class);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCourse(course_code.getText().toString(), title.getText().toString(), desc.getText().toString(), start_date.getText().toString(), end_date.getText().toString());
                finish();
                //startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(intent);

            }
        });
        Toast.makeText(this,""+ start_date.getId(), Toast.LENGTH_LONG).show();


        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = DatePickerFragment.DateSet(start_date);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }

        });

        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = DatePickerFragment.DateSet(end_date);
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_course, menu);
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

    public void saveCourse(String course_code, String title, String desc, String startdate, String end_date){

        Cursor cursor = getContentResolver().query(
               CourseEntry.CONTENT_URI,
                new String[]{CourseEntry._ID},
                null,
                null,
                null);
        if(cursor != null) {
            cursor.moveToFirst();
            cursor.close();

        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(CourseEntry.COLUMN_COURSE_CODE, course_code);
        contentValues.put(CourseEntry.COLUMN_TITLE, title);
        contentValues.put(CourseEntry.COLUMN_DESC, desc);
        contentValues.put(CourseEntry.COLUMN_START_DATE, startdate);
        contentValues.put(CourseEntry.COLUMN_END_DATE, end_date);

        ContentUris.parseId(getContentResolver().insert(CourseEntry.CONTENT_URI, contentValues));


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
            date.setText(""+day+ "/"+(month+1) + "/" + (year));
            // Do something with the date chosen by the user
        }
    }

}
