package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dtd.tungduong.kazoku.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dtd.tungduong.kazoku.Constants.Config.REGISTERUSER;


public class SignUp extends Fragment {
    ImageView iconback;
    EditText ed_fname, ed_sdt, ed_user, ed_password;
    Button btn_signup;
    RadioGroup radioGroupgt;
    RadioButton rd_nam, rd_nu;
    String gioitinh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        AnhXa(view);
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        iconback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment loginFragment = new LoginFragment();
                fragmentTransaction.replace(R.id.frame_login, loginFragment);
                fragmentTransaction.commit();
            }
        });
        // Lấy thông tin
        final Editable fname = ed_fname.getText();
        final Editable sdt = ed_sdt.getText();
        final Editable usernaeme = ed_user.getText();
        final Editable password = ed_password.getText();


        radioGroupgt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rd_nam:
                        gioitinh = "1";
                        break;
                    case R.id.rd_nu:
                        gioitinh = "2";
                        break;
                }
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fname.length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập Họ tên!", Toast.LENGTH_SHORT).show();
                } else if (sdt.length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập Số điện thoại!", Toast.LENGTH_SHORT).show();
                } else if (usernaeme.length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập Tài khoản!", Toast.LENGTH_SHORT).show();
                } else if (password.length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập Mật khẩu!", Toast.LENGTH_SHORT).show();
                } else if (rd_nam.isChecked() != true && rd_nu.isChecked() != true) {
                    Toast.makeText(getContext(), "Vui lòng chọn giới tính!", Toast.LENGTH_SHORT).show();
                } else {
                    showDialog(fname, sdt, usernaeme, password, gioitinh);
                }
            }
        });

        return view;
    }

    public void showDialog(final Editable fname, Editable sdt, Editable usernaeme, Editable password, final String gioitinh) {
        final Editable fnameD = fname;
        final Editable sdtD = sdt;
        final Editable usernaemeD = usernaeme;
        final Editable passwordD = password;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Đăng ký!");
        builder1.setTitle("Bạn có muốn đăng ký tài khoản?");

        builder1.setPositiveButton(
                "Có!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        registerUser(fnameD, sdtD, usernaemeD, passwordD, gioitinh);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Không!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    private void AnhXa(View view) {
        iconback = (ImageView) view.findViewById(R.id.back_icon);
        ed_fname = (EditText) view.findViewById(R.id.ed_fname);
        ed_sdt = (EditText) view.findViewById(R.id.ed_sdt);
        ed_user = (EditText) view.findViewById(R.id.ed_email);
        ed_password = (EditText) view.findViewById(R.id.ed_password);
        btn_signup = (Button) view.findViewById(R.id.btn_signup);
        radioGroupgt = (RadioGroup) view.findViewById(R.id.rd_gt);
        rd_nam = (RadioButton) view.findViewById(R.id.rd_nam);
        rd_nu = (RadioButton) view.findViewById(R.id.rd_nu);
    }

    public void registerUser(Editable fname, Editable sdt, Editable usernaeme, Editable password, String gioitinh) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Log.d("requestQueue", requestQueue.toString());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fname", fname);
            jsonObject.put("phone", sdt);
            jsonObject.put("email", usernaeme);
            jsonObject.put("password", password);
            jsonObject.put("gioitinh", gioitinh);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("jsonObj", jsonObject.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, REGISTERUSER, jsonObject, new Response.Listener<JSONObject>() {
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
                        Toast.makeText(getContext(), "Bạn đã tạo tài khoản thành công! Mời đăng nhập lại", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                    } else if (code_id == 201) {
                        Toast.makeText(getContext(), "Tài khoản đã tồn tại, mời đăng nhập!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Kiểm tra kết nối mạng và thử lại!", Toast.LENGTH_SHORT).show();
                Log.d("Eror", error.getMessage() + "");
            }
        });
        Log.d("jsonArrayRequest", jsonObjectRequest.toString());
        requestQueue.add(jsonObjectRequest);
    }

}
