package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dtd.tungduong.kazoku.Constants.PreferenceClass;
import com.dtd.tungduong.kazoku.R;
import com.dtd.tungduong.kazoku.Retrofit2.APIUtils;
import com.dtd.tungduong.kazoku.Retrofit2.DataClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;
import static com.dtd.tungduong.kazoku.Constants.Config.GET_CSVC;
import static com.dtd.tungduong.kazoku.Constants.Config.LIST_HOME;
import static com.dtd.tungduong.kazoku.Constants.Config.POST_HOME;


public class AddRoom3Frame extends Fragment {
    TextView finish, back_add2;
    SharedPreferences sharedPreferences;
    ImageView img_add;
    GridView grid_csvc;
    CheckBox checkboxname;
    int Request_Code_Image = 123;
    String realpath = "";
    ArrayList<CSVC> arrayImage;
    GridCsvcAdapter gridCsvcAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_room3_frame, container, false);
        Anhxa(view);
        arrayImage = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, GET_CSVC, null, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                JSONObject jsonResponse = null;
                String strJson = response.toString();
                Log.d("response", response.toString());
                try {
                    jsonResponse = new JSONObject(strJson);
                    int code_id = Integer.parseInt(jsonResponse.optString("code"));
                    Log.d("code_id", code_id + "");
                    if (code_id == 200) {
                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONArray jsonarray = json.getJSONArray("msg");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject datacsvc = jsonarray.getJSONObject(i);
                            Log.d("res", datacsvc.toString());
                            CSVC csvc = new CSVC();
                            csvc.setName_csvc(datacsvc.optString("ten"));
                            csvc.setImage_csvc(datacsvc.optString("image"));
                            csvc.setId_csvc(datacsvc.optString("id"));

                           arrayImage.add(csvc);

                        }
                        if (arrayImage != null) {
                            gridCsvcAdapter = new GridCsvcAdapter(arrayImage, getContext());
                            //adapterHome.getView(arrayImage,null);
                            grid_csvc.setAdapter(gridCsvcAdapter);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Kiểm tra lại mạng", Toast.LENGTH_SHORT).show();
                Log.d("Eror", error.getMessage() + "");
                Log.e("Eror", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);





        img_add = (ImageView) view.findViewById(R.id.add_image);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermistion();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
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
                String file_path = file.getAbsolutePath();
                if (file_path != null || file_path != "") {
                    String[] mangtenfile = file_path.split("\\.");
                    file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
                    String name = file.getName();
                    Log.d("ten", name);
                    Log.d("Duong dan", mangtenfile.toString());
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    // MultipartBody.Part is used to send also the actual file name
                    MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestFile);


                    DataClient dataClient = APIUtils.getData();

                    Call<Object> callback = dataClient.UploadImg(body);

                    callback.enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            // Không chạy vào đây luôn :(

                            if (response != null) {

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(PreferenceClass.POST_IMG_URL, response.body().toString());
                                editor.commit();
                                Log.d("CCC", response.body().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Log.d("BBB", t.toString());
                        }
                    });
                    sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
                    String chuoi_csvc = sharedPreferences.getString(PreferenceClass.POST_CSVC,"");
                    Log.d("BBB", chuoi_csvc);
                    JSONObject jsonObject2 = new JSONObject();
                    String[] output = chuoi_csvc.split("\\s");
                    for (int j = 0; j< output.length; j++){
                        String dem = String.valueOf(j);
                        Log.d("CCC", output[j]);
                        try {
                            jsonObject2.put( dem, output[j]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String ngay_ht = simpleDateFormat.format(calendar.getTime());
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id_user", sharedPreferences.getString(PreferenceClass.USER_ID, ""));
                        jsonObject.put("home_name", sharedPreferences.getString(PreferenceClass.POST_NAME, ""));
                        jsonObject.put("creats", ngay_ht);
                        jsonObject.put("province", sharedPreferences.getString(PreferenceClass.POST_PROVINCE, ""));
                        jsonObject.put("district", sharedPreferences.getString(PreferenceClass.POST_DISTRICT, ""));
                        jsonObject.put("ward", sharedPreferences.getString(PreferenceClass.POST_WARD, ""));
                        jsonObject.put("so_nha", sharedPreferences.getString(PreferenceClass.POST_NUMBERHOME, ""));
                        jsonObject.put("price_home_total", sharedPreferences.getString(PreferenceClass.POST_PRICES, ""));
                        jsonObject.put("price_nuoc", sharedPreferences.getString(PreferenceClass.POST_PRICES_NUOC, ""));
                        jsonObject.put("price_coc", sharedPreferences.getString(PreferenceClass.POST_PRICES_COC, ""));
                        jsonObject.put("price_dien", sharedPreferences.getString(PreferenceClass.POST_PRICES_DIEN, ""));
                        jsonObject.put("dien_tich", sharedPreferences.getString(PreferenceClass.POST_DIEN_TICH, ""));
                        jsonObject.put("max_people", sharedPreferences.getString(PreferenceClass.POST_PEOPLE, ""));
                        jsonObject.put("url_anh", sharedPreferences.getString(PreferenceClass.POST_IMG_URL, ""));
                        jsonObject.put("type_home", sharedPreferences.getString(PreferenceClass.POST_TYPE_HOME, ""));
                        jsonObject.put("id_csvc", jsonObject2);
                        jsonObject.put("kiem_duyet", 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("json???", jsonObject.toString());

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, POST_HOME, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            JSONObject jsonResponse = null;
                            String strJson = response.toString();
                            Log.d("response", response.toString());
                            try {
                                jsonResponse = new JSONObject(strJson);
                                int code_id = Integer.parseInt(jsonResponse.optString("code"));
                                Log.d("code_id", code_id + "");
                                if (code_id == 200) {
                                    Toast.makeText(getContext(), "Chúc Mừng bạn đã đang phòng thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getContext(), MainActivity.class));
                                    getActivity().finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "Kiểm tra lại mạng", Toast.LENGTH_SHORT).show();
                            Log.d("Eror", error.getMessage() + "");
                            Log.e("Eror", error.toString());
                        }
                    });
                    requestQueue.add(jsonObjectRequest);



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
        grid_csvc = (GridView) view.findViewById(R.id.grid_csvc);
        checkboxname = (CheckBox) view.findViewById(R.id.checkboxname);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Request_Code_Image && data != null && data.getData() != null) {
            Uri uri = data.getData();
            realpath = getRealPathFromURI(uri);
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

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }


}
