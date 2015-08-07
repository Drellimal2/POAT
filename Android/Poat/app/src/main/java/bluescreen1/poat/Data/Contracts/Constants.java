package bluescreen1.poat.Data.Contracts;

import android.net.Uri;

/**
 * Created by Dane on 7/14/2015.
 */
public class Constants {

    public static final String CONTENT_AUTHORITY = "bluescreen1.poat.app";

    public static final String PATH_COURSE = "course";

    public static final String PATH_ASSIGNMENT = "assignment";

    public static final String PATH_TEST = "test";

    public static final String PATH_SUBTASK = "subtask";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

}
