package com.example.dickyekaramadhan.nqandroid.Model;

import com.example.dickyekaramadhan.nqandroid.Result.ResultJadwal;
import com.example.dickyekaramadhan.nqandroid.Result.ResultPoli;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dicky Eka Ramadhan on 01/05/2018.
 */

public class ModelJadwal {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<ResultJadwal> result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultJadwal> getResult() {
        return result;
    }

    public void setResult(List<ResultJadwal> result) {
        this.result = result;
    }
}
