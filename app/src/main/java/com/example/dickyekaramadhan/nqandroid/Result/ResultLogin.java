package com.example.dickyekaramadhan.nqandroid.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dicky Eka Ramadhan on 01/05/2018.
 */

public class ResultLogin {
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("nama_user")
    @Expose
    private String namaUser;
    @SerializedName("email_user")
    @Expose
    private String emailUser;
    @SerializedName("password_user")
    @Expose
    private String passwordUser;
    @SerializedName("tgllahir_user")
    @Expose
    private String tgllahirUser;
    @SerializedName("alamat_user")
    @Expose
    private String alamatUser;
    @SerializedName("telepon_user")
    @Expose
    private String teleponUser;
    @SerializedName("jk_user")
    @Expose
    private String jkUser;
    @SerializedName("goldar_user")
    @Expose
    private String goldarUser;
    @SerializedName("no_nik")
    @Expose
    private String no_nik;

    public String getNo_nik() {
        return no_nik;
    }

    public void setNo_nik(String no_nik) {
        this.no_nik = no_nik;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getTgllahirUser() {
        return tgllahirUser;
    }

    public void setTgllahirUser(String tgllahirUser) {
        this.tgllahirUser = tgllahirUser;
    }

    public String getAlamatUser() {
        return alamatUser;
    }

    public void setAlamatUser(String alamatUser) {
        this.alamatUser = alamatUser;
    }

    public String getTeleponUser() {
        return teleponUser;
    }

    public void setTeleponUser(String teleponUser) {
        this.teleponUser = teleponUser;
    }

    public String getJkUser() {
        return jkUser;
    }

    public void setJkUser(String jkUser) {
        this.jkUser = jkUser;
    }

    public String getGoldarUser() {
        return goldarUser;
    }

    public void setGoldarUser(String goldarUser) {
        this.goldarUser = goldarUser;
    }
}
