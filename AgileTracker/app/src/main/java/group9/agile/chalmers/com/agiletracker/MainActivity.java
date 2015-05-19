package group9.agile.chalmers.com.agiletracker;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.Activity;
import android.view.KeyEvent;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;

import group9.agile.chalmers.com.agiletracker.common.logger.Log;
import group9.agile.chalmers.com.agiletracker.common.view.SampleFragmentPagerAdapter;
import group9.agile.chalmers.com.agiletracker.common.view.SlidingTabLayout;
import group9.agile.chalmers.com.agiletracker.ui.PokerGameFragment;
import group9.agile.chalmers.com.agiletracker.ui.SlidingTabsBasicFragment;


public class MainActivity extends FragmentActivity {

    ViewPager mViewPager;
    private String m_Text = "";
    private EditText edittext;
    /** Called when the user clicks the Send button */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        //login to git hub
        final Button btnOpenPopup = (Button)findViewById(R.id.openpopup);
        btnOpenPopup.setOnClickListener(new Button.OnClickListener() {
            //text input


            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);

                Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener() {

                    //text input


                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }
                });
                Button btnLogin = (Button) popupView.findViewById(R.id.login);
                btnLogin.setOnClickListener(new Button.OnClickListener() {

                    //text input


                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(btnOpenPopup, 100, 100);

            }
        });


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

        //addKeyListener();
    }
   /* public void addKeyListener() {

        // get edittext component
        edittext = (EditText) findViewById(R.id.userName);

        // add a key listener to keep track user input
        edittext.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                // if keydown and "enter" is pressed
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String userName = edittext.getText().toString();

                    return true;

                }
                return false;
            }
        });
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
