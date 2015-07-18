package bluescreen1.poat;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bluescreen1.poat.Contracts.CourseEntry;


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

        Toast.makeText(this,"Inserted: "+ ContentUris.parseId(getContentResolver().insert(CourseEntry.CONTENT_URI, contentValues)), Toast.LENGTH_LONG).show();


    }

}
