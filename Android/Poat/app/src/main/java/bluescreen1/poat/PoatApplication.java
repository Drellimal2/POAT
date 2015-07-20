package bluescreen1.poat;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseUser;

/**
 * Created by Dane on 7/14/2015.
 */
public class PoatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Crash Reporting.
        //ParseCrashReporting.enable(this);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(this, "HdgqPMhYszyIxpmgKpqMn7URS2kHNtgJsZdMDBZi", "3mLt36PcC0P8EZL8W6pZGynpbgf4ujLfRPEQzsTV");

        ParseUser.enableAutomaticUser();
//        ParseACL defaultACL = new ParseACL();
//        // Optionally enable public read access.
//        // defaultACL.setPublicReadAccess(true);
//        ParseACL.setDefaultACL(defaultACL, true);
    }

}
