package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Transformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dtd.tungduong.kazoku.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        TextView people  =(TextView) view.findViewById(R.id.txt_people_room);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_home);
        txtname.setText(hinhAnhList.get(position).getTen());
        diachi.setText(hinhAnhList.get(position).getDia_Chi());
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String fmtien_phong = currencyVN.format(Integer.parseInt(hinhAnhList.get(position).getGia_tien()));
        String tienphong = String.valueOf(Float.parseFloat(hinhAnhList.get(position).getGia_tien()) / 1000000);
        price.setText(tienphong + " Tr");
       Picasso.with(context).load(imgBaseURL +hinhAnhList.get(position).getURL_hinh()).resize(150, 150).into(imageView);
//        Glide.with(context).load(hinhAnhList.get(position)
//                .getURL_hinh())
////                .placeholder(R.drawable.load)
////                .error(R.drawable.error)
//                .into(imageView);
        return view;
    }
    public class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap,
                    BitmapShader.TileMode.CLAMP,  Shader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
    public void getView(ArrayList<HinhAnh> arrayImage, Context context) {

    }
}
