package com.example.dickyekaramadhan.nqandroid.Model;

/**
 * Created by DICKYEKA on 07/02/2018.
 */

public class OptionModel {
    private String nama, value;

    public OptionModel() {
    }

    public OptionModel(String nama, String value) {
        this.nama = nama;
        this.value = value;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
