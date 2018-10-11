package com.example.dickyekaramadhan.nqandroid.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dicky Eka Ramadhan on 01/05/2018.
 */

public class ResultPoli {
    @SerializedName("id_poli")
    @Expose
    private String idPoli;
    @SerializedName("nama_poli")
    @Expose
    private String namaPoli;
    @SerializedName("id_rs")
    @Expose
    private String idRs;

    public String getIdPoli() {
        return idPoli;
    }

    public void setIdPoli(String idPoli) {
        this.idPoli = idPoli;
    }

    public String getNamaPoli() {
        return namaPoli;
    }

    public void setNamaPoli(String namaPoli) {
        this.namaPoli = namaPoli;
    }

    public String getIdRs() {
        return idRs;
    }

    public void setIdRs(String idRs) {
        this.idRs = idRs;
    }
}
