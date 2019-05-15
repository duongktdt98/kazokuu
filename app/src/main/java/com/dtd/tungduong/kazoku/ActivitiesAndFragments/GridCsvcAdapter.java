package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtd.tungduong.kazoku.Constants.PreferenceClass;
import com.dtd.tungduong.kazoku.R;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.List;

import static com.dtd.tungduong.kazoku.Constants.Config.imgBaseURL;
import static com.dtd.tungduong.kazoku.Constants.PreferenceClass.POST_CSVC;

public class GridCsvcAdapter extends BaseAdapter {
    private Context context;
    String csvc;
    private List<CSVC> hinhAnhList;
    SharedPreferences sharedPreferences;

    public GridCsvcAdapter(List<CSVC> hinhAnhList, Context context) {
        this.context = context;
        this.hinhAnhList = hinhAnhList;
    }

    @Override
    public int getCount() {
        return hinhAnhList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.dong_csvc, null);
        final CheckBox txtcsvc = (CheckBox) convertView.findViewById(R.id.checkboxname);
        final RelativeLayout div_check = (RelativeLayout) convertView.findViewById(R.id.div_check);
        ImageView img_csvc = (ImageView) convertView.findViewById(R.id.image_csvc);


        txtcsvc.setText(hinhAnhList.get(position).getName_csvc());
        Picasso.with(context).load(imgBaseURL + hinhAnhList.get(position).getImage_csvc()).resize(150, 150).into(img_csvc);
        sharedPreferences = context.getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PreferenceClass.POST_CSVC);
        editor.commit();
        txtcsvc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isChecked == true) {
                    Drawable background = context.getResources().getDrawable(R.drawable.vienwhite);
                    div_check.setBackground(background);
                    txtcsvc.setTextColor(Color.parseColor("#0052FF"));
                    if (csvc == "" || csvc == null){
                        csvc = hinhAnhList.get(position).getId_csvc();

                    }else {
                        csvc = csvc +" " + hinhAnhList.get(position).getId_csvc();
                    }
                } else {
                    Drawable background = context.getResources().getDrawable(R.drawable.vien);
                    div_check.setBackground(background);
                    txtcsvc.setTextColor(Color.parseColor("#000000"));
                    if (csvc != "" || csvc != null ){
                        String[] output = csvc.split("\\s");
                        String csvcUncheck = "";
                        for (int k = 0; k< output.length; k++){

                            if (!output[k].equals(hinhAnhList.get(position).getId_csvc())){
                                if (csvcUncheck == "" || csvcUncheck == null){
                                    csvcUncheck = output[k];

                                }else {
                                    csvcUncheck = csvcUncheck +" " + output[k];
                                }
                            }
                        }
                        csvc = csvcUncheck;

                    }
                }
             //   Toast.makeText(context, csvc, Toast.LENGTH_SHORT).show();
                editor.putString(PreferenceClass.POST_CSVC , csvc);
                editor.commit();
            }
        });


        return convertView;
    }
}
