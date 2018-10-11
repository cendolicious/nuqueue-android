package com.example.dickyekaramadhan.nqandroid.Model;

/**
 * Created by DICKYEKA on 27/02/2018.
 */

public class RiwayatModel {
    String namaklinik,namapoli,status,tanggal;

    public RiwayatModel(String namaklinik, String namapoli, String status, String tanggal) {
        this.namaklinik = namaklinik;
        this.namapoli = namapoli;
        this.status = status;
        this.tanggal = tanggal;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
