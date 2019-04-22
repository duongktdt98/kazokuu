package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dtd.tungduong.kazoku.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.dtd.tungduong.kazoku.Constants.Config.imgBaseURL;

public class HomeAdapter extends BaseAdapter {
    private List<HinhAnh> hinhAnhList;
    Context context;

    public HomeAdapter(List<HinhAnh> hinhAnhList, Context context) {
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
        view = inflater.inflate(R.layout.row_home_icon,null);
        TextView txtname  =(TextView) view.findViewById(R.id.txt_name_home);
        TextView diachi  =(TextView) view.findViewById(R.id.diachi);
        TextView price  =(TextView) view.findViewById(R.id.prices_total);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_home);
        txtname.setText(hinhAnhList.get(position).getTen());
        diachi.setText(hinhAnhList.get(position).getDia_Chi());
        price.setText(hinhAnhList.get(position).getGia_tien());
       Picasso.with(context).load(imgBaseURL +hinhAnhList.get(position).getURL_hinh()).resize(150, 150).into(imageView);
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
