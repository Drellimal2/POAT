package bluescreen1.poat.Assignments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Calendar;

import bluescreen1.poat.R;
import bluescreen1.poat.utils.Utility;

/**
 * Created by Dane on 7/20/2015.
 */
public class AssignmentAdapter extends CursorAdapter{


        Context con;
        LayoutInflater mInflater;
        CountDownTimer time_remaining;

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
            final TextView rem = (TextView) view.findViewById(R.id.assignment_list_item_days_remaining);
            Calendar dueDate = Utility.getCalendar(due_date, due_time);
            final int[] hours = new int[1];
            final int[] minutes = new int[1];
            final int[] seconds = new int[1];
            final int[] days = new int[1];
            time_remaining = new CountDownTimer((dueDate.getTimeInMillis()-Calendar.getInstance().getTimeInMillis()), 1000) {
                @Override
                public void onTick(long millsUntilFinished) {
                    seconds[0] =(int) millsUntilFinished/1000;
                    minutes[0] = seconds[0] /60;
                    seconds[0] %= 60;
                    hours[0] = minutes[0]/60;
                    minutes[0] %= 60;
                    days[0] = hours[0]/24;
                    hours[0] %= 24;
                    if(days[0] > 0){
                        rem.setText(days[0] + " Days Remaining");
                    } else {
                        if(hours[0] > 0){
                            rem.setText(hours[0] + " Hours Remaining");
                        } else {
                            rem.setText(minutes[0] + " Minutes Remaining");
                        }
                    }
                }
                @Override
                public void onFinish() {
                    rem.setText("Due");
                }
            };
            time_remaining.start();

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

            rem.setText(days_remaining);



        }


}
