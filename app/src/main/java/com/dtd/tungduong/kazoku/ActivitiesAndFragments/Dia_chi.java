package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

public class Dia_chi {
    String Id_Province, Province, Type_Province, Id_Ward, Ward, Type_Ward, Id_District, District, Type_District;

    public Dia_chi(String Id_Province, String Province, String Type_Province, String Id_Ward, String Ward, String Type_Ward, String Id_District, String District, String Type_District) {
        this.Id_Province = Id_Province;
        this.Province = Province;
        this.Type_Province = Type_Province;
        this.Id_Ward = Id_Ward;
        this.Ward = Ward;
        this.Type_Ward = Type_Ward;
        this.Id_District = Id_District;
        this.District = District;
        this.Type_District = Type_District;
    }


    public String getId_Province() {
        return Id_Province;
    }

    public void setId_Province(String id_Province) {
        Id_Province = id_Province;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getType_Province() {
        return Type_Province;
    }

    public void setType_Province(String type_Province) {
        Type_Province = type_Province;
    }

    public String getId_Ward() {
        return Id_Ward;
    }

    public void setId_Ward(String id_Ward) {
        Id_Ward = id_Ward;
    }

    public String getWard() {
        return Ward;
    }

    public void setWard(String ward) {
        Ward = ward;
    }

    public String getType_Ward() {
        return Type_Ward;
    }

    public void setType_Ward(String type_Ward) {
        Type_Ward = type_Ward;
    }

    public String getId_District() {
        return Id_District;
    }

    public void setId_District(String id_District) {
        Id_District = id_District;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getType_District() {
        return Type_District;
    }

    public void setType_District(String type_District) {
        Type_District = type_District;
    }

    public Dia_chi() {

    }
}
