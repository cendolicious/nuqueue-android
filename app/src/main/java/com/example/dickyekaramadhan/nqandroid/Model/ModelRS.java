package com.example.dickyekaramadhan.nqandroid.Model;

import com.example.dickyekaramadhan.nqandroid.Result.ResultRS;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dicky Eka Ramadhan on 01/05/2018.
 */

public class ModelRS {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<ResultRS> result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultRS> getResult() {
        return result;
    }

    public void setResult(List<ResultRS> result) {
        this.result = result;
    }

}
