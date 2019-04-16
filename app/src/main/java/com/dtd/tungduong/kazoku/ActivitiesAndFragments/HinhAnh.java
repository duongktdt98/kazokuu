package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

public class HinhAnh {
    String URL_hinh, Ten, Dia_Chi, Gia_tien;

    public HinhAnh(String URL_hinh, String ten, String DiaChi, String GiaTien) {
        this.URL_hinh = URL_hinh;
        this.Ten = ten;
        this.Dia_Chi = DiaChi;
        this.Gia_tien = GiaTien;
    }

    public String getDia_Chi() {
        return Dia_Chi;
    }

    public void setDia_Chi(String dia_Chi) {
        Dia_Chi = dia_Chi;
    }

    public String getGia_tien() {
        return Gia_tien;
    }

    public void setGia_tien(String gia_tien) {
        Gia_tien = gia_tien;
    }

    public String getURL_hinh() {
        return URL_hinh;
    }

    public void setURL_hinh(String URL_hinh) {
        this.URL_hinh = URL_hinh;
    }

    public String getDiaChi() {
        return Dia_Chi;
    }

    public void setDiaChi(String diaChi) {
        Dia_Chi = diaChi;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }
}
