package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.content.Context.MODE_PRIVATE;
import static com.dtd.tungduong.kazoku.Constants.Config.GET_USER;
import static com.dtd.tungduong.kazoku.Constants.Config.LIST_HOME;
import static com.dtd.tungduong.kazoku.Constants.PreferenceClass.USER_ID;

public class UserAccount extends Fragment {
    //public boolean LOGIN = false;
    TextView txthoten;

    SharedPreferences sharedPreferences;
    private RelativeLayout activity_user__account, fragment_not_login;
    RelativeLayout div_log_out, hotline_div, user_name, room_you_rent, room_for_rent;
    String hoten, total_coin, id_user;
    TextView coin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user__account, container, false);
      Anhxa(view);
        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, MODE_PRIVATE);
        id_user = sharedPreferences.getString(USER_ID, "");

        JSONObject jsonObject2 = new JSONObject();
        try {
            jsonObject2.put("id_user", id_user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("jsonobj", jsonObject2.toString() );
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, GET_USER, jsonObject2, new Response.Listener<JSONObject>() {

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

                        Log.d("daylacoin", total_coin);
                        if (total_coin.equals("")|| total_coin.equals("0")){
                            coin.setText("0");
                        } else {
                            coin.setText(total_coin);
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
        // Inflate the layout for this fragment

        // checkLogInSession();
        hoten = sharedPreferences.getString("hoten", "");

        txthoten.setText(hoten);

        div_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLogout();
            }
        });

        room_for_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Room_for_rent room_for_rent = new Room_for_rent();
                fragmentTransaction.replace(R.id.frame_container, room_for_rent);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        room_you_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentManager fragmentManager = getFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Room_you_rent room_you_rent = new Room_you_rent();
                fragmentTransaction.replace(R.id.frame_container, room_you_rent);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Init(view);
        return view;
    }

    private void Anhxa(View view) {
        div_log_out = (RelativeLayout) view.findViewById(R.id.log_out_div);
        hotline_div = (RelativeLayout) view.findViewById(R.id.hotline_div);
        user_name = (RelativeLayout) view.findViewById(R.id.user_name_div);
        txthoten = (TextView) view.findViewById(R.id.user_name);
        coin = (TextView) view.findViewById(R.id.total_coin);
        room_for_rent = (RelativeLayout) view.findViewById(R.id.room_for_rent);
        room_you_rent = (RelativeLayout) view.findViewById(R.id.room_you_rent);
    }

    public void onResume() {
        super.onResume();
        // checkLogInSession();
    }

    public void Init(View view) {
        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Chúc bạn "+ hoten + " ngày mới vui vẻ!", Toast.LENGTH_SHORT).show();
            }
        });

        hotline_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    public void showDialog() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Gọi cho chúng tôi");
        builder1.setTitle("Nhấn Gọi để nhận hỗ trợ từ chúng tôi.");

        builder1.setPositiveButton(
                "Gọi",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onCall();
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

    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {


            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + getContext().getString(R.string.uan))));
        }
    }

    public void showDialogLogout() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Bạn có chắc chắn muốn đăng xuất?");
        builder1.setTitle("Đăng xuất?");

        builder1.setPositiveButton(
                "Đăng xuất",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("taikhoan", "");
                        editor.putString("matkhau", "");
                        editor.putString("hoten", "");
//                        editor.putString(PreferenceClass.pre_last, "");
//                        editor.putString(PreferenceClass.pre_contact, "");
//                        editor.putString(PreferenceClass.pre_user_id, "");
//                        editor.putString(PreferenceClass.ADMIN_USER_ID, "");
                        editor.putBoolean(PreferenceClass.IS_LOGIN, false);
                        editor.commit();

                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
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
