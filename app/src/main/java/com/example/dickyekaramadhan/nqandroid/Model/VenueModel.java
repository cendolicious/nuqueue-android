package com.example.dickyekaramadhan.nqandroid.Model;

/**
 * Created by DICKYEKA on 08/02/2018.
 */

public class VenueModel {
    private String nama;
    private String alamat;
    private String jarak;
    private String urlfoto;

    public VenueModel() {
    }

    public VenueModel(String nama, String alamat, String jarak, String urlfoto) {
        this.nama = nama;
        this.alamat = alamat;
        this.jarak = jarak;
        this.urlfoto = urlfoto;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }
}
