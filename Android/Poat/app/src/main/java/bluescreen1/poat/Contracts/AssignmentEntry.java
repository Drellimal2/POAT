package bluescreen1.poat.Contracts;

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

    public static final String COLUMN_PRIORITY = "assignemnt_priority";

    public static final String COLUMN_IS_COMPLETE = "assignment_is_complete";

    public static final String COLUMN_IS_SUBMITTED = "assignment_is_submit";

}
