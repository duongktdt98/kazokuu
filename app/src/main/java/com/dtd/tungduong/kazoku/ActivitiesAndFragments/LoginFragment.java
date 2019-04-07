package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.dtd.tungduong.kazoku.Constants.Config.LOGIN_URL;

@SuppressLint("ValidFragment")
public class LoginFragment extends Fragment {
    SharedPreferences sharedPreferences;
    public ProgressDialog progressDialog;
    EditText edtuser;
    EditText edtpass;
    TextView txsignup;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        AnhXa(view);
        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
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
        txsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }

    private void AnhXa(View view) {
        txsignup = (TextView) view.findViewById(R.id.tv_signed_up_now);
        edtuser = (EditText) view.findViewById(R.id.ed_email);
        edtpass = (EditText) view.findViewById(R.id.ed_password);
        button = (Button) view.findViewById(R.id.btn_login);
    }

    private void GetData(final String url, final String username, final String password) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
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
                        Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("taikhoan", username);
                        editor.putString("matkhau", username);
                        editor.putBoolean(PreferenceClass.IS_LOGIN, true);
                        editor.commit();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                    } else if (code_id == 201){
                        Toast.makeText(getContext(), "Tài khoản không tồn tại, mời đăng ký!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Sai Mật khẩu!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Looix", Toast.LENGTH_SHORT).show();
                Log.d("Eror", error.getMessage() + "");
            }
        });
        Log.d("jsonArrayRequest", jsonObjectRequest.toString());
        requestQueue.add(jsonObjectRequest);
    }

}
