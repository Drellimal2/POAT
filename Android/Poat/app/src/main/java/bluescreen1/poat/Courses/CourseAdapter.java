package bluescreen1.poat.Courses;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import bluescreen1.poat.R;

/**
 * Created by Dane on 7/16/2015.
 */
public class CourseAdapter extends CursorAdapter {

    Context con;
    LayoutInflater mInflater;

    public CourseAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        con = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return mInflater.inflate(R.layout.list_item_course, parent, false);
}

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


                    String courseCode = cursor.getString(CourseFragment.COL_COURSE_CODE);
                    TextView course_code = (TextView) view.findViewById(R.id.course_list_item_course_code);
                    course_code.setText("Course");


                    String Title = cursor.getString(CourseFragment.COL__TITLE);
                    TextView title = (TextView) view.findViewById(R.id.course_list_item_title);
                    title.setText(Title);



    }
}
