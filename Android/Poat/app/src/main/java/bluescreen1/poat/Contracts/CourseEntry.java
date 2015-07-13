package bluescreen1.poat.Contracts;

import android.provider.BaseColumns;

/**
 * Created by Dane on 7/11/2015.
 */
public class CourseEntry implements BaseColumns {

    public static final String TABLE_NAME = "course";

    public static final String COLUMN_COURSE_CODE = "course_code";

    public static final String COLUMN_TITLE = "course_title";

    public static final String COLUMN_DESC = "course_desc";

    public static final String COLUMN_START_DATE = "course_date";

    public static final String COLUMN_END_DATE = "course_end_date";

    public static final String COLUMN_IS_ACTIVE = "course_is_active";

    public static final String COLUMN_GPA = "course_gpa";

}
