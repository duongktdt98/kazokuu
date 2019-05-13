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

public class HomeDetailAdapter extends BaseAdapter {
    private List<HinhAnh> hinhAnhList;
    Context context;

    public HomeDetailAdapter(List<HinhAnh> hinhAnhList, Context context) {
        this.hinhAnhList = hinhAnhList;
        this.context = context;
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
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.fragment_home_detail,null);
       // TextView txtname  =(TextView) view.findViewById(R.id.txt_name_home);
        ImageView imageView = (ImageView) view.findViewById(R.id.img_detail);
        //txtname.setText(hinhAnhList.get(position).getTen());
       Picasso.with(context).load(hinhAnhList.get(position).getURL_hinh()).resize(150, 150).into(imageView);
//        Glide.with(context).load(hinhAnhList.get(position)
//                .getURL_hinh())
////                .placeholder(R.drawable.load)
////                .error(R.drawable.error)
//                .into(imageView);
        return view;
    }

    public void getView(ArrayList<HinhAnh> arrayImage, Context context) {

    }
}
