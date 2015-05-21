package group9.agile.chalmers.com.agiletracker;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;

import group9.agile.chalmers.com.agiletracker.network.GithubBackendService;
import group9.agile.chalmers.com.agiletracker.common.view.SampleFragmentPagerAdapter;
import group9.agile.chalmers.com.agiletracker.common.view.SlidingTabLayout;
import group9.agile.chalmers.com.agiletracker.network.GithubServiceConnection;


public class MainActivity extends FragmentActivity {

    ViewPager mViewPager;
    GithubServiceConnection githubServiceConnection;
    //@Inject StateManager stateManager;

    /** Called when the user clicks the Send button */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initGithubBackendService();

        final ActionBar actionBar = getActionBar();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the SlidingTabLayout the ViewPager
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        // Center the tabs in the layout
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);

        // Specify that tabs should be displayed in the action bar.
       // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {


            @Override
            public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

            }
        };
    }

    private void initGithubBackendService() {
        githubServiceConnection = new GithubServiceConnection(this);
        Intent serviceIntent = new Intent(this, GithubBackendService.class);
        bindService(serviceIntent, githubServiceConnection, BIND_IMPORTANT);
        startService(serviceIntent);
    }

    /**
     * Gets the ServiceConnection to the GithubBackendService. Used to communicate with it.
     * @return the ServiceConnection
     */
    public GithubServiceConnection getGithubServiceConnection() {
        return githubServiceConnection;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(githubServiceConnection);
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