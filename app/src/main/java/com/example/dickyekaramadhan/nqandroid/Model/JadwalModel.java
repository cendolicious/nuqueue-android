package com.example.dickyekaramadhan.nqandroid.Model;

/**
 * Created by DICKYEKA on 09/02/2018.
 */

public class JadwalModel {
    private String namapelayan,jam,jmlantrian;
    private Boolean statusbuka;

    public JadwalModel() {
    }

    public JadwalModel(String namapelayan, String jam, String jmlantrian, Boolean statusbuka) {
        this.namapelayan = namapelayan;
        this.jam = jam;
        this.jmlantrian = jmlantrian;
        this.statusbuka = statusbuka;
    }

    public String getNamapelayan() {
        return namapelayan;
    }

    public void setNamapelayan(String namapelayan) {
        this.namapelayan = namapelayan;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getJmlantrian() {
        return jmlantrian;
    }

    public void setJmlantrian(String jmlantrian) {
        this.jmlantrian = jmlantrian;
    }

    public Boolean getStatusbuka() {
        return statusbuka;
    }

    public void setStatusbuka(Boolean statusbuka) {
        this.statusbuka = statusbuka;
    }
}
