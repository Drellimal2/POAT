package bluescreen1.poat.Data.Contracts;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Dane on 7/11/2015.
 */
public class CourseEntry implements BaseColumns {

    public static final String TABLE_NAME = "course";

    public static final String COLUMN_COURSE_CODE = "course_code";

    public static final String COLUMN_TITLE = "course_title";

    public static final String COLUMN_DESC = "course_desc";

//    public static final String COLUMN_START_DATE = "course_date";
//
//    public static final String COLUMN_END_DATE = "course_end_date";

    public static final String COLUMN_IS_ACTIVE = "course_is_active";

    public static final String COLUMN_GRADE = "course_grade";

    public static final String COLUMN_CREDITS = "course_credits";




    public static final Uri CONTENT_URI =
            Constants.BASE_CONTENT_URI.buildUpon().appendPath(Constants.PATH_COURSE).build();

    public static final String CONTENT_TYPE =
            "vnd.android.cursor.dir/" + Constants.CONTENT_AUTHORITY + "/" + Constants.PATH_COURSE;
    public static final String CONTENT_ITEM_TYPE =
            "vnd.android.cursor.item/" + Constants.CONTENT_AUTHORITY + "/" + Constants.PATH_COURSE;


    public static Uri buildCourseUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

}
