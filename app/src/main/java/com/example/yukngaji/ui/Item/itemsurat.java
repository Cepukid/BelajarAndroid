package com.example.yukngaji.ui.Item;

public class itemsurat {
    private String nama;
    private String nomor;
    private String arti;

    //    private String TempatTurun;
    public itemsurat(String nama, String nomor, String arti) {
        this.nama=nama;
        this.nomor=nomor;
        this.arti=arti;
    }

    public String getArti() {
        return arti;
    }

//    public void setArti(String arti) {
//        this.arti = arti;
//    }
//
//    public String getTempatTurun() {
//        return TempatTurun;
//    }
//
//    public void setTempatTurun(String tempatTurun) {
//        TempatTurun = tempatTurun;
//    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomor() {
        return nomor;
    }

//    public void setNomor(String nomor) {
//        this.nomor = nomor;
//    }
}
