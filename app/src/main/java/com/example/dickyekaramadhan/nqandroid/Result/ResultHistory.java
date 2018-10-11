package com.example.dickyekaramadhan.nqandroid.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dicky Eka Ramadhan on 03/05/2018.
 */

public class ResultHistory {
    @SerializedName("nama_rs")
    @Expose
    private String namaRs;
    @SerializedName("nama_poli")
    @Expose
    private String namaPoli;
    @SerializedName("nama_dokter")
    @Expose
    private String namaDokter;
    @SerializedName("no_antrian")
    @Expose
    private String noAntrian;
    @SerializedName("tgl_antrian")
    @Expose
    private String tglAntrian;
    @SerializedName("nama_pasien")
    @Expose
    private String namaPasien;

    public String getNamaRs() {
        return namaRs;
    }

    public void setNamaRs(String namaRs) {
        this.namaRs = namaRs;
    }

    public String getNamaPoli() {
        return namaPoli;
    }

    public void setNamaPoli(String namaPoli) {
        this.namaPoli = namaPoli;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public String getNoAntrian() {
        return noAntrian;
    }

    public void setNoAntrian(String noAntrian) {
        this.noAntrian = noAntrian;
    }

    public String getTglAntrian() {
        return tglAntrian;
    }

    public void setTglAntrian(String tglAntrian) {
        this.tglAntrian = tglAntrian;
    }

    public String getNamaPasien() {
        return namaPasien;
    }

    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
    }
}
