package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

public class HinhAnh {
   String URL_hinh, Ten;

    public HinhAnh(String URL_hinh, String ten) {
        this.URL_hinh = URL_hinh;
        Ten = ten;
    }

    public HinhAnh(String s){

    }

    public String getURL_hinh() {
        return URL_hinh;
    }

    public void setURL_hinh(String URL_hinh) {
        this.URL_hinh = URL_hinh;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }
}
