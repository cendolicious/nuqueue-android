package com.example.dickyekaramadhan.nqandroid.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dicky Eka Ramadhan on 01/05/2018.
 */

public class ResultRS {
    @SerializedName("id_rs")
    @Expose
    private String idRs;
    @SerializedName("nama_rs")
    @Expose
    private String namaRs;
    @SerializedName("alamat_rs")
    @Expose
    private String alamatRs;
    @SerializedName("telepon_rs")
    @Expose
    private String teleponRs;
    @SerializedName("deskripsi_rs")
    @Expose
    private String deskripsiRs;
    @SerializedName("email_rs")
    @Expose
    private String emailRs;
    @SerializedName("password_rs")
    @Expose
    private String passwordRs;
    @SerializedName("lat_rs")
    @Expose
    private String latRs;
    @SerializedName("lon_rs")
    @Expose
    private String lonRs;
    @SerializedName("status_rs")
    @Expose
    private String statusRs;
    @SerializedName("url_api")
    @Expose
    private String urlApi;

    public String getIdRs() {
        return idRs;
    }

    public void setIdRs(String idRs) {
        this.idRs = idRs;
    }

    public String getNamaRs() {
        return namaRs;
    }

    public void setNamaRs(String namaRs) {
        this.namaRs = namaRs;
    }

    public String getAlamatRs() {
        return alamatRs;
    }

    public void setAlamatRs(String alamatRs) {
        this.alamatRs = alamatRs;
    }

    public String getTeleponRs() {
        return teleponRs;
    }

    public void setTeleponRs(String teleponRs) {
        this.teleponRs = teleponRs;
    }

    public String getDeskripsiRs() {
        return deskripsiRs;
    }

    public void setDeskripsiRs(String deskripsiRs) {
        this.deskripsiRs = deskripsiRs;
    }

    public String getEmailRs() {
        return emailRs;
    }

    public void setEmailRs(String emailRs) {
        this.emailRs = emailRs;
    }

    public String getPasswordRs() {
        return passwordRs;
    }

    public void setPasswordRs(String passwordRs) {
        this.passwordRs = passwordRs;
    }

    public String getLatRs() {
        return latRs;
    }

    public void setLatRs(String latRs) {
        this.latRs = latRs;
    }

    public String getLonRs() {
        return lonRs;
    }

    public void setLonRs(String lonRs) {
        this.lonRs = lonRs;
    }

    public String getStatusRs() {
        return statusRs;
    }

    public void setStatusRs(String statusRs) {
        this.statusRs = statusRs;
    }

    public String getUrlApi() {
        return urlApi;
    }

    public void setUrlApi(String urlApi) {
        this.urlApi = urlApi;
    }
}
