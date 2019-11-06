package com.example.yukngaji.ui.Item;

public class itemayat {
    private String nomorayat, ayat, arti;
    public itemayat(String ayat, String nomorayat,String arti){
        this.ayat=ayat;
        this.nomorayat=nomorayat;
        this.arti=arti;
    }

    public String getNomorayat() {
        return nomorayat;
    }

//    public void setNomorayat(String nomorsurat) {
//        this.nomorayat = nomorsurat;
//    }

    public String getAyat() {
        return ayat;
    }

//    public void setAyat(String ayat) {
//        this.ayat = ayat;
//    }

    public String getArti() {
        return arti;
    }

//    public void setArti(String arti) {
//        this.arti = arti;
//    }
}
