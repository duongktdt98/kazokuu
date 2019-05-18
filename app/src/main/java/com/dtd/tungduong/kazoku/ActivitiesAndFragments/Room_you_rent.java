package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dtd.tungduong.kazoku.Constants.PreferenceClass;
import com.dtd.tungduong.kazoku.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.dtd.tungduong.kazoku.Constants.Config.LIST_FOR_RENT;
import static com.dtd.tungduong.kazoku.Constants.Config.LIST_YOU_RENT;


public class Room_you_rent extends Fragment {
    ListView lv_for_rent;
    ArrayList<HinhAnh> array_for_rent;
    SharedPreferences sharedPreferences;
    Adapter_for_rent adapterHome;
    RelativeLayout progressDialog,transparent_layer,div_checked;
    TextView back_icon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_you_rent, container, false);
        Anhxa(view);
        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        array_for_rent = new ArrayList<HinhAnh>();
        progressDialog.setVisibility(View.VISIBLE);
        transparent_layer.setVisibility(View.VISIBLE);
        DataArrayList();
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                UserAccount userAccount = new UserAccount();
                fragmentTransaction.replace(R.id.frame_container, userAccount);
                fragmentTransaction.commit();
            }
        });
        lv_for_rent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(PreferenceClass.HOME_ID, array_for_rent.get(position).getId());
                editor.putBoolean(PreferenceClass.IS_USER_GUEST, false);
                editor.commit();
                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeDetailFragment homeDetailFragment = new HomeDetailFragment();
                fragmentTransaction.replace(R.id.frame_container, homeDetailFragment);
                fragmentTransaction.addToBackStack("Add2");
                fragmentTransaction.commit();
            }
        });
        return view;
    }


    private void Anhxa(View view) {
        back_icon =(TextView) view.findViewById(R.id.back_list_home);
        lv_for_rent = (ListView) view.findViewById(R.id.list_for_rent);
        progressDialog = view.findViewById(R.id.progressDialog);
        transparent_layer = view.findViewById(R.id.transparent_layer);
        div_checked = view.findViewById(R.id.div_checked);

    }

    public void DataArrayList() {

        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        String id_user = sharedPreferences.getString(PreferenceClass.USER_ID,"");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id_user", id_user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("XCV", jsonObject.toString());
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, LIST_YOU_RENT, jsonObject, new Response.Listener<JSONObject>() {

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
                            JSONObject home = jsonarray.getJSONObject(i);
                            Log.d("res", home.toString());
                            HinhAnh Home = new HinhAnh();
                            Log.d("tenhome", home.optString("ten_phong"));
                            Home.setTen(home.optString("ten_phong"));
                            Home.setGia_tien(home.optString("gia_phong"));
                            Home.setLoaiphong(home.optString("loai_phong"));
                            Home.setURL_hinh(home.optString("ten_anh"));
                            Home.setNgaytao(home.optString("created"));
                            Home.setId(home.optString("id") );
                            Home.setTrang_thai(home.optString("trang_thai") );
                            Home.setKiem_duyet(home.optString("kiem_duyet") );
                            array_for_rent.add(Home);
                            String trang_thai = home.optString("trang_thai");
//                            if (trang_thai.equals("1")){
//                                div_checked.setVisibility(View.VISIBLE);
//                            } else {
//                                div_checked.setVisibility(View.GONE);
//                            }
                        }
                        if (array_for_rent != null) {
                            sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
                            adapterHome = new Adapter_for_rent(array_for_rent, getContext());
                            //adapterHome.getView(arrayImage,null);
                            lv_for_rent.setAdapter(adapterHome);
                            progressDialog.setVisibility(View.GONE);
                            transparent_layer.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Kiểm tra lại mạng", Toast.LENGTH_SHORT).show();
                progressDialog.setVisibility(View.GONE);
                transparent_layer.setVisibility(View.GONE);
                Log.d("Eror", error.getMessage() + "");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
