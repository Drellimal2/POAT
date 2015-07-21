package bluescreen1.poat.utils;

import java.util.Calendar;

/**
 * Created by Dane on 7/21/2015.
 */
public class Utility {






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
