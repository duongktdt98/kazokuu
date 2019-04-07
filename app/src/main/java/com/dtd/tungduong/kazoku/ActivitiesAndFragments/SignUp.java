package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dtd.tungduong.kazoku.R;

import static com.dtd.tungduong.kazoku.Constants.Config.LOGIN_URL;


public class SignUp extends Fragment {
    ImageView iconback;
    EditText ed_fname, ed_lname, ed_user, ed_password;
    Button btn_signup;

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
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_fname.getText().length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập Họ!", Toast.LENGTH_SHORT).show();
                } else if (ed_lname.getText().length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập Tên!", Toast.LENGTH_SHORT).show();
                } else if (ed_user.getText().length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập Tài khoản!", Toast.LENGTH_SHORT).show();
                } else if (ed_password.getText().length() == 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập Mật khẩu!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void AnhXa(View view) {
        iconback = (ImageView) view.findViewById(R.id.back_icon);
        ed_fname = (EditText) view.findViewById(R.id.ed_fname);
        ed_lname = (EditText) view.findViewById(R.id.ed_lname);
        ed_user = (EditText) view.findViewById(R.id.ed_email);
        ed_password = (EditText) view.findViewById(R.id.ed_password);
        btn_signup = (Button) view.findViewById(R.id.btn_signup);

    }

}
