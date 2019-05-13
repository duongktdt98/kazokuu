package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import static com.dtd.tungduong.kazoku.Constants.Config.LOGIN_URL;
import static com.dtd.tungduong.kazoku.Constants.PreferenceClass.USER_ID;

@SuppressLint("ValidFragment")
public class LoginFragment extends Fragment {
    SharedPreferences sharedPreferences;
    RelativeLayout progressDialog,transparent_layer;
    EditText edtuser;
    EditText edtpass;
    TextView txsignup;
    Button button;
    RelativeLayout loginFB, loginGM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        AnhXa(view);
        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        loginFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Tính năng đang trong giai đoạn phát triển", Toast.LENGTH_SHORT).show();
            }
        });
        loginGM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Tính năng đang trong giai đoạn phát triển", Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (edtuser.getText().length() == 0 || edtpass.getText().length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng điền đủ tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    String email = edtuser.getText().toString();
                    String pass = edtpass.getText().toString();
                    GetData(LOGIN_URL, email, pass);
                }


            }
        });
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        txsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp signUpfragment = new SignUp();
                fragmentTransaction.replace(R.id.frame_container, signUpfragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void AnhXa(View view) {
        loginFB = (RelativeLayout) view.findViewById(R.id.fb_div_login);
        loginGM = (RelativeLayout) view.findViewById(R.id.google_sign_in_div);
        txsignup = (TextView) view.findViewById(R.id.tv_signed_up_now);
        edtuser = (EditText) view.findViewById(R.id.ed_email);
        edtpass = (EditText) view.findViewById(R.id.ed_password);
        button = (Button) view.findViewById(R.id.btn_login);
        progressDialog = view.findViewById(R.id.progressDialog);
        transparent_layer = view.findViewById(R.id.transparent_layer);

    }

    private void GetData(final String url, final String username, final String password) {
        progressDialog.setVisibility(View.VISIBLE);
        transparent_layer.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Log.d("requestQueue", requestQueue.toString());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("jsonObj", jsonObject.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
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
                        JSONObject resultObj = json.getJSONObject("msg");
                        Log.d("resultObj", resultObj.toString());
                        Log.d("resultObj", resultObj.optString("hoten"));

                        Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
                        progressDialog.setVisibility(View.GONE);
                        transparent_layer.setVisibility(View.GONE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("taikhoan", username);
                        editor.putString(USER_ID, resultObj.optString("id"));
                        editor.putString("matkhau", username);
                        editor.putString("hoten", resultObj.optString("hoten"));
                        editor.putBoolean(PreferenceClass.IS_LOGIN, true);
                        editor.commit();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                    } else if (code_id == 201) {
                        progressDialog.setVisibility(View.GONE);
                        transparent_layer.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Tài khoản không tồn tại, mời đăng ký!", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.setVisibility(View.GONE);
                        transparent_layer.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Sai Mật khẩu!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Kiểm tra lại mạng", Toast.LENGTH_SHORT).show();
                Log.d("Eror", error.getMessage() + "");
            }
        });
        Log.d("jsonArrayRequest", jsonObjectRequest.toString());
        requestQueue.add(jsonObjectRequest);
    }

}
