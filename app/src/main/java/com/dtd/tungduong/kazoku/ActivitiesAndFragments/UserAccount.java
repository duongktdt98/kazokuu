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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dtd.tungduong.kazoku.Constants.PreferenceClass;
import com.dtd.tungduong.kazoku.R;

import static android.content.Context.MODE_PRIVATE;

public class UserAccount extends Fragment {
    //public boolean LOGIN = false;
    SharedPreferences sharedPreferences;
    private RelativeLayout activity_user__account, fragment_not_login;
    RelativeLayout div_log_out,hotline_div;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user__account, container, false);
        div_log_out = (RelativeLayout) view.findViewById(R.id.log_out_div);
        hotline_div = (RelativeLayout) view.findViewById(R.id.hotline_div);
        // Inflate the layout for this fragment
        sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, MODE_PRIVATE);
        // checkLogInSession();
        div_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLogout();
            }
        });
        Init(view);
        return view;
    }

    public void onResume() {
        super.onResume();
        // checkLogInSession();
    }

    public void Init(View view){
        hotline_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }
    public void showDialog(){

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


            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+getContext().getString(R.string.uan))));
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
                        editor.putString(PreferenceClass.pre_email, "");
                        editor.putString(PreferenceClass.pre_pass, "");
                        editor.putString(PreferenceClass.pre_first, "");
                        editor.putString(PreferenceClass.pre_last, "");
                        editor.putString(PreferenceClass.pre_contact, "");
                        editor.putString(PreferenceClass.pre_user_id, "");
                        editor.putString(PreferenceClass.ADMIN_USER_ID, "");
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
//    private void checkLogInSession() {
//        boolean getLoINSession = sharedPreferences.getBoolean(PreferenceClass.IS_LOGIN, false);
//        if (getLoINSession) {
//            UserAccount fragmentChild = new UserAccount();
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.replace(R.id.frame_account, fragmentChild);
//            transaction.commit();
//        } else {
//            LoginFragment fragmentChild = new LoginFragment();
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.replace(R.id.frame_login, fragmentChild);
//            transaction.commit();
//        }
//
//    }
}
