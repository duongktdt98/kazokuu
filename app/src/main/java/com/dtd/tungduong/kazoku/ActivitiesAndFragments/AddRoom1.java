package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

import static com.dtd.tungduong.kazoku.Constants.Config.LIST_DISTRICT;
import static com.dtd.tungduong.kazoku.Constants.Config.LIST_PROVINCE;
import static com.dtd.tungduong.kazoku.Constants.Config.LIST_WARD;

public class AddRoom1 extends Fragment {
    SharedPreferences sharedPreferences;
    TextView txt_add_room2;
    Spinner spinnerProvince, spinnerDistrict, spinnerWard;
    String District, Province, Ward;
    EditText edt_name_room, edt_so_nha;
    ArrayList<Dia_chi> arrayProvince, arrayDistrict, arrayWard;
    ProvinceAdapter provinceAdapter;
    DistrictAdapter districtAdapter;
    WardAdapter wardAdapter;


    // EditText

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_room1, container, false);

        Anhxa(view);
        arrayProvince = new ArrayList<>();
        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        get_Province();
        txt_add_room2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (edt_so_nha.getText().length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên đường hoặc số nhà !", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (edt_name_room.getText().length() == 0){
                        String ten_phong = " " + Ward + " " + District + " " + Province;
                        editor.putString(PreferenceClass.POST_NAME, ten_phong);
                        editor.putBoolean(PreferenceClass.CHECK_NAME, true);
                    } else{
                        editor.putString(PreferenceClass.POST_NAME, edt_name_room.getText().toString());
                    }

                    editor.putString(PreferenceClass.POST_NUMBERHOME, edt_so_nha.getText().toString());
                    editor.commit();
                    final FragmentManager fragmentManager = getFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    AddRoomFragment2 add2 = new AddRoomFragment2();
                    fragmentTransaction.replace(R.id.frame_container, add2);
                    fragmentTransaction.addToBackStack("Add1");
                    fragmentTransaction.commit();
                }

            }
        });


        return view;
    }

    private void get_Province() {
        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Log.d("ur;city", LIST_PROVINCE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, LIST_PROVINCE, null, new Response.Listener<JSONObject>() {

            @SuppressLint("ResourceType")
            @Override
            public void onResponse(JSONObject response) {

                JSONObject jsonResponse = null;
                String strJson = response.toString();
                Log.d("mangdau?", response.toString());
                try {
                    jsonResponse = new JSONObject(strJson);
                    int code_id = Integer.parseInt(jsonResponse.optString("code"));
                    Log.d("code_id", code_id + "");
                    if (code_id == 200) {
                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONArray jsonarray = json.getJSONArray("msg");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject province = jsonarray.getJSONObject(i);
                            Dia_chi Province = new Dia_chi();
                            Province.setProvince(province.optString("name"));
                            Province.setId_Province(province.optString("provinceid"));
                            arrayProvince.add(Province);

                        }
                        if (arrayProvince != null) {

                            provinceAdapter = new ProvinceAdapter(arrayProvince, R.layout.dong_province, getContext());
                            spinnerProvince.setAdapter(provinceAdapter);
                            spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    Province = arrayProvince.get(position).getProvince();
                                    editor.putString(PreferenceClass.POST_PROVINCE, arrayProvince.get(position).getId_Province());
                                    editor.commit();
                                    get_District(arrayProvince.get(position).getId_Province());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    get_District("Mời chọn Quận,Huyện");
                                }
                            });
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
                Log.d("Eror", error.getMessage() + "");
                Log.e("Eror", error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void get_District(String ProvinceId) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        arrayDistrict = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("province_id", Integer.parseInt(ProvinceId));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("idpro", jsonObject.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, LIST_DISTRICT, jsonObject, new Response.Listener<JSONObject>() {

            @SuppressLint("ResourceType")
            @Override
            public void onResponse(JSONObject response) {

                JSONObject jsonResponse = null;
                String strJson = response.toString();
                Log.d("mangdau?", response.toString());
                try {
                    jsonResponse = new JSONObject(strJson);
                    int code_id = Integer.parseInt(jsonResponse.optString("code"));
                    Log.d("code_id", code_id + "");
                    if (code_id == 200) {
                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONArray jsonarray = json.getJSONArray("msg");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject province = jsonarray.getJSONObject(i);
                            Dia_chi District = new Dia_chi();
                            District.setDistrict(province.optString("name"));
                            District.setId_District(province.optString("districtid"));
                            arrayDistrict.add(District);

                        }
                        if (arrayDistrict != null) {

                            districtAdapter = new DistrictAdapter(arrayDistrict, R.layout.dong_province, getContext());
                            spinnerDistrict.setAdapter(districtAdapter);
                            spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    District = arrayDistrict.get(position).getDistrict();
                                    editor.putString(PreferenceClass.POST_DISTRICT, arrayDistrict.get(position).getId_District());
                                    editor.commit();
                                    get_Ward(arrayDistrict.get(position).getId_District());

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
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
                Log.d("Eror", error.getMessage() + "");
                Log.e("Eror", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void get_Ward(String DistrictId) {
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        arrayWard = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("district_id", Integer.parseInt(DistrictId));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("idpro", jsonObject.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, LIST_WARD, jsonObject, new Response.Listener<JSONObject>() {

            @SuppressLint("ResourceType")
            @Override
            public void onResponse(JSONObject response) {

                JSONObject jsonResponse = null;
                String strJson = response.toString();
                Log.d("mangdau?", response.toString());
                try {
                    jsonResponse = new JSONObject(strJson);
                    int code_id = Integer.parseInt(jsonResponse.optString("code"));
                    Log.d("code_id", code_id + "");
                    if (code_id == 200) {
                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONArray jsonarray = json.getJSONArray("msg");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject province = jsonarray.getJSONObject(i);
                            Dia_chi Ward = new Dia_chi();
                            Ward.setWard(province.optString("name"));
                            Ward.setId_Ward(province.optString("wardid"));
                            arrayWard.add(Ward);

                        }
                        if (arrayWard != null) {

                            wardAdapter = new WardAdapter(arrayWard, R.layout.dong_province, getContext());
                            spinnerWard.setAdapter(wardAdapter);
                            spinnerWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    Ward = arrayWard.get(position).getWard();
                                    editor.putString(PreferenceClass.POST_WARD, arrayWard.get(position).getId_Ward());
                                    editor.commit();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
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
                Log.d("Eror", error.getMessage() + "");
                Log.e("Eror", error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void Anhxa(View view) {
        txt_add_room2 = (TextView) view.findViewById(R.id.add_form2);
        edt_so_nha = (EditText) view.findViewById(R.id.edt_so_nha);
        edt_name_room = (EditText) view.findViewById(R.id.edt_name_room);
        spinnerProvince = (Spinner) view.findViewById(R.id.spinnerProvince);
        spinnerDistrict = (Spinner) view.findViewById(R.id.spinnerDistrict);
        spinnerWard = (Spinner) view.findViewById(R.id.spinnerWard);

    }


}
