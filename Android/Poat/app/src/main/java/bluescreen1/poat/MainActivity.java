package bluescreen1.poat;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import bluescreen1.poat.Assignments.AssignmentMain;
import bluescreen1.poat.Courses.CourseFragment;
import bluescreen1.poat.Data.Contracts.AssignmentEntry;
import bluescreen1.poat.Data.Contracts.TestEntry;
import bluescreen1.poat.Tests.TestMain;
import bluescreen1.poat.utils.AlarmReceiver;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor>{

    private static final int TEST_LOADER = 1;
    private static final int ASSIGNMENT_LOADER = 0;
    AlarmReceiver alarmReceiver = new AlarmReceiver();
    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";
    private DrawerLayout drawerLayout;
    private int navItemId;
    private LinearLayout test;
    public static final String ITEM_POS = "item_pos";
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar mToolbar;
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(Color.parseColor("#0babdd"));
        setSupportActionBar(mToolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportLoaderManager().initLoader(ASSIGNMENT_LOADER, null, this);
        getSupportLoaderManager().initLoader(TEST_LOADER, null, this);
        final Intent account = new Intent(this, AccountActivity.class);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        menu.getItem(1).setChecked(true);
        onNavigationItemSelected(menu.getItem(1));
        test = (LinearLayout) navigationView.findViewById(R.id.nav_drawer_header);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastyToast("WELP");
                startActivity(account);
             }
        });

        // This method will trigger on item Click of navigation menu

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.open, R.string.close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        alarmReceiver.setAlarm(this);



    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void ToastyToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {


        //Checking if the item is in checked state or not, if not make it in checked state
        if (menuItem.isChecked()) menuItem.setChecked(false);
        else menuItem.setChecked(true);

        //Closing drawer on item click
        drawerLayout.closeDrawers();
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Check to see which item was being clicked and perform appropriate action
        switch (menuItem.getItemId()) {


            //Replacing the main content with ContentFragment Which is our Inbox View;
//            case R.id.drawer_item_1:
//                Toast.makeText(getApplicationContext(), "Account Selected", Toast.LENGTH_SHORT).show();
////                FragmentManager fragment = new FragmentManager();
//                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
////                fragmentTransaction.replace(R.id.container, fragment);
//                fragmentTransaction.commit();
//                return true;

            // For rest of the options we just show a toast on click

            case R.id.drawer_item_2:
                fragmentManager.beginTransaction().replace(R.id.container, CourseFragment.newInstance(2))
                          .commit();
                Toast.makeText(getApplicationContext(), "Courses Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.drawer_item_3:
                fragmentManager.beginTransaction().replace(R.id.container, AssignmentMain.newInstance(2))
                        .commit();
                Toast.makeText(getApplicationContext(), "Assignments Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.drawer_item_4:
                fragmentManager.beginTransaction().replace(R.id.container, TestMain.newInstance(2))
                        .commit();
                Toast.makeText(getApplicationContext(), "Tests Selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.drawer_item_5:
                Toast.makeText(getApplicationContext(), "Today Selected", Toast.LENGTH_SHORT).show();
                fragmentManager.beginTransaction().replace(R.id.container, TimeMain.newInstance(10))
                        .commit();
                return true;

            case R.id.drawer_item_6:
                Toast.makeText(getApplicationContext(), "This Week Selected", Toast.LENGTH_SHORT).show();
                fragmentManager.beginTransaction().replace(R.id.container, TimeMain.newInstance(11))
                        .commit();
                return true;

            case R.id.drawer_item_7:
                Toast.makeText(getApplicationContext(), "This Month Selected", Toast.LENGTH_SHORT).show();
                fragmentManager.beginTransaction().replace(R.id.container, TimeMain.newInstance(12))
                        .commit();
                return true;

            case R.id.drawer_item_8:
                Toast.makeText(getApplicationContext(), "Past Selected", Toast.LENGTH_SHORT).show();
                fragmentManager.beginTransaction().replace(R.id.container, TimeMain.newInstance(13))
                        .commit();
                return true;

            case R.id.drawer_item_9:
                Toast.makeText(getApplicationContext(),"GPA Selected", Toast.LENGTH_SHORT).show();

            default:
                Toast.makeText(getApplicationContext(), "Default", Toast.LENGTH_SHORT).show();
                return true;
        }



        // Initializing Drawer Layout and ActionBarToggle


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch(id){
            case TEST_LOADER:
                return new CursorLoader(
                        this,
                        TestEntry.CONTENT_URI,
                        new String[]{"count(" + TestEntry._ID + ")"},
                        TestEntry.COLUMN_IS_COMPLETE + " = ?",
                        new String[]{ "0"},
                        null
                );
            case ASSIGNMENT_LOADER:
                return new CursorLoader(this,
                        AssignmentEntry.CONTENT_URI,
                        new String[]{"count(" + AssignmentEntry._ID + ")"},
                        AssignmentEntry.COLUMN_DUE_DATETIME + " > ? AND " + AssignmentEntry.COLUMN_IS_SUBMITTED + " = ?" ,
                        new String[]{ Long.toString(Calendar.getInstance().getTimeInMillis()/1000), "0"},
                        null);


        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        TextView assignments = (TextView) test.findViewById(R.id.nav_drawer_header_no_assignments);
        TextView tests = (TextView) test.findViewById(R.id.nav_drawer_header_no_tests);
        switch(loader.getId()){
            case ASSIGNMENT_LOADER:
                Toast.makeText(this, "Assignments :" + data.getInt(0), Toast.LENGTH_LONG).show();
                assignments.setText(data.getInt(0) + " Assignments Due");
                break;
            case TEST_LOADER:
                Toast.makeText(this, "Tests :" + data.getInt(0), Toast.LENGTH_LONG).show();
                tests.setText(data.getInt(0) + " Tests Coming Up");
                break;

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
//
//        navigationView.getMenu().findItem(navItemId).setChecked(true);
//
//        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.open,
//                R.string.close);
//        drawerLayout.setDrawerListener(drawerToggle);
//        drawerToggle.syncState();
//
//        navigate(navItemId);
//        alarmReceiver.setAlarm(this);
//    }
//
//    private void navigate(final int itemId) {
//        // perform the actual navigation logic, updating the main content fragment etc
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(final MenuItem menuItem) {
//        // update highlighted item in the navigation menu
//        menuItem.setChecked(true);
//        navItemId = menuItem.getItemId();
//
//        // allow some time after closing the drawer before performing real navigation
//        // so the user can see what is happening
//        drawerLayout.closeDrawer(GravityCompat.START);
//        drawerActionHander.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                navigate(menuItem.getItemId());
//            }
//        }, DRAWER_CLOSE_DELAY_MS);
//        return true;
//    }
//
//    @Override
//    public void onConfigurationChanged(final Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        drawerToggle.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(final MenuItem item) {
//        if (item.getItemId() == android.support.v7.appcompat.R.id.home) {
//            return drawerToggle.onOptionsItemSelected(item);
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    protected void onSaveInstanceState(final Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt(NAV_ITEM_ID, navItemId);
//    }
//
////        mNavigationDrawerFragment = (NavigationDrawerFragment)
////                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
////        mTitle = getTitle();
////
////        // Set up the drawer.
////        mNavigationDrawerFragment.setUp(
////                R.id.navigation_drawer,
////                (DrawerLayout) findViewById(R.id.drawer_layout));
////
////
////        onNavigationDrawerItemSelected(1);
////
////        Intent intent = getIntent();
////        if(intent.hasExtra(ITEM_POS)){
////            int position = intent.getIntExtra(ITEM_POS,1);
////            onNavigationDrawerItemSelected(position);
////        }
//
// //   @Override
////    public void onNavigationDrawerItemSelected(int position) {
////        // update the main content by replacing fragments
////        FragmentManager fragmentManager = getSupportFragmentManager();
////        switch (position) {
////            case 0:
////                fragmentManager.beginTransaction()
////                        .replace(R.id.container, CourseFragment.newInstance(position + 1))
////                        .commit();
////                break;
////            case 1:
////                fragmentManager.beginTransaction()
////                        .replace(R.id.container, AssignmentMain.newInstance(position + 1))
////                        .commit();
////                break;
////
////            default:
////                fragmentManager.beginTransaction()
////                        .replace(R.id.container,  AssignmentMain.newInstance(position + 1))
////                        .commit();
////        }
////
////    }
////
////    public void onSectionAttached(int number) {
////        switch (number) {
////            case 1:
////                mTitle = getString(R.string.course);
////                break;
////            case 2:
////                mTitle = getString(R.string.assignments);
////                break;
////
////        }
////    }
////
////    public void restoreActionBar() {
////        ActionBar actionBar = getSupportActionBar();
////        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
////        actionBar.setDisplayShowTitleEnabled(true);
////        actionBar.setTitle(mTitle);
////    }
////
////
////
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        if (!mNavigationDrawerFragment.isDrawerOpen()) {
////            // Only show items in the action bar relevant to this screen
////            // if the drawer is not showing. Otherwise, let the drawer
////            // decide what to show in the action bar.
////            getMenuInflater().inflate(R.menu.main, menu);
////            restoreActionBar();
////            return true;
////        }
////        return super.onCreateOptionsMenu(menu);
////    }
////
////    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
////        // Handle action bar item clicks here. The action bar will
////        // automatically handle clicks on the Home/Up button, so long
////        // as you specify a parent activity in AndroidManifest.xml.
////        int id = item.getItemId();
////
////        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
////
////        return super.onOptionsItemSelected(item);
////    }
////
////
//
//}
