package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.dtd.tungduong.kazoku.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.dtd.tungduong.kazoku.Constants.Config.LIST_FOR_RENT;
import static com.dtd.tungduong.kazoku.Constants.Config.LIST_HOME;

public class Room_for_rent extends Fragment {
    ListView lv_for_rent;
    ArrayList<HinhAnh> array_for_rent;
    Adapter_for_rent adapterHome;
    RelativeLayout progressDialog,transparent_layer;
    TextView back_icon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_for_rent, container, false);
        Anhxa(view);

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
        return view;
    }

    private void Anhxa(View view) {
        back_icon =(TextView) view.findViewById(R.id.back_list_home);
        lv_for_rent = (ListView) view.findViewById(R.id.list_for_rent);
        progressDialog = view.findViewById(R.id.progressDialog);
        transparent_layer = view.findViewById(R.id.transparent_layer);

    }

    public void DataArrayList() {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, LIST_FOR_RENT, null, new Response.Listener<JSONObject>() {

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
                            Home.setId(home.optString("id") + "/");
                            array_for_rent.add(Home);

                        }
                        if (array_for_rent != null) {
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
