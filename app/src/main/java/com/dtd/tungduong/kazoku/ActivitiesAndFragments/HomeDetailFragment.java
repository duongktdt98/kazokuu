package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dtd.tungduong.kazoku.Constants.PreferenceClass;
import com.dtd.tungduong.kazoku.R;
import com.squareup.picasso.Picasso;

import static com.dtd.tungduong.kazoku.Constants.PreferenceClass.RESTAURANT_NAME;

public class HomeDetailFragment extends Fragment {
    public SharedPreferences dealsDetailPref;
    String name, url, dia_chi, gia_tien;
    TextView name_detail,back_list_home, diachi;
    ImageView imageView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_home_detail, container, false);


        dealsDetailPref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        name = dealsDetailPref.getString(PreferenceClass.RESTAURANT_NAME, "");
        url = dealsDetailPref.getString(PreferenceClass.RESTAURANT_URL, "");
        dia_chi = dealsDetailPref.getString(PreferenceClass.RESTAURANT_ADRESS, "");
        gia_tien = dealsDetailPref.getString(PreferenceClass.RESTAURANT_PRICE, "");

        ImageView imageView = (ImageView) view.findViewById(R.id.img_detail);
        TextView name_detail = (TextView) view.findViewById(R.id.txt_name_home_detail);
        TextView adress = (TextView) view.findViewById(R.id.txt_adress_room);
        TextView back_list = (TextView) view.findViewById(R.id.back_list_home);
        TextView prices = (TextView) view.findViewById(R.id.txt_prices_room);
        name_detail.setText(name);
        adress.setText(dia_chi);
        prices.setText(gia_tien);
        Picasso.with(getContext()).load(url).resize(350,300).into(imageView);
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        back_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    SearchFragment searchFragment = new SearchFragment();
                    fragmentTransaction.replace(R.id.frame_search, searchFragment);
                    fragmentTransaction.commit();
                }
        });

        return view;
    }


}
