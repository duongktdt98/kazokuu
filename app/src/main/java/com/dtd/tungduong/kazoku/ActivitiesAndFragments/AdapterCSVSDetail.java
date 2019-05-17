package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dtd.tungduong.kazoku.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.dtd.tungduong.kazoku.Constants.Config.imgBaseURL;

public class AdapterCSVSDetail extends BaseAdapter {
    Context context;
    ArrayList<CSVC> ListCSVC;

    public AdapterCSVSDetail(Context context, ArrayList<CSVC> listCSVC) {
        this.context = context;
        ListCSVC = listCSVC;
    }

    @Override
    public int getCount() {
        return ListCSVC.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.dong_detal_csvc, null);
        ImageView img_csvc = (ImageView) convertView.findViewById(R.id.image_csvc);
        TextView txt_csvc = (TextView) convertView.findViewById(R.id.txt_name_csvc);

        txt_csvc.setText(ListCSVC.get(position).getName_csvc());
        Picasso.with(context).load(imgBaseURL + ListCSVC.get(position).getImage_csvc()).resize(30, 30).into(img_csvc);


        return convertView;
    }
}
