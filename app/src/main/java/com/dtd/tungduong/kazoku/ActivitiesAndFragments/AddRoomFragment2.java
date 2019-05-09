package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dtd.tungduong.kazoku.R;


public class AddRoomFragment2 extends Fragment  {
   TextView btn_back, add_room3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_room2, container, false);
        Anhxa(view);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();

            }
        });

        add_room3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddRoom3Frame add3 = new AddRoom3Frame();
                fragmentTransaction.replace(R.id.frame_container, add3);
                fragmentTransaction.addToBackStack("Add2");
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private void Anhxa(View view) {
        btn_back =(TextView) view.findViewById(R.id.back_add1);
        add_room3 =(TextView) view.findViewById(R.id.add_form3);

    }


}
