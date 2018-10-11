package com.example.dickyekaramadhan.nqandroid.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DICKYEKA on 08/02/2018.
 */

public class AntrianModel {
    private String namaklinik,namapoli,noantri,tglpesan,antriandilayani;
    @SerializedName("booking_id")
    private String id;

    public AntrianModel(String namaklinik, String namapoli, String noantri, String tglpesan, String antriandilayani, String id) {
        this.namaklinik = namaklinik;
        this.namapoli = namapoli;
        this.noantri = noantri;
        this.tglpesan = tglpesan;
        this.antriandilayani = antriandilayani;
        this.id = id;
    }



    public String getNamaklinik() {
        return namaklinik;
    }

    public void setNamaklinik(String namaklinik) {
        this.namaklinik = namaklinik;
    }

    public String getNamapoli() {
        return namapoli;
    }

    public void setNamapoli(String namapoli) {
        this.namapoli = namapoli;
    }

    public String getNoantri() {
        return noantri;
    }

    public void setNoantri(String noantri) {
        this.noantri = noantri;
    }

    public String getTglpesan() {
        return tglpesan;
    }

    public void setTglpesan(String tglpesan) {
        this.tglpesan = tglpesan;
    }

    public String getAntriandilayani() {
        return antriandilayani;
    }

    public void setAntriandilayani(String antriandilayani) {
        this.antriandilayani = antriandilayani;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
