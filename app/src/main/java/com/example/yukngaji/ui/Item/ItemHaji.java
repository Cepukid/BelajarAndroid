package com.example.yukngaji.ui.Item;

import java.io.Serializable;

public class ItemHaji implements Serializable {
    private String Nama, Deskripsi, Gambar, Alamat, Telepon, Website;

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setGambar(String gambar) {
        Gambar = gambar;
    }

    public String getNama() {
        return Nama;
    }

    public void setDeskripsi(String deskripsi) {
        Deskripsi = deskripsi;
    }

    public String getGambar() {
        return Gambar;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getTelepon() {
        return Telepon;
    }

    public void setTelepon(String telepon) {
        Telepon = telepon;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }
}
