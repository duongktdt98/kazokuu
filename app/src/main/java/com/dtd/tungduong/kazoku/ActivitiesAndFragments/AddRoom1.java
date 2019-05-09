package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dtd.tungduong.kazoku.R;

public class AddRoom1 extends Fragment {
    TextView txt_add_room2;


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_room1, container, false);
        Anhxa(view);

        txt_add_room2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddRoomFragment2 add2 = new AddRoomFragment2();
                fragmentTransaction.replace(R.id.frame_container, add2);
                fragmentTransaction.addToBackStack("Add1");
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private void Anhxa(View view) {
        txt_add_room2 = (TextView) view.findViewById(R.id.add_form2);

    }


}
