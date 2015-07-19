package bluescreen1.poat.Contracts;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Dane on 7/11/2015.
 */
public class AssignmentEntry implements BaseColumns {

    public static final String TABLE_NAME = "assignment";

    public static final String COLUMN_COURSE_CODE = "assignment_course_code";

    public static final String COLUMN_TITLE = "assignment_title";

    public static final String COLUMN_DESC = "assignment_desc";

    public static final String COLUMN_GIVEN_DATE = "assignment_given_date";

    public static final String COLUMN_DUE_DATE = "assignment_due_date";

    public static final String COLUMN_DUE_TIME = "assignment_due_time";

    public static final String COLUMN_PRIORITY = "assignemnt_priority";

    public static final String COLUMN_IS_COMPLETE = "assignment_is_complete";

    public static final String COLUMN_IS_SUBMITTED = "assignment_is_submit";

    //URI

    public static final Uri CONTENT_URI =
            Constants.BASE_CONTENT_URI.buildUpon().appendPath(Constants.PATH_ASSIGNMENT).build();

    public static final String CONTENT_TYPE =
            "vnd.android.cursor.dir/" + Constants.CONTENT_AUTHORITY + "/" + Constants.PATH_ASSIGNMENT;
    public static final String CONTENT_ITEM_TYPE =
            "vnd.android.cursor.item/" + Constants.CONTENT_AUTHORITY + "/" + Constants.PATH_ASSIGNMENT;

    public static Uri buildAssignmentUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static Uri buildAssignmentCourse(String course_code) {
        return CONTENT_URI.buildUpon().appendPath(course_code).build();
    }

}
