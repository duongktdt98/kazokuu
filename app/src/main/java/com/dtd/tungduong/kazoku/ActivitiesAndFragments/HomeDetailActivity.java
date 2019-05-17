package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dtd.tungduong.kazoku.Constants.PreferenceClass;
import com.dtd.tungduong.kazoku.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.dtd.tungduong.kazoku.Constants.Config.HOME_DETAIL;
import static com.dtd.tungduong.kazoku.Constants.Config.LIST_CSVC_DETAIL;
import static com.dtd.tungduong.kazoku.Constants.Config.imgBaseURL;

public class HomeDetailActivity extends AppCompatActivity {
    public SharedPreferences dealsDetailPref;
    String name, url, dientich, gia_tien, songuoi, id_phong, gia_coc, gia_dien, gia_nuoc;
    TextView name_detail, back_list, tien_coc, booking_now, dien_tich, tien_dien, tien_nuoc, adress, so_nguoi, gia_phong, dia_chi;
    ImageView imageView;
    ViewFlipper viewFlipper;
    ArrayList<CSVC> arrayListCsvc;
    AdapterCSVSDetail adapterCSVSDetail;
    GridView gv_csvc;
    int limit = 4;
    TextView showmore;
    int max = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);
        AnhXa();
        dealsDetailPref = getApplication().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        name = dealsDetailPref.getString(PreferenceClass.HOME_NAME, "");
        url = dealsDetailPref.getString(PreferenceClass.HOME_IMG_URL, "");
        dientich = dealsDetailPref.getString(PreferenceClass.HOME_DIEN_TICH, "");
        gia_tien = dealsDetailPref.getString(PreferenceClass.HOME_PRICES, "");
        songuoi = dealsDetailPref.getString(PreferenceClass.HOME_PEOPLE, "");

        name_detail.setText(name);
        adress.setText(dientich);
        so_nguoi.setText(songuoi);
        Picasso.with(this).load(imgBaseURL + url).resize(350, 300).into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogImage();
            }
        });
        back_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), MainActivity.class));
            }
        });


        booking_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Booking_home_Frame booking_home_frame = new Booking_home_Frame();
                fragmentTransaction.replace(R.id.frame_container_detail, booking_home_frame);
                fragmentTransaction.commit();
            }
        });
        getDetail();
        getDetail_CSVC();
    }
    private void AnhXa() {
        gv_csvc = (GridView) findViewById(R.id.gv_csvc);
        imageView = (ImageView) findViewById(R.id.img_detail);
        name_detail = (TextView) findViewById(R.id.txt_name_home_detail);
        showmore = (TextView) findViewById(R.id.show_more);
        adress = (TextView) findViewById(R.id.txt_dientich_room);
        back_list = (TextView) findViewById(R.id.back_list_home);
        tien_coc = (TextView) findViewById(R.id.txt_tien_coc);
        so_nguoi = (TextView) findViewById(R.id.txt_people_room);
        dien_tich = (TextView) findViewById(R.id.txt_dientich_room);
        booking_now = (TextView) findViewById(R.id.booking);
        tien_dien = (TextView) findViewById(R.id.txt_dien);
        tien_nuoc = (TextView) findViewById(R.id.txt_nuoc);
        gia_phong = (TextView) findViewById(R.id.tong_tien);
        dia_chi = (TextView) findViewById(R.id.txt_dia_chi);
    }

    private void getDetail() {
        dealsDetailPref = this.getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        id_phong = dealsDetailPref.getString(PreferenceClass.HOME_ID, "");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id_phong", Integer.parseInt(id_phong));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("CSVC", jsonObject.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, HOME_DETAIL, jsonObject, new Response.Listener<JSONObject>() {

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
                        Log.d("detail", jsonarray.toString());

                        JSONObject jsonObject1 = jsonarray.getJSONObject(0);
                        Locale localeVN = new Locale("vi", "VN");
                        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                        String fmtien_phong = currencyVN.format(Integer.parseInt(jsonObject1.optString("gia_phong")));
                        String fmtien_coc = currencyVN.format(Integer.parseInt(jsonObject1.optString("tien_coc")));
                        String fmtien_dien = String.valueOf(Float.parseFloat(jsonObject1.optString("gia_dien")) / 1000);
                        String fmtien_nuoc = String.valueOf(Float.parseFloat(jsonObject1.optString("gia_nuoc")) / 1000);
                        String ward = jsonObject1.optString("ward_name");
                        String province = jsonObject1.optString("province_name");
                        String district = jsonObject1.optString("district_name");
                        tien_dien.setText(fmtien_dien + " k/th");
                        tien_nuoc.setText(fmtien_nuoc + " k/th");
                        gia_phong.setText(fmtien_phong);
                        tien_coc.setText(fmtien_coc);
                        dia_chi.setText(ward + ", " + district + ", " + province);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Eror", error.getMessage() + "");
                Log.e("Eror", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    private void getDetail_CSVC() {
        arrayListCsvc = new ArrayList<>();
        final JSONArray[] jsonarray = new JSONArray[1];
        dealsDetailPref = this.getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        id_phong = dealsDetailPref.getString(PreferenceClass.HOME_ID, "");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id_phong", Integer.parseInt(id_phong));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("CSVC", jsonObject.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, LIST_CSVC_DETAIL, jsonObject, new Response.Listener<JSONObject>() {

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
                        jsonarray[0] = json.getJSONArray("msg");
                        max = jsonarray[0].length();
                        if (max > limit) {
                            limit = 4;
                            showmore.setVisibility(View.VISIBLE);
                        } else if (max < limit) {
                            limit = max;
                            showmore.setVisibility(View.GONE);
                        } else {
                            if (showmore.getText().equals("Thu gọn")) {
                                showmore.setVisibility(View.VISIBLE);
                            } else {
                                showmore.setVisibility(View.GONE);
                            }
                        }
                        for (int i = 0; i < limit; i++) {
                            JSONObject list_csvc = jsonarray[0].getJSONObject(i);
                            Log.d("res", list_csvc.toString());

                            CSVC csvc = new CSVC();
                            csvc.setName_csvc(list_csvc.optString("ten"));
                            csvc.setImage_csvc(list_csvc.optString("image"));
                            arrayListCsvc.add(csvc);
                        }
                        if (arrayListCsvc != null) {
                            adapterCSVSDetail = new AdapterCSVSDetail(getApplication(), arrayListCsvc);
                            gv_csvc.setAdapter(adapterCSVSDetail);
                            setGridViewHeightBasedOnChildren(gv_csvc, 4);
                        }
                        showmore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (showmore.getText().equals("Xem thêm")) {
                                    limit = limit + (max - 4);
                                    showmore.setText("Thu gọn");
                                    getDetail_CSVC();
                                    showmore.setVisibility(View.VISIBLE);
                                } else {

                                    max = jsonarray[0].length();
                                    showmore.setText("Xem thêm");
                                    limit = limit - (max - 4);
                                    getDetail_CSVC();
                                    showmore.setVisibility(View.VISIBLE);
                                }

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Eror", error.getMessage() + "");
                Log.e("Eror", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
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
        params.height = totalHeight + 20;
        gridView.setLayoutParams(params);

    }

    private void DialogImage() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.modal_image_detail_home);
        viewFlipper = (ViewFlipper) dialog.findViewById(R.id.view_image_dialog);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        dialog.show();
    }

}
