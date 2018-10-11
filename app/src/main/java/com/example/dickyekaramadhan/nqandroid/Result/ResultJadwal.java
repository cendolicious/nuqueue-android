package com.example.dickyekaramadhan.nqandroid.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dicky Eka Ramadhan on 01/05/2018.
 */

public class ResultJadwal {
    @SerializedName("id_jadwal")
    @Expose
    private String idJadwal;
    @SerializedName("jadwal_poli")
    @Expose
    private String jadwalPoli;
    @SerializedName("jammulai_poli")
    @Expose
    private String jammulaiPoli;
    @SerializedName("jamselesai_poli")
    @Expose
    private String jamselesaiPoli;
    @SerializedName("id_poli")
    @Expose
    private String idPoli;
    @SerializedName("id_rs")
    @Expose
    private String idRs;
    @SerializedName("id_dokter")
    @Expose
    private String idDokter;
    @SerializedName("nama_dokter")
    @Expose
    private String namaDokter;
    @SerializedName("jml_antrian")
    @Expose
    private String jmlAntrian;

    public String getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(String idJadwal) {
        this.idJadwal = idJadwal;
    }

    public String getJadwalPoli() {
        return jadwalPoli;
    }

    public void setJadwalPoli(String jadwalPoli) {
        this.jadwalPoli = jadwalPoli;
    }

    public String getJammulaiPoli() {
        return jammulaiPoli;
    }

    public void setJammulaiPoli(String jammulaiPoli) {
        this.jammulaiPoli = jammulaiPoli;
    }

    public String getJamselesaiPoli() {
        return jamselesaiPoli;
    }

    public void setJamselesaiPoli(String jamselesaiPoli) {
        this.jamselesaiPoli = jamselesaiPoli;
    }

    public String getIdPoli() {
        return idPoli;
    }

    public void setIdPoli(String idPoli) {
        this.idPoli = idPoli;
    }

    public String getIdRs() {
        return idRs;
    }

    public void setIdRs(String idRs) {
        this.idRs = idRs;
    }

    public String getIdDokter() {
        return idDokter;
    }

    public void setIdDokter(String idDokter) {
        this.idDokter = idDokter;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public String getJmlAntrian() {
        return jmlAntrian;
    }

    public void setJmlAntrian(String jmlAntrian) {
        this.jmlAntrian = jmlAntrian;
    }
}
