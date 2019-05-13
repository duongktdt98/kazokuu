package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dtd.tungduong.kazoku.R;

import java.util.List;

public class ProvinceAdapter extends BaseAdapter {
    private List<Dia_chi> ListProvince;
    int mylayout;
    Context context;

    public ProvinceAdapter(List<Dia_chi> listProvince, int mylayout, Context context) {
        ListProvince = listProvince;
        this.mylayout = mylayout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ListProvince.size();
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
        convertView = inflater.inflate(R.layout.dong_province,null);
        TextView txt_province = (TextView) convertView.findViewById(R.id.txt_province);

        txt_province.setText(ListProvince.get(position).getProvince());

        return convertView;
    }
}
