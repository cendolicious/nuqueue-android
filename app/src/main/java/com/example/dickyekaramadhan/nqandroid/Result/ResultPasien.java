package com.example.dickyekaramadhan.nqandroid.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dicky Eka Ramadhan on 02/05/2018.
 */

public class ResultPasien {
    @SerializedName("id_pasien")
    @Expose
    private String idPasien;
    @SerializedName("nama_pasien")
    @Expose
    private String namaPasien;
    @SerializedName("tgllahir_pasien")
    @Expose
    private String tgllahirPasien;
    @SerializedName("alamat_pasien")
    @Expose
    private String alamatPasien;
    @SerializedName("telepon_pasien")
    @Expose
    private String teleponPasien;
    @SerializedName("jk_pasien")
    @Expose
    private String jkPasien;
    @SerializedName("goldar_pasien")
    @Expose
    private String goldarPasien;
    @SerializedName("no_nik")
    @Expose
    private String noNik;

    public String getIdPasien() {
        return idPasien;
    }

    public void setIdPasien(String idPasien) {
        this.idPasien = idPasien;
    }

    public String getNamaPasien() {
        return namaPasien;
    }

    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
    }

    public String getTgllahirPasien() {
        return tgllahirPasien;
    }

    public void setTgllahirPasien(String tgllahirPasien) {
        this.tgllahirPasien = tgllahirPasien;
    }

    public String getAlamatPasien() {
        return alamatPasien;
    }

    public void setAlamatPasien(String alamatPasien) {
        this.alamatPasien = alamatPasien;
    }

    public String getTeleponPasien() {
        return teleponPasien;
    }

    public void setTeleponPasien(String teleponPasien) {
        this.teleponPasien = teleponPasien;
    }

    public String getJkPasien() {
        return jkPasien;
    }

    public void setJkPasien(String jkPasien) {
        this.jkPasien = jkPasien;
    }

    public String getGoldarPasien() {
        return goldarPasien;
    }

    public void setGoldarPasien(String goldarPasien) {
        this.goldarPasien = goldarPasien;
    }

    public String getNoNik() {
        return noNik;
    }

    public void setNoNik(String noNik) {
        this.noNik = noNik;
    }
}
