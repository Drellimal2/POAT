package bluescreen1.poat.Data.Contracts;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Dane on 7/11/2015.
 */
public class SubTaskEntry implements BaseColumns{

    public static final String TABLE_NAME = "subtask";

    public static final String COLUMN_ASSIGNMENT_ID = "assignment_id";

    public static final String COLUMN_TITLE = "subtask_title";

    public static final String COLUMN_DESC = "subtask_desc";

    public static final String COLUMN_GIVEN_DATE = "subtask_given_date";

    public static final String COLUMN_DESIRED_END_DATE = "subtask_end_date";

    public static final String COLUMN_PRIORITY = "subtask_priority";

    public static final String COLUMN_IS_COMPLETE = "subtask_is_complete";


    public static final Uri CONTENT_URI =
            Constants.BASE_CONTENT_URI.buildUpon().appendPath(Constants.PATH_SUBTASK).build();

    public static final String CONTENT_TYPE =
            "vnd.android.cursor.dir/" + Constants.CONTENT_AUTHORITY + "/" + Constants.PATH_SUBTASK;
    public static final String CONTENT_ITEM_TYPE =
            "vnd.android.cursor.item/" + Constants.CONTENT_AUTHORITY + "/" + Constants.PATH_SUBTASK;


}
