package com.example.yukngaji.ui.Item;

import java.io.Serializable;

public class ItemNotif implements Serializable {
    private String judul;
    private String gambar;
    private String deskripsi;

    public String getJudul() {
        return judul;
    }

    public ItemNotif(String judul, String gambar, String description) {
        this.judul = judul;
        this.gambar = gambar;
        this.deskripsi = description;
    }

    public ItemNotif() {

    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getDescription() {
        return deskripsi;
    }

    public void setDescription(String description) {
        this.deskripsi = description;
    }
}
