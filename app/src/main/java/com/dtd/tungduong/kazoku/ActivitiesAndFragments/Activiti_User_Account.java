package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dtd.tungduong.kazoku.Constants.PreferenceClass;
import com.dtd.tungduong.kazoku.R;

public class Activiti_User_Account extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private View activity_user__account;
    private View fragment_not_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__account);
        activity_user__account = findViewById(R.id.account_div);
        fragment_not_login = findViewById(R.id.frame_not_login);
        checkLogInSession();
    }

    private void checkLogInSession(){
        boolean getLoINSession = sharedPreferences.getBoolean(PreferenceClass.IS_LOGIN,false);
        if(getLoINSession){
            activity_user__account.setVisibility(View.VISIBLE);
            fragment_not_login.setVisibility(View.GONE);
        }
        else {
            fragment_not_login.setVisibility(View.VISIBLE);
            activity_user__account.setVisibility(View.GONE);
        }

    }
}
