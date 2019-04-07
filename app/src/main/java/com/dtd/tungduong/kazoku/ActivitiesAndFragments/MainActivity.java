package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.Window;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dtd.tungduong.kazoku.Constants.PreferenceClass;
import com.dtd.tungduong.kazoku.R;

import static com.dtd.tungduong.kazoku.Constants.PreferenceClass.IS_LOGIN;

public class MainActivity extends AppCompatActivity {
    private long mBackPressed;
    public static SharedPreferences sPre;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private TextView mTextMessage;
    private ActionBar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = getSupportActionBar();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        initFragment();
        loadFragment(new SearchFragment());
    }

    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frame_container,new SearchFragment());
        ft.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        SharedPreferences sharedPreferences;
        SharedPreferences mSharedPreferences;


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    fragment = new SearchFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_chat:
                    fragment = new ChatFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_location:
                    fragment = new LocationFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_login:
                    mSharedPreferences = getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
                    boolean getLoginSession = mSharedPreferences.getBoolean(PreferenceClass.IS_LOGIN, true);
                    if (getLoginSession){
                        fragment = new UserAccount();
                        loadFragment(fragment);
                        return true;
                    } else {
                        fragment = new LoginFragment();
                        loadFragment(fragment);
                        return true;
                    }

            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            MainActivity.this.finish();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Nhấn lần nữa để thoát ứng dụng", Toast.LENGTH_SHORT).show();

            mBackPressed = System.currentTimeMillis();

        }

    }


}
