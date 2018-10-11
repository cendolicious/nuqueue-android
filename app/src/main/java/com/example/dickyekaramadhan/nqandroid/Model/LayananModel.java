package com.example.dickyekaramadhan.nqandroid.Model;

/**
 * Created by DICKYEKA on 09/02/2018.
 */

public class LayananModel {
    private String nama;
    private int icon;

    public LayananModel() {
    }

    public LayananModel(int icon, String nama) {
        this.icon = icon;
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
