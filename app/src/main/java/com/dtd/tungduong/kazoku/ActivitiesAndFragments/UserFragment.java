package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.app.ProgressDialog;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dtd.tungduong.kazoku.R;

import org.json.JSONArray;

import java.lang.reflect.Method;

public class UserFragment extends Fragment {
    String urlGetData = "http://192.168.1.198/doan.local.com/Config.php";
    private static Object JsonArrayRequest = "";
    public ProgressDialog progressDialog;
    EditText UserEditText;
    EditText PassEditText;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_user, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        UserEditText = (EditText) view.findViewById(R.id.ed_email);
        PassEditText = (EditText) view.findViewById(R.id.ed_password);
        button  = (Button) view.findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GetData(urlGetData);
            }
        });


        return view;
    }


}
