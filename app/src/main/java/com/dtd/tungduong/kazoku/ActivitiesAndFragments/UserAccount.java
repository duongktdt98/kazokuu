package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.dtd.tungduong.kazoku.Constants.PreferenceClass;
import com.dtd.tungduong.kazoku.R;

import static android.content.Context.MODE_PRIVATE;

public class UserAccount extends Fragment {
    SharedPreferences sharedPreferences;
    private RelativeLayout activity_user__account, fragment_not_login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user__account, container, false);

        // Inflate the layout for this fragment
        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, MODE_PRIVATE);
        activity_user__account = view.findViewById(R.id.activity_user__account);
        fragment_not_login = view.findViewById(R.id.frame_not_login);
        checkLogInSession();
        return view;
    }

    public void onResume() {
        super.onResume();
        checkLogInSession();
    }

    private void checkLogInSession() {
        boolean getLoINSession = sharedPreferences.getBoolean(PreferenceClass.IS_LOGIN, false);
        if (getLoINSession) {
            activity_user__account.setVisibility(View.VISIBLE);
            fragment_not_login.setVisibility(View.GONE);
        } else {
            fragment_not_login.setVisibility(View.VISIBLE);
            activity_user__account.setVisibility(View.GONE);
        }

    }
}
