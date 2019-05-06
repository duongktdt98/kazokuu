package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dtd.tungduong.kazoku.R;
import com.dtd.tungduong.kazoku.Retrofit2.APIUtils;
import com.dtd.tungduong.kazoku.Retrofit2.DataClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class AddRoom3Frame extends Fragment {
    TextView finish;
    ImageView img_add;
    int Request_Code_Image = 123;
    String realpath = "";
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

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(realpath);
                Log.d("anh", file.getName());
                String file_path = file.getAbsolutePath();
                String[] mangtenfile = file_path.split("\\.");

                file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                MultipartBody.Part body =  MultipartBody.Part.createFormData("upload_file", file_path, requestBody);
                DataClient dataClient = APIUtils.getData();
                Call<String> callback = dataClient.UploadImg(body);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response != null){
                            Toast.makeText(getContext(), "Vào r", Toast.LENGTH_SHORT).show();
                            String message = response.body();
                            Log.d("BBB", message);
                        } else {
                            Toast.makeText(getContext(), "Null rồi", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                      }
                });

            }
        });
    return view;
    }


    private void Anhxa(View view) {
        finish = (TextView) view.findViewById(R.id.finish);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Request_Code_Image  && data != null && data.getData() != null){
            Uri uri = data.getData();
            realpath  = getRealPathFromURI(uri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                img_add.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       // super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }


}
