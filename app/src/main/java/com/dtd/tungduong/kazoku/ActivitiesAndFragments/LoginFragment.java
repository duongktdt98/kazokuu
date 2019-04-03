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
import static com.dtd.tungduong.kazoku.Constants.PreferenceClass.IS_LOGIN;

@SuppressLint("ValidFragment")
public class LoginFragment extends Fragment {
    SharedPreferences sharedPreferences;
    String user;
    String pass;
    ArrayList<Account> arrayAccount;
    private int Status;
    private static Object JsonArrayRequest = "";
    public ProgressDialog progressDialog;
    EditText UserEditText;
    EditText PassEditText;
    Button button;

    public class Account {
        private int Id;
        private String User;
        private String Password;

        public Account(int id, String user, String password) {
            Id = id;
            User = user;
            Password = password;
        }

        public int getId() {
            return Id;
        }

        public String getUser() {
            return User;
        }

        public String getPassword() {
            return Password;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        AnhXa(view);
        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (UserEditText.getText().length() == 0 || PassEditText.getText().length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng điền đủ tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    String email = UserEditText.getText().toString();
                    String pass = PassEditText.getText().toString();
                    GetData(LOGIN_URL, email, pass);
                }


            }
        });


        return view;
    }

    private void AnhXa(View view){
        UserEditText = (EditText) view.findViewById(R.id.ed_email);
        PassEditText = (EditText) view.findViewById(R.id.ed_password);
        button = (Button) view.findViewById(R.id.btn_login);
    }
    private void GetData(final String url , final String username, final String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Log.d("requestQueue", requestQueue.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String json = null;
                try {
                    String user;
                    String pass;
                    JSONArray events = response.getJSONArray("contacts");
                    Log.d("events", events.toString());
                    for (int i = 0; i < events.length(); i++) {
                        JSONObject data = events.getJSONObject(i);
                        user = data.getString("ten_taikhoan");
                        int id = data.getInt("id");
                        pass = data.getString("matkhau");
                        if( user.equals(username) && pass.equals(password)){
                            Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("taikhoan", username);
                            editor.putString("matkhau", username);
                            editor.putBoolean(PreferenceClass.IS_LOGIN , true);
                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        } else {
                            Toast.makeText(getContext(), "Đăng nhập sai, mời đăng nhập lại", Toast.LENGTH_SHORT).show();
                        }
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
