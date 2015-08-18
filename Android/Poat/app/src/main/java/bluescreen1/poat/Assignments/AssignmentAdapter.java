package bluescreen1.poat.Assignments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import bluescreen1.poat.Data.Contracts.AssignmentEntry;
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
        public void bindView(final View view, final Context context, Cursor cursor) {


            LinearLayout details = (LinearLayout) view.findViewById(R.id.list_item_assignment_details);
            final LinearLayout icons = (LinearLayout) view.findViewById(R.id.assignment_list_item_icon_actions);

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(icons.getVisibility() == View.GONE) {
                        icons.setVisibility(View.VISIBLE);
                    } else {
                        icons.setVisibility(View.GONE);
                    }
                }
            });

            ImageView  edit = (ImageView) view.findViewById(R.id.list_item_assignment_edit);
            ImageView  deets = (ImageView) view.findViewById(R.id.list_item_assignment_view);
            ImageView  complete = (ImageView) view.findViewById(R.id.list_item_assignment_complete);
            ImageView  submit = (ImageView) view.findViewById(R.id.list_item_assignment_submit);
            final String _id = cursor.getString(0);
            final Intent detailsIntent = new Intent(context, AssignmentDetailsActivity.class );

            final Intent editIntent =  new Intent(context, EditAssignment.class);
            detailsIntent.putExtra(AssignmentEntry._ID, _id);
            editIntent.putExtra(AssignmentEntry._ID, _id);
//                    startActivity(intent);)

            final ContentValues contentValues = new ContentValues();

            deets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(detailsIntent);
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Edit", Toast.LENGTH_LONG).show();
                    context.startActivity(editIntent);

                }
            });
            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Complete", Toast.LENGTH_LONG).show();
                    contentValues.put(AssignmentEntry.COLUMN_IS_COMPLETE, 1);
                    int updated = context.getContentResolver().update(AssignmentEntry.CONTENT_URI, contentValues, AssignmentEntry._ID + " = ?", new String[]{_id});
                    Toast.makeText(context, "" + updated, Toast.LENGTH_LONG).show();
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Submit", Toast.LENGTH_LONG).show();
                    contentValues.put(AssignmentEntry.COLUMN_IS_SUBMITTED, 1);
                    contentValues.put(AssignmentEntry.COLUMN_IS_COMPLETE, 1);
                    int updated = context.getContentResolver().update(AssignmentEntry.CONTENT_URI, contentValues, AssignmentEntry._ID + " = ?", new String[]{_id});
                    Toast.makeText(context, "" + updated, Toast.LENGTH_LONG).show();;
                }
            });


            String courseCode = cursor.getString(AssignmentFragment.COL_COURSE_CODE);
            TextView course_code = (TextView) view.findViewById(R.id.assignment_list_item_course_code);
            course_code.setText(courseCode);

            String Title = cursor.getString(AssignmentFragment.COL__TITLE);
            TextView title = (TextView) view.findViewById(R.id.assignment_list_item_title);
            title.setText(Title);


            String due_date = cursor.getString(AssignmentFragment.COL__DUE_DATE);
            String days_remaining = "3 days left";
            int is_complete = cursor.getInt(AssignmentFragment.COL_IS_COMPLETE);
            int is_submitted = cursor.getInt(AssignmentFragment.COL_IS_SUBMITTED);
            String due_time = cursor.getString(AssignmentFragment.COL_DUE_TIME);
            final TextView rem = (TextView) view.findViewById(R.id.assignment_list_item_days_remaining);
            Calendar dueDate = Utility.getCalendar(due_date, due_time);
            final long[] hours = new long[1];
            final long[] minutes = new long[1];
            final long[] seconds = new long[1];
            final long[] days = new long[1];
            time_remaining = new CountDownTimer((dueDate.getTimeInMillis()-Calendar.getInstance().getTimeInMillis()), 1000) {
                @Override
                public void onTick(long millsUntilFinished) {
                    seconds[0] = millsUntilFinished/1000;
                    minutes[0] = seconds[0] /60;
                    seconds[0] %= 60;
                    hours[0] = minutes[0]/60;
                    minutes[0] %= 60;
                    days[0] = hours[0]/24;
                    hours[0] %= 24;
                    if(days[0] > 1){
                        rem.setText(days[0] + " Days Remaining");
                    } else {
                        if(hours[0] > 1){
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
            FrameLayout indicator = (FrameLayout) view.findViewById(R.id.assignment_list_item_indicator);
            //ImageView indicator = (ImageView) view.findViewById(R.id.assignment_list_item_indicator);
            if(is_submitted == 1){
                indicator.setBackgroundColor(Color.parseColor("#11C300"));
                //indicator.setImageResource(R.mipmap.ic_bar_submitted);
                //indicator.setImageResource(R.mipmap.ic_flag_submitted);
            } else {
                if (is_complete == 1){
                    indicator.setBackgroundColor(Color.parseColor("#F4DE00"));
                    //indicator.setImageResource(R.mipmap.ic_bar_completed);
                    //indicator.setImageResource(R.mipmap.ic_flag_complete);

                }else {
                    indicator.setBackgroundColor(Color.parseColor("#dd7777"));
                    //ndicator.setImageResource(R.mipmap.ic_bar_notdone);
                    //indicator.setImageResource(R.mipmap.ic_flag_notdone);

                }
            }

            TextView due = (TextView) view.findViewById(R.id.assignment_list_item_due_date);
            due.setText(due_date + " @ " + due_time);




        }


}
