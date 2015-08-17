package bluescreen1.poat.utils;

import java.util.Calendar;

import bluescreen1.poat.Data.Contracts.AssignmentEntry;
import bluescreen1.poat.Data.Contracts.CourseEntry;
import bluescreen1.poat.Data.Contracts.TestEntry;

/**
 * Created by Dane on 7/21/2015.
 */
public class Utility {


    public static int ASSIGNMENTS_NO = 0;
    public static String[] ASSIGNMENT_COLUMNS = new String[]{
            AssignmentEntry.TABLE_NAME + '.' + AssignmentEntry._ID,
            AssignmentEntry.COLUMN_COURSE_CODE,
            AssignmentEntry.COLUMN_TITLE,
            AssignmentEntry.COLUMN_DESC,
//            AssignmentEntry.COLUMN_GIVEN_DATE,
            AssignmentEntry.COLUMN_DUE_DATETIME,
            AssignmentEntry.COLUMN_DUE_DATE,
            AssignmentEntry.COLUMN_DUE_TIME,
            AssignmentEntry.COLUMN_IS_COMPLETE,
            AssignmentEntry.COLUMN_IS_SUBMITTED
//            AssignmentEntry.COLUMN_PRIORITY
    };

    public static String[] COURSE_COLUMNS = new String[]{
            CourseEntry.TABLE_NAME + '.' + CourseEntry._ID,
            CourseEntry.COLUMN_COURSE_CODE,
            CourseEntry.COLUMN_TITLE,
            CourseEntry.COLUMN_DESC,
//            CourseEntry.COLUMN_START_DATE,
//            CourseEntry.COLUMN_END_DATE,
            CourseEntry.COLUMN_IS_ACTIVE,
            CourseEntry.COLUMN_GRADE,
            CourseEntry.COLUMN_CREDITS
    };

    public static String[] TEST_COLUMNS = new String[]{
            TestEntry.TABLE_NAME + '.' + TestEntry._ID,
            TestEntry.COLUMN_COURSE_CODE,
            TestEntry.COLUMN_TITLE,
            TestEntry.COLUMN_TOPICS,
            TestEntry.COLUMN_DATE,
            TestEntry.COLUMN_TIME,
            TestEntry.COLUMN_IS_COMPLETE

    };


    public static Calendar getCalendar(String date_s, String time_s){
        Calendar alarm = Calendar.getInstance();
        String date[] = date_s.split("/");
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]) -1;
        int year = Integer.parseInt(date[2]);
        String time[] = time_s.split(":");
        int hourofday = Integer.parseInt(time[0]);
        int min = Integer.parseInt(time[1]);
        alarm = setAlarmDate(month,day, year, alarm);
        alarm =setAlarmTime(hourofday, min, alarm);
        return alarm;
    }



    private static Calendar setAlarmDate(int month, int day, int year, Calendar alarm){
        alarm.set(year, month, day);
        return alarm;
    }

    private static Calendar setAlarmTime(int hour, int min, Calendar alarm){
        alarm.set(Calendar.HOUR_OF_DAY, hour);
        alarm.set(Calendar.MINUTE, min);
        alarm.set(Calendar.SECOND, 0);
        alarm.set(Calendar.MILLISECOND, 0);
        return alarm;
    }


}
