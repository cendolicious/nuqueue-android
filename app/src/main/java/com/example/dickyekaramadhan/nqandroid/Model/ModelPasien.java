package com.example.dickyekaramadhan.nqandroid.Model;

import com.example.dickyekaramadhan.nqandroid.Result.ResultPasien;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dicky Eka Ramadhan on 02/05/2018.
 */

public class ModelPasien {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<ResultPasien> result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultPasien> getResult() {
        return result;
    }

    public void setResult(List<ResultPasien> result) {
        this.result = result;
    }
}
