package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

public class HinhAnh {
   String URL_hinh, Ten, DiaChi;

    public HinhAnh(String URL_hinh, String ten, String DiaChi) {
        this.URL_hinh = URL_hinh;
        Ten = ten;
        DiaChi = DiaChi;
    }


    public String getURL_hinh() {
        return URL_hinh;
    }

    public void setURL_hinh(String URL_hinh) {
        this.URL_hinh = URL_hinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }
}
