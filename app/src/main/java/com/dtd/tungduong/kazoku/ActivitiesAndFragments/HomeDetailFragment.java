package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dtd.tungduong.kazoku.Constants.PreferenceClass;
import com.dtd.tungduong.kazoku.R;

import static com.dtd.tungduong.kazoku.Constants.PreferenceClass.RESTAURANT_NAME;

public class HomeDetailFragment extends Fragment {
    public SharedPreferences dealsDetailPref;
    String name;
    TextView txt_home;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_home_detail, container, false);


        dealsDetailPref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        name = dealsDetailPref.getString(PreferenceClass.RESTAURANT_NAME, "");

        txt_home = view.findViewById(R.id.txt_home_detail);

        txt_home.setText(name);
        return view;
    }


}
