package bluescreen1.poat.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import bluescreen1.poat.Data.Contracts.AssignmentEntry;
import bluescreen1.poat.Data.Contracts.Constants;
import bluescreen1.poat.Data.Contracts.CourseEntry;
import bluescreen1.poat.Data.Contracts.TestEntry;
import bluescreen1.poat.utils.Utility;

/**
 * Created by Dane on 7/14/2015.
 */
public class PoatProvider extends ContentProvider {


    private static final UriMatcher uriMatcher = buildUriMatcher();
    private PoatDbHelper poatDbHelper;

    private static final int ASSIGNMENT = 100;
    private static final int ASSIGNMENT_WITH_COURSE = 101;
    private static final int COURSE = 300;
    private static final int COURSE_ID = 301;
    private static final int SUBTASK = 500;
    private static final int TEST= 700;
    private static final int TEST_ID= 701;



    private static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Constants.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, Constants.PATH_ASSIGNMENT, ASSIGNMENT);
        matcher.addURI(authority, Constants.PATH_ASSIGNMENT + "/*", ASSIGNMENT_WITH_COURSE);

        matcher.addURI(authority, Constants.PATH_COURSE, COURSE);
        matcher.addURI(authority, Constants.PATH_COURSE + "/#", COURSE_ID);

        matcher.addURI(authority, Constants.PATH_TEST, TEST);
        matcher.addURI(authority, Constants.PATH_TEST + "/#" , TEST_ID);


        return matcher;
    }


    @Override
    public boolean onCreate() {
        poatDbHelper = new PoatDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        final int match = uriMatcher.match(uri);
        switch (match){
            case ASSIGNMENT:{
                retCursor = poatDbHelper.getReadableDatabase().query(
                        AssignmentEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case ASSIGNMENT_WITH_COURSE: {
                retCursor = poatDbHelper.getReadableDatabase().query(
                        AssignmentEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case COURSE:{
                retCursor = poatDbHelper.getReadableDatabase().query(
                        CourseEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case COURSE_ID:{
                retCursor = poatDbHelper.getReadableDatabase().query(
                       CourseEntry.TABLE_NAME,
                        projection,
                        CourseEntry._ID + "= '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TEST:{
                retCursor = poatDbHelper.getReadableDatabase().query(
                        TestEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case TEST_ID:{
                retCursor = poatDbHelper.getReadableDatabase().query(
                        TestEntry.TABLE_NAME,
                        projection,
                        TestEntry._ID + " = ?",
                        new String[]{""+ContentUris.parseId(uri)},
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown URI");

        }


        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;

    }

    @Override
    public String getType(Uri uri) {
        switch(uriMatcher.match(uri)){

            case ASSIGNMENT:
                return AssignmentEntry.CONTENT_TYPE;

            case ASSIGNMENT_WITH_COURSE:
                return AssignmentEntry.CONTENT_TYPE;

            case COURSE:
                return CourseEntry.CONTENT_TYPE;

            case COURSE_ID:
                return CourseEntry.CONTENT_ITEM_TYPE;

            case TEST:
                return TestEntry.CONTENT_TYPE;

            case TEST_ID:
                return TestEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown URI");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = poatDbHelper.getWritableDatabase();

        Uri returnUri;
        switch(uriMatcher.match(uri)){

            case COURSE: {
                long _id = db.insert(CourseEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = CourseEntry.buildCourseUri(_id);
                    values.put(CourseEntry.TABLE_NAME + '.' + CourseEntry._ID, _id);
                    addCourseToParse(values);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            case ASSIGNMENT: {
                long _id = db.insert(AssignmentEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = AssignmentEntry.buildAssignmentUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            case TEST: {
                long _id = db.insert(TestEntry.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = TestEntry.buildTestUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    public void addCourseToParse(ContentValues values){
        ParseObject new_course =  new ParseObject("Course");
        String id = CourseEntry.TABLE_NAME + '.' + CourseEntry._ID;
        for(String key : Utility.COURSE_COLUMNS){
            if(key.equals(id)){
                new_course.put("Course_ID",values.getAsString(key) );
                continue;
            }

            if(values.get(key) != null) {
                new_course.put(key, values.getAsString(key));
            } else {
                new_course.put(key, 0);
            }
        }

        new_course.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

            }
        });

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = poatDbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case ASSIGNMENT:
                rowsDeleted = db.delete(
                        AssignmentEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case COURSE:
                rowsDeleted = db.delete(
                        CourseEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TEST:
                rowsDeleted = db.delete(
                        TestEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }



    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = poatDbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case ASSIGNMENT:
                rowsUpdated = db.update(AssignmentEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case COURSE:
                rowsUpdated = db.update(CourseEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TEST:
                rowsUpdated = db.update(TestEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }


}
