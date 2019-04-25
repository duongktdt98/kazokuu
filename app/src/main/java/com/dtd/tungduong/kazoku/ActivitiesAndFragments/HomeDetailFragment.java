package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.dtd.tungduong.kazoku.Constants.PreferenceClass;
import com.dtd.tungduong.kazoku.R;
import com.squareup.picasso.Picasso;

import static com.dtd.tungduong.kazoku.Constants.Config.imgBaseURL;

public class HomeDetailFragment extends Fragment {
    public SharedPreferences dealsDetailPref;
    String name, url, dientich, gia_tien, songuoi;
    TextView name_detail, back_list_home, diachi;
    ImageView imageView;
    ViewFlipper viewFlipper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_detail, container, false);


        dealsDetailPref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        name = dealsDetailPref.getString(PreferenceClass.HOME_NAME, "");
        url = dealsDetailPref.getString(PreferenceClass.HOME_IMG_URL, "");
        dientich = dealsDetailPref.getString(PreferenceClass.HOME_DIEN_TICH, "");
        gia_tien = dealsDetailPref.getString(PreferenceClass.HOME_PRICES, "");
        songuoi = dealsDetailPref.getString(PreferenceClass.HOME_PEOPLE, "");

        ImageView imageView = (ImageView) view.findViewById(R.id.img_detail);
        TextView name_detail = (TextView) view.findViewById(R.id.txt_name_home_detail);
        TextView adress = (TextView) view.findViewById(R.id.txt_dientich_room);
        TextView back_list = (TextView) view.findViewById(R.id.back_list_home);
        TextView prices = (TextView) view.findViewById(R.id.txt_prices_room);
        TextView people = (TextView) view.findViewById(R.id.txt_people_room);
        name_detail.setText(name);
        adress.setText(dientich);
        people.setText(songuoi);
        prices.setText(gia_tien);
        Picasso.with(getContext()).load(imgBaseURL + url).resize(350, 300).into(imageView);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogImage();
            }
        });
        back_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SearchFragment searchFragment = new SearchFragment();
                fragmentTransaction.replace(R.id.frame_container, searchFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
    private void DialogImage(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.modal_image_detail_home);
        viewFlipper = (ViewFlipper) dialog.findViewById(R.id.view_image_dialog);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        dialog.show();
    }

    @Override
    public void onStart() {
        Log.d("fragmentB", "fragmentB: onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("fragmentB", "fragmentB: onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("fragmentB", "fragmentB: onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("fragmentB", "fragmentB: onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("fragmentB", "fragmentB: onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d("fragmentB", "fragmentB: onDestroy");
        super.onDestroy();
    }

}
