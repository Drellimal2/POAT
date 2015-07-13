package bluescreen1.poat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import bluescreen1.poat.Contracts.AssignmentEntry;
import bluescreen1.poat.Contracts.CourseEntry;
import bluescreen1.poat.Contracts.SubTaskEntry;


/**
 * Created by Dane on 7/12/2015.
 */
public class PoatDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "poat.db";

    public PoatDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_COURSE_TABLE = "CREATE TABLE " + CourseEntry.TABLE_NAME + " (" +
                CourseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CourseEntry.COLUMN_COURSE_CODE + " TEXT UNIQUE NOT NULL, " +
                CourseEntry.COLUMN_TITLE + " VARCHAR(50) NOT NULL, " +
                CourseEntry.COLUMN_DESC + " TEXT, " +
                CourseEntry.COLUMN_START_DATE + " TEXT, " +
                CourseEntry.COLUMN_END_DATE + " TEXT, " +
                CourseEntry.COLUMN_IS_ACTIVE + " TINYINT(1) NOT NULL DEFAULT 0, " +
                CourseEntry.COLUMN_GPA + " REAL );";

        final String SQL_CREATE_ASSIGNMENT_TABE = "CREATE TABLE " + AssignmentEntry.TABLE_NAME + " (" +
                AssignmentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AssignmentEntry.COLUMN_COURSE_CODE + " TEXT NOT NULL, " +
                AssignmentEntry.COLUMN_TITLE + " VARCHAR(50) NOT NULL, " +
                AssignmentEntry.COLUMN_DESC + " TEXT, " +
                AssignmentEntry.COLUMN_GIVEN_DATE + " TEXT NOT NULL, " +
                AssignmentEntry.COLUMN_DUE_DATE + " TEXT NOT NULL, " +
                AssignmentEntry.COLUMN_IS_COMPLETE + " TINYINT(1) NOT NULL DEFAULT 0, " +
                AssignmentEntry.COLUMN_IS_SUBMITTED + " TINYINT(1) NOT NULL DEFAULT 0, " +
                AssignmentEntry.COLUMN_PRIORITY + " INT); ";

        final String SQL_CREATE_SUBTASK_TABLE = "CREATE TABLE " + SubTaskEntry.TABLE_NAME + " (" +
                SubTaskEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SubTaskEntry.COLUMN_ASSIGNMENT_ID + " INT NOT NULL, " +
                SubTaskEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                SubTaskEntry.COLUMN_DESC + " TEXT, " +
                SubTaskEntry.COLUMN_GIVEN_DATE + " TEXT, " +
                SubTaskEntry.COLUMN_DESIRED_END_DATE + " TEXT NOT NULL, " +
                SubTaskEntry.COLUMN_IS_COMPLETE + " TINYINT NOT NULL DEFAULT 0, " +
                SubTaskEntry.COLUMN_PRIORITY + " INTEGER NOT NULL, " +
                " FOREIGN KEY (" + SubTaskEntry.COLUMN_ASSIGNMENT_ID + ") REFERENCES " +
                AssignmentEntry.TABLE_NAME + " (" +AssignmentEntry._ID + "));";


        db.execSQL(SQL_CREATE_ASSIGNMENT_TABE);
        db.execSQL(SQL_CREATE_COURSE_TABLE);
        db.execSQL(SQL_CREATE_SUBTASK_TABLE);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);

    }
}
