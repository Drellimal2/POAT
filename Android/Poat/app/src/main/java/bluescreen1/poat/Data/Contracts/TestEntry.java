package bluescreen1.poat.Data.Contracts;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Dane on 7/24/2015.
 */
public class TestEntry implements BaseColumns{
    public static final String TABLE_NAME = "test";

    public static final String COLUMN_COURSE_CODE = "test_course_code";

    public static final String COLUMN_TITLE = "test_title";

    public static final String COLUMN_TOPICS = "test_topics";

    public static final String COLUMN_DATE = "test__date";

    public static final String COLUMN_TIME = "test_time";

    public static final String COLUMN_IS_COMPLETE = "test_is_complete";

    public static final Uri CONTENT_URI =
            Constants.BASE_CONTENT_URI.buildUpon().appendPath(Constants.PATH_TEST).build();

    public static final String CONTENT_TYPE =
            "vnd.android.cursor.dir/" + Constants.CONTENT_AUTHORITY + "/" + Constants.PATH_TEST;
    public static final String CONTENT_ITEM_TYPE =
            "vnd.android.cursor.item/" + Constants.CONTENT_AUTHORITY + "/" + Constants.PATH_TEST;


    public static Uri buildTestUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }



}
