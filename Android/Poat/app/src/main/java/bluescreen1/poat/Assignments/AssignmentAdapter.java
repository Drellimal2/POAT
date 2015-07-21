package bluescreen1.poat.Assignments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import bluescreen1.poat.R;

/**
 * Created by Dane on 7/20/2015.
 */
public class AssignmentAdapter extends CursorAdapter{


        Context con;
        LayoutInflater mInflater;

        public AssignmentAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
            con = context;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return mInflater.inflate(R.layout.list_item_assignment, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {




            String courseCode = cursor.getString(AssignmentFragment.COL_COURSE_CODE);
            TextView course_code = (TextView) view.findViewById(R.id.assignment_list_item_course_code);
            course_code.setText(courseCode);


            String Title = cursor.getString(AssignmentFragment.COL__TITLE);
            TextView title = (TextView) view.findViewById(R.id.assignment_list_item_title);
            title.setText(Title);

            FrameLayout indicator = (FrameLayout) view.findViewById(R.id.assignment_list_item_indicator);

            String due_date = cursor.getString(AssignmentFragment.COL__DUE_DATE);
            String days_remaining = "3 days left";
            int is_complete = cursor.getInt(AssignmentFragment.COL_IS_COMPLETE);
            int is_submitted = cursor.getInt(AssignmentFragment.COL_IS_SUBMITTED);
            String due_time = cursor.getString(AssignmentFragment.COL_DUE_TIME);

            if(is_submitted == 1){
                indicator.setBackgroundColor(Color.parseColor("#11C300"));

            } else {
                if (is_complete == 1){
                    indicator.setBackgroundColor(Color.parseColor("#F4DE00"));
                }else {
                    indicator.setBackgroundColor(Color.parseColor("#dd7777"));
                }
            }

            TextView due = (TextView) view.findViewById(R.id.assignment_list_item_due_date);
            due.setText(due_date + " @ " + due_time);

            TextView rem = (TextView) view.findViewById(R.id.assignment_list_item_days_remaining);
            rem.setText(days_remaining);



        }


}
