package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.dtd.tungduong.kazoku.Constants.Config.SEND_REQUEST;
import static com.dtd.tungduong.kazoku.Constants.PreferenceClass.DATE_SHOW;
import static com.dtd.tungduong.kazoku.Constants.PreferenceClass.USER_ID;

public class Booking_home_Frame extends Fragment {
    EditText edt_date, current_day, edt_sdt, edit_people_rent;
    TextView booking_now;
    String sdt_dangky, ngay_dat, ngay_ht, gioi_tinh;
    Integer id_user, so_nguoi, id_home;
    RadioButton r_nam, r_nu, r_both;
    public SharedPreferences dealsDetailPref;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_home__frame, container, false);
        Anhxa(view);
        dealsDetailPref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        boolean getLoginSession2 = dealsDetailPref.getBoolean(PreferenceClass.IS_LOGIN, false);
        if (!getLoginSession2) {
            Toast.makeText(getContext(), "Mời bạn đăng nhập trước khi thuê phòng!", Toast.LENGTH_SHORT).show();
            final FragmentManager fragmentManager = getFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LoginFragment loginFragment = new LoginFragment();
            fragmentTransaction.replace(R.id.frame_container, loginFragment);
            fragmentTransaction.addToBackStack("Add1");
            fragmentTransaction.commit();
        }
        edt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });
        booking_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    if (edt_sdt.getText().length() == 0 || edit_people_rent.getText().length() == 0 ||
                            current_day.getText().length() == 0 || edt_date.getText().length() == 0) {
                        Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else if (r_nam.isChecked() != true && r_nu.isChecked() != true && r_both.isChecked() != true) {
                        Toast.makeText(getContext(), "Vui lòng chọn giới tính!", Toast.LENGTH_SHORT).show();
                    } else {
                        Booking_now();

                    }
            }

            private void Booking_now() {
                dealsDetailPref = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
                id_user = Integer.parseInt(dealsDetailPref.getString(PreferenceClass.USER_ID, ""));
                id_home = Integer.parseInt(dealsDetailPref.getString(PreferenceClass.HOME_ID, ""));

                //     ngay_dat = dealsDetailPref.getString(PreferenceClass.DATE_SHOW, "");
                sdt_dangky = edt_sdt.getText().toString();
                so_nguoi = Integer.parseInt(edit_people_rent.getText().toString());

                if (r_nam.isChecked() == true) {
                    gioi_tinh = "1";
                } else if (r_nu.isChecked() == true) {
                    gioi_tinh = "0";
                } else {
                    gioi_tinh = "2";
                }


                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                Log.d("requestQueue", requestQueue.toString());
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id_user", id_user);
                    jsonObject.put("id_home", id_home);
                    jsonObject.put("sdt", sdt_dangky);
                    jsonObject.put("people", so_nguoi);
                    jsonObject.put("gioi_tinh", gioi_tinh);
                    jsonObject.put("date_booking", ngay_dat);
                    jsonObject.put("date_current", ngay_ht);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("jsonObj", jsonObject.toString());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SEND_REQUEST, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonResponse = null;
                        String strJson = response.toString();
                        Log.d("response", response.toString());
                        try {
                            jsonResponse = new JSONObject(strJson);
                            int code_id = Integer.parseInt(jsonResponse.optString("code"));
                            if (code_id == 200) {
                                Toast.makeText(getContext(), "Bạn đã gửi yêu cầu đặt phòng thành công! Chúng tôi sẽ sớm liên lạc với bạn! Chúc bạn sức khỏe ^^", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getContext(), MainActivity.class));
                                getActivity().finish();
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
                    }
                });
                Log.d("jsonArrayRequest", jsonObjectRequest.toString());
                requestQueue.add(jsonObjectRequest);
            }
        });
        return view;
    }

    private void ChonNgay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                ngay_ht = simpleDateFormat.format(calendar.getTime());
                current_day.setText(simpleDateFormat.format(calendar.getTime()));
                calendar.set(year, month, dayOfMonth);

                edt_date.setText(simpleDateFormat.format(calendar.getTime()));
                ngay_dat = simpleDateFormat.format(calendar.getTime());
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void Anhxa(View view) {
        edt_date = (EditText) view.findViewById(R.id.edt_chon_ngay);
        current_day = (EditText) view.findViewById(R.id.edt_current_day);
        edit_people_rent = (EditText) view.findViewById(R.id.edit_people_rent);
        edt_sdt = (EditText) view.findViewById(R.id.edt_sdt);
        booking_now = (TextView) view.findViewById(R.id.booking_now);
        r_nam = (RadioButton) view.findViewById(R.id.r_nam);
        r_both = (RadioButton) view.findViewById(R.id.r_both);
        r_nu = (RadioButton) view.findViewById(R.id.r_nu);
    }

}
