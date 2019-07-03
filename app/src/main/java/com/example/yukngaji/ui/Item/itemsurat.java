package com.example.yukngaji.ui.Item;

public class itemsurat {
    String nama;
    String nomor;
    String arti;
    String TempatTurun;
    public itemsurat(String nama, String nomor,String arti,String TempatTurun){
        this.nama=nama;
        this.nomor=nomor;
        this.arti=arti;
        this.TempatTurun=TempatTurun;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }

    public String getTempatTurun() {
        return TempatTurun;
    }

    public void setTempatTurun(String tempatTurun) {
        TempatTurun = tempatTurun;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }
}
