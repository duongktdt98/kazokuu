package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dtd.tungduong.kazoku.R;

import java.util.ArrayList;

public class AddRoom1 extends Fragment {
    TextView txt_add_room2;
    Spinner spinnercity;
    // EditText

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

        final ArrayList<String> arrayCity = new ArrayList<String>();
        arrayCity.add("Hà Nội");
        arrayCity.add("Hải Phòng");
        arrayCity.add("Đà Nẵng");
        arrayCity.add("Huế");
        arrayCity.add("Nghệ An");
        arrayCity.add("Hà Tĩnh");
        arrayCity.add("Bắc Ninh");
        arrayCity.add("Hà Nội");
        arrayCity.add("Hải Phòng");
        arrayCity.add("Đà Nẵng");
        arrayCity.add("Huế");
        arrayCity.add("Nghệ An");
        arrayCity.add("Hà Tĩnh");
        arrayCity.add("Bắc Ninh");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, arrayCity);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercity.setAdapter(arrayAdapter);

        spinnercity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),    arrayCity.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private void Anhxa(View view) {
        txt_add_room2 = (TextView) view.findViewById(R.id.add_form2);
        spinnercity = (Spinner) view.findViewById(R.id.spinnercity);

    }


}
