package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;


public class AddRoom3Frame extends Fragment {
    TextView finish, back_add2;
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
                checkPermistion();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, Request_Code_Image);
            }
        });

        back_add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File file = new File(realpath);
                Log.d("anh", file.getName());
                String file_path = file.getAbsolutePath();
                Log.d("anh", file_path);
                if (file_path != null || file_path != ""){
                    String[] mangtenfile = file_path.split("\\.");
                    file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];

                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    // MultipartBody.Part is used to send also the actual file name
                    MultipartBody.Part body =MultipartBody.Part.createFormData("uploaded_file", file_path , requestFile);


                    DataClient dataClient = APIUtils.getData();

                    Call<Object> callback =  dataClient.UploadImg(body);

                    callback.enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            // Không chạy vào đây luôn :(

                            if (response != null){
                                Log.d("CCC",response.body().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Log.d("BBB", t.toString());
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Mời bạn chọn ảnh!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    return view;
    }

    private void checkPermistion() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
    }
    private void Anhxa(View view) {
        finish = (TextView) view.findViewById(R.id.finish);
        back_add2 = (TextView) view.findViewById(R.id.back_add2);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Request_Code_Image  && data != null && data.getData() != null){
            Uri uri = data.getData();
            realpath  = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

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
