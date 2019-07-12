package com.example.yukngaji.setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.yukngaji.ui.Item.itemdaftar;

public class UserPreference {
    private static final String PREFS_NAME = "user_pref";
    private static final String Firtsrun = "Firtsrun";
    private static final String Nama = "Email";
    private static final String Email = "Email";
    private static final String Uid = "Uid";
    private static final String Cekbayar = "cekbayar";
    private static final String Cekdaftar = "cekdaftar";
    private static final String Tunggu = "Tunggu";
    private static final String Guru = "Guru";
    private static final String NamaGuru = "NamaGuru";
    private static final String UidGuru = "UidGuru";
    private final SharedPreferences preferences;
    public UserPreference(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public void setFirtsrun(Boolean firtsrun){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Firtsrun, firtsrun);
        editor.apply();
    }
    public Boolean getFirtsrun(){
        Boolean cek=preferences.getBoolean(Firtsrun, true);
        return cek;
    }
    public void setTunggu(Boolean tunggu){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Tunggu, tunggu);
        editor.apply();
    }
    public Boolean getTunggu(){
        Boolean cek=preferences.getBoolean(Tunggu, false);
        return cek;
    }
    public void setNamaGuru(String namaGuru){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NamaGuru, namaGuru);
        editor.apply();
    }
    public String getNamaGuru(){
        String cek=preferences.getString(NamaGuru, "Guru");
        return cek;
    }
    public void setUidGuru(String uidGuru){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(UidGuru, uidGuru);
        editor.apply();
    }
    public String getUidGuru(){
        String cek=preferences.getString(UidGuru, "kosong");
        return cek;
    }

    public String getNama() {
        String nama=preferences.getString(Nama,"");
        return nama;
    }
    public String getEmail() {
        String email=preferences.getString(Email,"");
        return email;
    }
    public String getUid() {
        String uid=preferences.getString(Uid,"");
        return uid;
    }
    public void setNama(String nama){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Nama, nama);
        editor.apply();
    }
    public void setEmail(String email){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Email, email);
        editor.apply();
    }
    public void setUid(String uid){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Uid, uid);
        editor.apply();
    }
    public void setCekBayar(Boolean bayar){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Cekbayar, bayar);
        editor.apply();
    }
    public void setDaftar(Boolean daftar){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Cekdaftar, daftar);
        editor.apply();
    }
    public Boolean getCekBayar(){
        Boolean cek=preferences.getBoolean(Cekbayar, false);
        return cek;
    }
    public Boolean getCekDaftar(){
        Boolean cek=preferences.getBoolean(Cekdaftar, false);
        return cek;
    }
    public Boolean getGuru(){
        Boolean cek=preferences.getBoolean(Guru, false);
        return cek;
    }
    public void setGuru(Boolean guru){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Guru, guru);
        editor.apply();
    }

    public void setDataMengaji(String Jenjang,String Tanggal,String PrefGuru,String Paket){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Jenjang", Jenjang);
        editor.putString("Tanggal", Tanggal);
        editor.putString("PrefGuru", PrefGuru);
        editor.putString("Paket", Paket);
        editor.apply();
    }
    public void setLokasiMengaji(String Provinsi,String kota,String Kecamatan,String Alamat){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Provinsi", Provinsi);
        editor.putString("kota", kota);
        editor.putString("Kecamatan", Kecamatan);
        editor.putString("Alamat", Alamat);
        editor.apply();
    }
    public itemdaftar getitem(){
        itemdaftar item =new itemdaftar();
        item.setJenjang(preferences.getString("Jenjang", ""));
        item.setTanggal(preferences.getString("Tanggal", ""));
        item.setPrefGuru(preferences.getString("PrefGuru", ""));
        item.setPaket(preferences.getString("Paket", ""));
        item.setProvinsi(preferences.getString("Provinsi", ""));
        item.setKota(preferences.getString("kota", ""));
        item.setKecamatan(preferences.getString("Kecamatan", ""));
        item.setAlamat(preferences.getString("Alamat", ""));
        return item;
    }


}
