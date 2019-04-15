package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dtd.tungduong.kazoku.Constants.PreferenceClass;
import com.dtd.tungduong.kazoku.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class SearchFragment extends Fragment {
    TextView btn;
    TextView txt;
    ImageView img;
    RadioButton kv_hn, kv_h, kv_hcm;
    public SharedPreferences sharedPreferences;
    GridView gv_nha_tro;
    ArrayList<HinhAnh> arrayImage;
    HomeAdapter adapterHome;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        AnhXa(view);

     //   ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayStrings);

   //     gv_nha_tro.setAdapter(arrayAdapter);
        arrayImage = new ArrayList<>();
        DataArrayList();
        adapterHome = new HomeAdapter(arrayImage, getContext());
        //adapterHome.getView(arrayImage,null);
        gv_nha_tro.setAdapter(adapterHome);

        gv_nha_tro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), arrayImage.get(position).getTen() , Toast.LENGTH_SHORT).show();

                sharedPreferences = getContext().getSharedPreferences(PreferenceClass.user, Context.MODE_PRIVATE);

                try {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(PreferenceClass.RESTAURANT_NAME, arrayImage.get(position).getTen());
                    editor.commit();
                    Fragment restaurantMenuItemsFragment = new HomeDetailFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction =  fragmentManager.beginTransaction();
                    transaction.replace(R.id.frame_search, restaurantMenuItemsFragment).commit();
                 } catch (IndexOutOfBoundsException e) {
                e.getCause();
            }


            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.khuvuc, null);
                dialog.setView(dialogView);
                kv_hn = dialogView.findViewById(R.id.radio_hn);
                kv_h = dialogView.findViewById(R.id.radio_h);
                kv_hcm = dialogView.findViewById(R.id.radio_hcm);
                kv_hn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn.setText(kv_hn.getText());
                    }
                });
                kv_h.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn.setText(kv_h.getText());
                    }
                });
                kv_hcm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn.setText(kv_hcm.getText());
                    }
                });
                dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e(TAG, "OK");
                    }
                });
                dialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.e(TAG, "Cancle");
                    }
                });
                dialog.show();
            }
        });

        return view;

    }

    public void AnhXa(View view) {
        btn = (TextView) view.findViewById(R.id.btn_search_kv);
        gv_nha_tro = (GridView) view.findViewById(R.id.gv_nha_tro);
        ImageView image_home = (ImageView) view.findViewById(R.id.image_home);
    }

    public void DataArrayList(){

       // HinhAnh HinhanhObj = new HinhAnh();

        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "Nhà trọ ở Cầu Giấy","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "Nhà trọ khu vực Bách Khoa","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "Phòng trọ khu Đại Học Quốc Gia","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "Phòng trọ sinh viên ITPlus","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 5","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 6","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 7","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 8","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 9","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 10","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 11","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 12","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 13","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 14","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 15","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 16","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 17","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 18","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 19","Hà Nội"));
        arrayImage.add(new HinhAnh("https://inhome.vn/hm_content/uploads/tin-tuc/4/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien/8-mau-thiet-ke-noi-that-phong-tro-dep-cho-sinh-vien-vo-chong-moi-cuoi-8.jpg", "anh 20","Hà Nội"));
    }


}
