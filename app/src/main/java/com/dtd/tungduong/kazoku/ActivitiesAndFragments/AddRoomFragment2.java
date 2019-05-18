package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dtd.tungduong.kazoku.Constants.PreferenceClass;
import com.dtd.tungduong.kazoku.R;


public class AddRoomFragment2 extends Fragment {
    TextView btn_back, add_room3;
    RadioGroup radioGroup;
    SharedPreferences sharedPreferences;
    EditText edt_price_room, edt_price_coc, edt_price_dien, edt_price_nuoc, edt_dien_dich, edt_max_people, edt_mo_ta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_room2, container, false);
        Anhxa(view);
        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                switch (checkedId) {
                    case R.id.rd_can_ho:
                        editor.putString(PreferenceClass.POST_TYPE_HOME, "1");
                        break;
                    case R.id.rd_chung_cu:
                        editor.putString(PreferenceClass.POST_TYPE_HOME, "2");
                        break;
                    case R.id.rd_nha_tro:
                        editor.putString(PreferenceClass.POST_TYPE_HOME, "3");
                        break;
                }
                editor.commit();
            }
        });

        add_room3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_price_room.getText().length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập Giá phòng!", Toast.LENGTH_SHORT).show();
                } else if (edt_price_coc.getText().length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập số tiền đặt cọc !", Toast.LENGTH_SHORT).show();
                } else if (edt_price_dien.getText().length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập giá 1 số điện !", Toast.LENGTH_SHORT).show();
                } else if (edt_price_nuoc.getText().length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập giá 1 m³ nước !", Toast.LENGTH_SHORT).show();
                } else if (edt_dien_dich.getText().length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập diện tích phòng !", Toast.LENGTH_SHORT).show();
                } else if (edt_max_people.getText().length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập số người tối đa có thể ở !", Toast.LENGTH_SHORT).show();
                } else {
                    sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(PreferenceClass.POST_PRICES, edt_price_room.getText().toString());
                    editor.putString(PreferenceClass.POST_PRICES_COC, edt_price_coc.getText().toString());
                    editor.putString(PreferenceClass.POST_PRICES_DIEN, edt_price_dien.getText().toString());
                    editor.putString(PreferenceClass.POST_PRICES_NUOC, edt_price_nuoc.getText().toString());
                    editor.putString(PreferenceClass.POST_DIEN_TICH, edt_dien_dich.getText().toString());
                    editor.putString(PreferenceClass.POST_PEOPLE, edt_max_people.getText().toString());
                    editor.putString(PreferenceClass.POST_MOTA, edt_mo_ta.getText().toString());
                    editor.commit();
                    final FragmentManager fragmentManager = getFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    AddRoom3Frame add3 = new AddRoom3Frame();
                    fragmentTransaction.replace(R.id.frame_container, add3);
                    fragmentTransaction.addToBackStack("Add2");
                    fragmentTransaction.commit();
                }
            }
        });
        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        Toast.makeText(getContext(), sharedPreferences.getString(PreferenceClass.POST_DISTRICT, ""), Toast.LENGTH_SHORT).show();
        return view;
    }

    private void Anhxa(View view) {
        btn_back = (TextView) view.findViewById(R.id.back_add1);
        add_room3 = (TextView) view.findViewById(R.id.add_form3);
        edt_dien_dich = (EditText) view.findViewById(R.id.edt_dien_dich);
        edt_price_room = (EditText) view.findViewById(R.id.edt_price_room);
        edt_price_coc = (EditText) view.findViewById(R.id.edt_price_coc);
        edt_price_dien = (EditText) view.findViewById(R.id.edt_price_dien);
        edt_price_nuoc = (EditText) view.findViewById(R.id.edt_price_nuoc);
        edt_max_people = (EditText) view.findViewById(R.id.edt_max_people);
        edt_mo_ta = (EditText) view.findViewById(R.id.edt_mo_ta);
        radioGroup = (RadioGroup) view.findViewById(R.id.rd_loaiphong);

    }


}
