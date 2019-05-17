package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
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

import static android.support.constraint.Constraints.TAG;
import static com.dtd.tungduong.kazoku.Constants.Config.LIST_HOME;
import static com.dtd.tungduong.kazoku.Constants.Config.SEARCH_HOME;

public class SearchFragment extends Fragment {
    RelativeLayout progressDialog,transparent_layer;
    TextView btn;
    TextView txt;
    ImageView img;
    RadioButton kv_hn, kv_h, kv_hcm;
    public SharedPreferences sharedPreferences;
    GridView gv_nha_tro;
    EditText search_home;
    ArrayList<HinhAnh> arrayImage;
    HomeAdapter adapterHome;
    int a = 0;
    private test.OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        AnhXa(view);

        progressDialog.setVisibility(View.VISIBLE);
        transparent_layer.setVisibility(View.VISIBLE);





        search_home.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value = search_home.getText().toString();
                Search(value);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        DataArrayList();
        gv_nha_tro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), arrayImage.get(position).getTen(), Toast.LENGTH_SHORT).show();

                sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);

                try {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(PreferenceClass.HOME_NAME, arrayImage.get(position).getTen());
                    editor.putString(PreferenceClass.HOME_ID, arrayImage.get(position).getId());
                    editor.putString(PreferenceClass.HOME_IMG_URL, arrayImage.get(position).getURL_hinh());
                    editor.putString(PreferenceClass.HOME_ADRESS, arrayImage.get(position).getDia_Chi());
                    editor.putString(PreferenceClass.HOME_DIEN_TICH, arrayImage.get(position).getDien_tich());
                    editor.putString(PreferenceClass.HOME_PEOPLE, arrayImage.get(position).getPeople());
                    editor.putString(PreferenceClass.HOME_PRICES, arrayImage.get(position).getGia_tien());
                    editor.commit();

                } catch (IndexOutOfBoundsException e) {
                    e.getCause();
                }
                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeDetailFragment homeDetailFragment = new HomeDetailFragment();
                fragmentTransaction.replace(R.id.frame_container, homeDetailFragment);
                fragmentTransaction.addToBackStack("Add2");
                fragmentTransaction.commit();


            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.khuvuc, null);
                dialog.setView(dialogView);
                kv_hn = dialogView.findViewById(R.id.radio_hn);
                kv_h = dialogView.findViewById(R.id.radio_h);
                kv_hcm = dialogView.findViewById(R.id.radio_hcm);
                kv_hn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn.setText(kv_hn.getText());
                    }
                });
                kv_h.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn.setText(kv_h.getText());
                    }
                });
                kv_hcm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn.setText(kv_hcm.getText());
                    }
                });
                dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e(TAG, "OK");
                    }
                });
                dialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e(TAG, "Cancle");
                    }
                });
                dialog.show();
            }
        });

        return view;

    }

    private void Search(String value) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        arrayImage = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("key", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("keysearch", jsonObject.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SEARCH_HOME, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                JSONObject jsonResponse = null;
                String strJson = response.toString();
                Log.d("search", response.toString());
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
                            Log.d("ten", home.optString("ten_phong"));
                            Home.setTen(home.optString("ten_phong"));
                            Home.setGia_tien(home.optString("gia_phong"));
                            Home.setDia_Chi(home.optString("ward_name"));
                            Home.setDien_tich(home.optString("kich_thuoc"));
                            Home.setURL_hinh(home.optString("ten_anh"));
                            Home.setPeople(home.optString("so_nguoi"));
                            Home.setId(home.optString("id") );
                            arrayImage.add(Home);

                        }
                        if (arrayImage != null) {
                            adapterHome = new HomeAdapter(arrayImage, getContext());
                            //adapterHome.getView(arrayImage,null);
                            progressDialog.setVisibility(View.GONE);
                            transparent_layer.setVisibility(View.GONE);

                            adapterHome.notifyDataSetChanged();
                            gv_nha_tro.invalidate();
                            gv_nha_tro.setAdapter(new HomeAdapter(arrayImage, getContext()));

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.setVisibility(View.GONE);
                transparent_layer.setVisibility(View.GONE);
                Log.d("Eror", error.getMessage() + "");
                Log.e("Eror", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void AnhXa(View view) {
        btn = (TextView) view.findViewById(R.id.btn_search_kv);
        search_home = (EditText) view.findViewById(R.id.search_home);
        gv_nha_tro = (GridView) view.findViewById(R.id.gv_nha_tro);
        ImageView image_home = (ImageView) view.findViewById(R.id.image_home);
        progressDialog = view.findViewById(R.id.progressDialog);
        transparent_layer = view.findViewById(R.id.transparent_layer);
    }

    public void DataArrayList() {
        arrayImage = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, LIST_HOME, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                JSONObject jsonResponse = null;
                String strJson = response.toString();
                try {
                    jsonResponse = new JSONObject(strJson);
                    int code_id = Integer.parseInt(jsonResponse.optString("code"));
                    if (code_id == 200) {
                        JSONObject json = new JSONObject(jsonResponse.toString());
                        JSONArray jsonarray = json.getJSONArray("msg");
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject home = jsonarray.getJSONObject(i);
                            HinhAnh Home = new HinhAnh();
                            Home.setTen(home.optString("ten_phong"));
                            Home.setGia_tien(home.optString("gia_phong"));
                            Home.setDia_Chi(home.optString("ward_name"));
                            Home.setDien_tich(home.optString("kich_thuoc"));
                            Home.setURL_hinh(home.optString("ten_anh"));
                            Home.setPeople(home.optString("so_nguoi"));
                            Home.setId(home.optString("id") );
                            arrayImage.add(Home);

                        }
                        if (arrayImage != null) {
                            adapterHome = new HomeAdapter(arrayImage, getContext());
                            //adapterHome.getView(arrayImage,null);
                            progressDialog.setVisibility(View.GONE);
                            transparent_layer.setVisibility(View.GONE);
                            gv_nha_tro.setAdapter(adapterHome);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.setVisibility(View.GONE);
                transparent_layer.setVisibility(View.GONE);
                Log.d("Eror", error.getMessage() + "");
                Log.e("Eror", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
