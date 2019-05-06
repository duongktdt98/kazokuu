package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dtd.tungduong.kazoku.R;

import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;


public class AddRoom3Frame extends Fragment {

    ImageView img_add;
    int Request_Code_Image = 123;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_room3_frame, container, false);
        Anhxa(view);
        img_add = (ImageView) view.findViewById(R.id.add_image);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, Request_Code_Image);
            }
        });

    return view;
    }
    private void Anhxa(View view) {


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Request_Code_Image  && data != null && data.getData() != null){
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                img_add.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       // super.onActivityResult(requestCode, resultCode, data);
    }




}
