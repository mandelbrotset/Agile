package group9.agile.chalmers.com.agiletracker.common.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import group9.agile.chalmers.com.agiletracker.common.Resources;
import group9.agile.chalmers.com.agiletracker.ui.CommitViewFragment;
import group9.agile.chalmers.com.agiletracker.ui.LoginFragment;
import group9.agile.chalmers.com.agiletracker.ui.PokerGameFragment;


public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Log in", "Poker game"};
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            Boolean loggedIn= preferences.getBoolean(Resources.LOGGED_IN, false);
            if(loggedIn){
                tabTitles[0]="Commit info";
                return new CommitViewFragment();
            }else{
                tabTitles[0]="Log in";
                return new LoginFragment();
            }

        }else{
            return new PokerGameFragment();
        }
    }

    @Override
    public int getItemPosition(Object item) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean loggedIn= preferences.getBoolean(Resources.LOGGED_IN, false);
        if(loggedIn){
            tabTitles[0]="Commit info";
        }else{
            tabTitles[0]="Log in";
        }
        return tabTitles[position];
    }
}