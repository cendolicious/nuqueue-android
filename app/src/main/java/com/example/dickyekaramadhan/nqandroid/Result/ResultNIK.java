package com.example.dickyekaramadhan.nqandroid.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dicky Eka Ramadhan on 30/07/2018.
 */

public class ResultNIK {
    @SerializedName("id_nik")
    @Expose
    private String idNik;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("no_nik")
    @Expose
    private String noNik;
    @SerializedName("id_rs")
    @Expose
    private String idRs;

    public String getIdNik() {
        return idNik;
    }

    public void setIdNik(String idNik) {
        this.idNik = idNik;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNoNik() {
        return noNik;
    }

    public void setNoNik(String noNik) {
        this.noNik = noNik;
    }

    public String getIdRs() {
        return idRs;
    }

    public void setIdRs(String idRs) {
        this.idRs = idRs;
    }


}
