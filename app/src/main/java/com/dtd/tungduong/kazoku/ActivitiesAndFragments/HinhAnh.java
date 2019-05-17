package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

public class HinhAnh {
    String URL_hinh, Ten, Dia_Chi, Gia_tien, Id, Dien_tich, People, Loaiphong, Ngaytao, Trang_thai;

    public String getLoaiphong() {
        return Loaiphong;
    }

    public void setLoaiphong(String loaiphong) {
        Loaiphong = loaiphong;
    }

    public String getNgaytao() {
        return Ngaytao;
    }

    public void setNgaytao(String ngaytao) {
        Ngaytao = ngaytao;
    }



    public HinhAnh(String URL_hinh, String Ten, String Dia_Chi, String Gia_tien, String Id, String dientich, String People, String Loaiphong, String Ngaytao, String Trang_thai) {
        this.URL_hinh = URL_hinh;
        this.Ten = Ten;
        this.Dia_Chi = Dia_Chi;
        this.Gia_tien = Gia_tien;
        this.Id = Id;
        this.Dien_tich = dientich;
        this.People = People;
        this.Loaiphong = Loaiphong;
        this.Ngaytao = Ngaytao;
        this.Trang_thai = Trang_thai;
    }

    public String getTrang_thai() {
        return Trang_thai;
    }

    public void setTrang_thai(String trang_thai) {
        Trang_thai = trang_thai;
    }
    public String getPeople() {
        return People;
    }

    public void setPeople(String people) {
        People = people;
    }

    public String getDien_tich() {
        return Dien_tich;
    }

    public void setDien_tich(String dien_tich) {
        Dien_tich = dien_tich;
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
