package com.example.yukngaji.ui.Item;

public class ItemProfil {
    private String Nama,JenisKelamin,Provinsi,Kota,Kecamatan,Alamat,KodePos;
    public ItemProfil(){

    }
    public ItemProfil(String nama,String jeniskelamin,String provinsi,String kota,String kecamatan,String alamat,String kodePos){
        this.Nama=nama;
        this.JenisKelamin=jeniskelamin;
        this.Provinsi=provinsi;
        this.Kota=kota;
        this.Kecamatan=kecamatan;
        this.Alamat=alamat;
        this.KodePos=kodePos;
    }
    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getJenisKelamin() {
        return JenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        JenisKelamin = jenisKelamin;
    }

    public String getProvinsi() {
        return Provinsi;
    }

    public void setProvinsi(String provinsi) {
        Provinsi = provinsi;
    }

    public String getKota() {
        return Kota;
    }

    public void setKota(String kota) {
        Kota = kota;
    }

    public String getKecamatan() {
        return Kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        Kecamatan = kecamatan;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getKodePos() {
        return KodePos;
    }

    public void setKodePos(String kodePos) {
        KodePos = kodePos;
    }

}
