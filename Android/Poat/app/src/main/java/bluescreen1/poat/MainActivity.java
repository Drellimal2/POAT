package bluescreen1.poat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseObject;

import bluescreen1.poat.Assignments.AssignmentMain;
import bluescreen1.poat.Courses.CourseFragment;
import bluescreen1.poat.utils.AlarmReceiver;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    AlarmReceiver alarmReceiver = new AlarmReceiver();
    private static final long DRAWER_CLOSE_DELAY_MS = 250;
    private static final String NAV_ITEM_ID = "navItemId";
    private DrawerLayout drawerLayout;
    private int navItemId;
    public static final String ITEM_POS = "item_pos";
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar mToolbar;
    private CharSequence mTitle;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(Color.parseColor("#0babdd"));
        setSupportActionBar(mToolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        // This method will trigger on item Click of navigation menu

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {

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
            case R.id.drawer_item_1:
                Toast.makeText(getApplicationContext(), "Account Selected", Toast.LENGTH_SHORT).show();
//                FragmentManager fragment = new FragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
                return true;

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
                Toast.makeText(getApplicationContext(), "Tests Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.drawer_item_5:
                Toast.makeText(getApplicationContext(), "Today Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.drawer_item_6:
                Toast.makeText(getApplicationContext(), "This Week Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.drawer_item_7:
                Toast.makeText(getApplicationContext(), "This Month Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.drawer_item_8:
                Toast.makeText(getApplicationContext(), "Past Selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(getApplicationContext(), "Default", Toast.LENGTH_SHORT).show();
                return true;
        }



        // Initializing Drawer Layout and ActionBarToggle


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
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
