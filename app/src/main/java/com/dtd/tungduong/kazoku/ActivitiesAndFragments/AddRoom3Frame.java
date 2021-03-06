package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
import static android.content.Context.MODE_PRIVATE;
import static com.dtd.tungduong.kazoku.Constants.Config.GET_CSVC;
import static com.dtd.tungduong.kazoku.Constants.Config.GET_USER;
import static com.dtd.tungduong.kazoku.Constants.Config.LIST_HOME;
import static com.dtd.tungduong.kazoku.Constants.Config.POST_HOME;
import static com.dtd.tungduong.kazoku.Constants.PreferenceClass.USER_ID;


public class AddRoom3Frame extends Fragment {
    TextView finish, back_add2;
    SharedPreferences sharedPreferences;
    ImageView img_add;
    GridView grid_csvc;
    String name_home;
    CheckBox checkboxname;
    int Request_Code_Image = 123;
    String realpath = "", url_img = "", total_coin, id_user;
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
                            setGridViewHeightBasedOnChildren(grid_csvc, 2);

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
                sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, MODE_PRIVATE);
                id_user = sharedPreferences.getString(USER_ID, "");
                JSONObject jsonObject2 = new JSONObject();
                try {
                    jsonObject2.put("id_user", id_user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("jsonobj", jsonObject2.toString());
                RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
                JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, GET_USER, jsonObject2, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        JSONObject jsonResponse = null;
                        String strJson = response.toString();
                        try {
                            jsonResponse = new JSONObject(strJson);
                            int code_id = Integer.parseInt(jsonResponse.optString("code"));
                            if (code_id == 200) {
                                JSONObject json = new JSONObject(jsonResponse.toString());
                                total_coin = json.optString("msg");
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("coin", total_coin);
                                editor.commit();
                                Log.d("daylacoin", total_coin);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Eror", error.getMessage() + "");
                        Log.e("Eror", error.toString());
                    }
                });
                requestQueue2.add(jsonObjectRequest2);
                if (realpath.equals("") ) {
                    Toast.makeText(getContext(), "Mời bạn chọn ảnh", Toast.LENGTH_SHORT).show();
                } else {
                    total_coin = sharedPreferences.getString("coin", "");

                    Log.d("coin_D", total_coin);
                    if (total_coin.equals("0") || total_coin.equals("")) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setMessage("Bạn có muốn nạp thêm coin");
                        builder1.setTitle("Bạn không đủ coin để thực hiện.");

                        builder1.setPositiveButton(
                                "Có",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Toast.makeText(getContext(), "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                });

                        builder1.setNegativeButton(
                                "Hủy",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.dismiss();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    } else {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                        builder1.setMessage("Phí đăng phòng sẽ mất 5 Coin");
                        builder1.setTitle("Bạn có đồng ý đăng phòng không?");

                        builder1.setPositiveButton(
                                "Có",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        File file = new File(realpath);
                                        Log.d("file???", file.toString());
                                        String file_path = file.getAbsolutePath();
                                        String[] mangtenfile = file_path.split("\\.");
                                        String[] mangten = file.getName().split("\\.");
                                        url_img = mangten[0] + System.currentTimeMillis() + "." + mangten[1];
                                        file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
                                        String name = file.getName();
                                        Log.d("tenanh", url_img);
                                        Log.d("Duong dan", file_path);
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
                                        String url = sharedPreferences.getString(PreferenceClass.POST_IMG_URL, "");
                                        if (url != "" || url != null) {
                                            String chuoi_csvc = sharedPreferences.getString(PreferenceClass.POST_CSVC, "");
                                            Log.d("BBB", chuoi_csvc);
                                            JSONObject jsonObject2 = new JSONObject();
                                            String[] output = chuoi_csvc.split("\\s");
                                            for (int j = 0; j < output.length; j++) {
                                                String dem = String.valueOf(j);
                                                Log.d("CCC", output[j]);
                                                try {
                                                    jsonObject2.put(dem, output[j]);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                            Calendar calendar = Calendar.getInstance();
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            String ngay_ht = simpleDateFormat.format(calendar.getTime());
                                            JSONObject jsonObject = new JSONObject();
                                            String loai = sharedPreferences.getString(PreferenceClass.POST_TYPE_HOME, "");
                                            Boolean check = sharedPreferences.getBoolean(PreferenceClass.CHECK_NAME, false);
                                            if (check) {
                                                if (loai.equals("2")) {
                                                    name_home = "Chung Cư tại" + sharedPreferences.getString(PreferenceClass.POST_NAME, "");
                                                } else if (loai.equals("1")) {
                                                    name_home = "Căn hộ tại" + sharedPreferences.getString(PreferenceClass.POST_NAME, "");
                                                } else if (loai.equals("3")) {
                                                    name_home = "Nhà trọ tại" + sharedPreferences.getString(PreferenceClass.POST_NAME, "");
                                                }
                                            }else {
                                                name_home = sharedPreferences.getString(PreferenceClass.POST_NAME, "");
                                            }
                                            try {
                                                jsonObject.put("id_user", sharedPreferences.getString(PreferenceClass.USER_ID, ""));
                                                jsonObject.put("home_name", name_home);
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
                                                jsonObject.put("mo_ta", sharedPreferences.getString(PreferenceClass.POST_MOTA, ""));
                                                jsonObject.put("url_anh", url_img);
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
                                                            Toast.makeText(getContext(), "Bạn đã đăng phòng thành công. Xin chờ xét duyệt !", Toast.LENGTH_LONG).show();
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
                                        }
                                        dialog.cancel();
                                    }
                                });

                        builder1.setNegativeButton(
                                "Hủy",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.dismiss();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }


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

    public void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if (items > columns) {
            x = items / columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);

    }

}
