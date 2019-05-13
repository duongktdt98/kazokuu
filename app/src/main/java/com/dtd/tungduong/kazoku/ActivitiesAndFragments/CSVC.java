package com.dtd.tungduong.kazoku.ActivitiesAndFragments;

public class CSVC {
    String image_csvc;
    String name_csvc;
    String id_csvc;
    public String getId_csvc() {
        return id_csvc;
    }

    public void setId_csvc(String id_csvc) {
        this.id_csvc = id_csvc;
    }



    public String getImage_csvc() {
        return image_csvc;
    }

    public void setImage_csvc(String image_csvc) {
        this.image_csvc = image_csvc;
    }

    public String getName_csvc() {
        return name_csvc;
    }

    public void setName_csvc(String name_csvc) {
        this.name_csvc = name_csvc;
    }

    public CSVC(String image_csvc, String name_csvc,String id_csvc) {
        this.image_csvc = image_csvc;
        this.name_csvc = name_csvc;
        this.id_csvc = id_csvc;
    }

    public CSVC(){

    }
}
