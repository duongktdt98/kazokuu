package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
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
import static com.dtd.tungduong.kazoku.Constants.Config.SET_TRANG_THAI;
import static com.dtd.tungduong.kazoku.Constants.Config.imgBaseURL;

public class HomeDetailFragment extends Fragment {
    public SharedPreferences dealsDetailPref;
    String name, url, dientich, gia_tien, songuoi, id_phong,  id_user, trang_thai;
    TextView name_detail, back_list,mo_ta, tien_coc, booking_now, dien_tich, tien_dien, tien_nuoc, adress, so_nguoi, gia_phong, dia_chi;
    RelativeLayout div_dat_phong,div_trang_thai;
    ImageView imageView;
    Switch switch_hien_thi;
    ViewFlipper viewFlipper;
    ArrayList<CSVC> arrayListCsvc;
    AdapterCSVSDetail adapterCSVSDetail;
    GridView gv_csvc;
    int limit = 4;
    TextView showmore;
    int max = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_detail, container, false);
        AnhXa(view);

        dealsDetailPref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        boolean getUserGuest = dealsDetailPref.getBoolean(PreferenceClass.IS_USER_GUEST, false);
        url = dealsDetailPref.getString(PreferenceClass.HOME_IMG_URL, "");
        id_user = dealsDetailPref.getString(PreferenceClass.USER_ID, "");
        if (getUserGuest){
                div_dat_phong.setVisibility(View.VISIBLE);
        } else {
                div_dat_phong.setVisibility(View.GONE);
        }




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogImage();
            }
        });
        back_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SearchFragment searchFragment = new SearchFragment();
                fragmentTransaction.replace(R.id.frame_container, searchFragment);
                fragmentTransaction.commit();
            }
        });


        booking_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Booking_home_Frame booking_home_frame = new Booking_home_Frame();
                fragmentTransaction.replace(R.id.frame_container, booking_home_frame);
                fragmentTransaction.commit();
            }
        });
        getDetail();
        getDetail_CSVC();

        return view;
    }

    private void AnhXa(View view) {
        gv_csvc = (GridView) view.findViewById(R.id.gv_csvc);
        imageView = (ImageView) view.findViewById(R.id.img_detail);
        name_detail = (TextView) view.findViewById(R.id.txt_name_home_detail);
        showmore = (TextView) view.findViewById(R.id.show_more);
        adress = (TextView) view.findViewById(R.id.txt_dientich_room);
        back_list = (TextView) view.findViewById(R.id.back_list_home);
        tien_coc = (TextView) view.findViewById(R.id.txt_tien_coc);
        so_nguoi = (TextView) view.findViewById(R.id.txt_people_room);
        dien_tich = (TextView) view.findViewById(R.id.txt_dientich_room);
        booking_now = (TextView) view.findViewById(R.id.booking);
        tien_dien = (TextView) view.findViewById(R.id.txt_dien);
        tien_nuoc = (TextView) view.findViewById(R.id.txt_nuoc);
        gia_phong = (TextView) view.findViewById(R.id.tong_tien);
        dia_chi = (TextView) view.findViewById(R.id.txt_dia_chi);
        mo_ta = (TextView) view.findViewById(R.id.txt_mo_ta);
        div_dat_phong = (RelativeLayout) view.findViewById(R.id.div_dat_phong);
        div_trang_thai = (RelativeLayout) view.findViewById(R.id.div_trang_thai);
        switch_hien_thi = (Switch) view.findViewById(R.id.hien_thi);
    }

    private void getDetail() {
        dealsDetailPref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        id_phong = dealsDetailPref.getString(PreferenceClass.HOME_ID, "");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                         name = jsonObject1.optString("ten_phong");
                         dientich = jsonObject1.optString("kich_thuoc");
                         songuoi = jsonObject1.optString("so_nguoi");
                        String ward = jsonObject1.optString("ward_name");
                         trang_thai = jsonObject1.optString("trang_thai");
                        String province = jsonObject1.optString("province_name");
                        String district = jsonObject1.optString("district_name");
                        String is_userr = jsonObject1.optString("id_user");
                        String mota = jsonObject1.optString("mota");
                        tien_dien.setText(fmtien_dien + " k/1KW");
                        tien_nuoc.setText(fmtien_nuoc + " k/m³");
                        gia_phong.setText(fmtien_phong);
                        tien_coc.setText(fmtien_coc);
                        if (mota.equals("") || mota.equals(null)){
                            mo_ta.setText("Phòng hiện không có mô tả nào !");
                        } else {
                            mo_ta.setText(mota);
                        }
                        dia_chi.setText(ward + ", " + district + ", " + province);

                        name_detail.setText(name);
                        adress.setText(dientich);
                        so_nguoi.setText(songuoi);
                        Picasso.with(getContext()).load(imgBaseURL + url).resize(350, 300).into(imageView);
                        if (trang_thai.equals("1")){
                            switch_hien_thi.setChecked(true);
                        }else {
                            switch_hien_thi.setChecked(false);
                        }
                        switch_hien_thi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked){
                                    trang_thai = "1";
                                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("id_phong", Integer.parseInt(id_phong));
                                        jsonObject.put("trang_thai", Integer.parseInt(trang_thai));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("trang_thai", jsonObject.toString());
                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SET_TRANG_THAI, jsonObject, new Response.Listener<JSONObject>() {

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
                                                    Toast.makeText(getContext(), "Bạn cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();

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
                                } else {
                                    trang_thai = "0";
                                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("id_phong", Integer.parseInt(id_phong));
                                        jsonObject.put("trang_thai", Integer.parseInt(trang_thai));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("trang_thai", jsonObject.toString());
                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SET_TRANG_THAI, jsonObject, new Response.Listener<JSONObject>() {

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
                                                    Toast.makeText(getContext(), "Bạn cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();

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
                            }
                        });
                        if (!id_user.equals(is_userr)){
                            div_dat_phong.setVisibility(View.VISIBLE);
                            div_trang_thai.setVisibility(View.GONE);

                        } else {
                            div_dat_phong.setVisibility(View.GONE);
                            div_trang_thai.setVisibility(View.VISIBLE);
                        }
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
        dealsDetailPref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        id_phong = dealsDetailPref.getString(PreferenceClass.HOME_ID, "");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                            adapterCSVSDetail = new AdapterCSVSDetail(getContext(), arrayListCsvc);
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
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.modal_image_detail_home);
        viewFlipper = (ViewFlipper) dialog.findViewById(R.id.view_image_dialog);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        dialog.show();
    }


}
