package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

public class HinhAnh {
    String URL_hinh, Ten, Dia_Chi, Gia_tien, Id;

    public HinhAnh(String URL_hinh,String Ten,String Dia_Chi,String Gia_tien,String Id) {
        this.URL_hinh = URL_hinh;
        this.Ten = Ten;
        this.Dia_Chi = Dia_Chi;
        this.Gia_tien = Gia_tien;
        this.Id = Id;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public HinhAnh(){

    }

}
