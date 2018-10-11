package com.example.dickyekaramadhan.nqandroid.Model;

import com.example.dickyekaramadhan.nqandroid.Result.ResultAntrianUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dicky Eka Ramadhan on 03/05/2018.
 */

public class ModelAntrianUser {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<ResultAntrianUser> result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultAntrianUser> getResult() {
        return result;
    }

    public void setResult(List<ResultAntrianUser> result) {
        this.result = result;
    }

}
