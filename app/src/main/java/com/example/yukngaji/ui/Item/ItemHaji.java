package com.example.yukngaji.ui.Item;

import java.io.Serializable;

public class ItemHaji implements Serializable {
    private String Nama,Penjelasan,Gambar;

    public void setNama(String nama) {
        Nama = nama;
    }

    public void setPenjelasan(String penjelasan) {
        Penjelasan = penjelasan;
    }

    public void setGambar(String gambar) {
        Gambar = gambar;
    }

    public String getNama() {
        return Nama;
    }

    public String getPenjelasan() {
        return Penjelasan;
    }

    public String getGambar() {
        return Gambar;
    }
}
