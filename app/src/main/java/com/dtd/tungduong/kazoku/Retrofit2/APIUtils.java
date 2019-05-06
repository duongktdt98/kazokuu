package com.dtd.tungduong.kazoku.Retrofit2;

import retrofit2.Retrofit;

public class APIUtils {
    public static final String Base_Url = "https://thanhduong.vantinviet.com/";
    public static DataClient getData(){
        // Giúp nhận và trả giữ liệu
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
