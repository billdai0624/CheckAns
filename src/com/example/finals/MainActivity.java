package com.example.finals;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    public static Setting setting;
    private About about;
    private Quiz quiz;
    private Bundle bd;
    private int flag = 0;
    private long delta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        delta = System.currentTimeMillis();
       
        about = new About();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
       
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    	if(setting==null) 
    		setting = new Setting();
    		
    	
        // update the main content by replacing fragments
    	android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      //  FragmentManager fragmentManager = getSupportFragmentManager();
        
        if(position==0){
        	if(flag==0){
        		fragmentTransaction.replace(R.id.container, setting).commit();
        		quiz = null;
        		bd = null;
        	}
        	else{
        		flag = 1;
        	}
        }
        else if (position==1){
        	if(quiz==null)
        		quiz = new Quiz();
        	flag = 1;
        	if(bd==null){
        		bd = setting.getData();
        		bd.putLong("delta",System.currentTimeMillis() - delta);
        		quiz.setArguments(bd);
        	}
        	if(bd.getInt("quantity")==0){
        		bd = null;
        		Toast.makeText(this, "The quantity of your question is abnormal\nPlease reset again", Toast.LENGTH_LONG);
        	}
        	else
        		fragmentTransaction.replace(R.id.container, quiz).commit();
        }
        else {
    		fragmentTransaction.replace(R.id.container, about).commit();       	
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }
    public void refreshSetting(){
    	setting = new Setting();
    	flag = 0;
    }
  /*  public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

}
