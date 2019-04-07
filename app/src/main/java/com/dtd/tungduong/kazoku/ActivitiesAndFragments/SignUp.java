package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dtd.tungduong.kazoku.R;


public class SignUp extends Fragment {
    ImageView iconback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        AnhXa(view);
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        iconback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment loginFragment = new LoginFragment();
                fragmentTransaction.replace(R.id.frame_login, loginFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private void  AnhXa(View view){
        iconback  = (ImageView) view.findViewById(R.id.back_icon);

    }

}
