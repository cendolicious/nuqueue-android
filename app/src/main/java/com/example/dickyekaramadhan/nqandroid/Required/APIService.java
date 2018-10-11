package com.example.dickyekaramadhan.nqandroid.Required;

import com.example.dickyekaramadhan.nqandroid.Model.ModelAntrianUser;
import com.example.dickyekaramadhan.nqandroid.Model.ModelHistory;
import com.example.dickyekaramadhan.nqandroid.Model.ModelInsert;
import com.example.dickyekaramadhan.nqandroid.Model.ModelJadwal;
import com.example.dickyekaramadhan.nqandroid.Model.ModelLogin;
import com.example.dickyekaramadhan.nqandroid.Model.ModelNIK;
import com.example.dickyekaramadhan.nqandroid.Model.ModelPasien;
import com.example.dickyekaramadhan.nqandroid.Model.ModelPoli;
import com.example.dickyekaramadhan.nqandroid.Model.ModelRS;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by DICKYEKA on 14/02/2018.
 */

public interface APIService {

    @FormUrlEncoded
    @POST("getrumahsakit")
    Call<ModelRS> getrumahsakit(
            @Field("id_rs") String id_rs
    );

    @FormUrlEncoded
    @POST("getpoli")
    Call<ModelPoli> getpoli(
            @Field("id_rs") String id_rs
    );

    @FormUrlEncoded
    @POST("getjadwal")
    Call<ModelJadwal> getjadwal(
            @Field("id_poli") String id_poli
    );

    @FormUrlEncoded
    @POST("login")
    Call<ModelLogin> login(
            @Field("email_user") String email_user,
            @Field("password_user") String password_user
    );

    @FormUrlEncoded
    @POST("getprofiluser")
    Call<ModelLogin> getprofiluser(
            @Field("id_user") String id_user
    );

    @FormUrlEncoded
    @POST("gethistoryantrian")
    Call<ModelHistory> getriwayatantrian(
            @Field("id_user") String id_user
    );

    @FormUrlEncoded
    @POST("getlistantrian")
    Call<ModelAntrianUser> getlistantrian(
            @Field("id_user") String id_user
    );

    @FormUrlEncoded
    @POST("getnik")
    Call<ModelNIK> getnik(
            @Field("id_user") String id_user
    );

    @FormUrlEncoded
    @POST("insertPasienTemp")
    Call<ModelInsert> insertPasien(
            @Field("nama_pasien") String nama_pasien,
            @Field("tgllahir_pasien") String tgllahir_pasien,
            @Field("alamat_pasien") String alamat_pasien,
            @Field("telepon_pasien") String telepon_pasien,
            @Field("jk_pasien") String jk_pasien,
            @Field("goldar_pasien") String goldar_pasien,
            @Field("no_nik") String no_nik
    );

    @FormUrlEncoded
    @POST("posteditprofiluser")
    Call<ModelInsert> editprofil(
            @Field("id_user") String id_user,
            @Field("nama_user") String nama_user,
            @Field("tgllahir_user") String tgllahir_user,
            @Field("alamat_user") String alamat_user,
            @Field("telepon_user") String telepon_user,
            @Field("jk_user") String jk_user,
            @Field("goldar_user") String goldar_user,
            @Field("no_nik") String no_nik
    );

    @FormUrlEncoded
    @POST("batalkanantrian")
    Call<ModelInsert> batalkanantrian(
            @Field("id_antrian") String id_antrian
    );

    @FormUrlEncoded
    @POST("insertAntrian")
    Call<ModelInsert> insertAntrian(
            @Field("id_user") String id_user,
            @Field("id_rs") String id_rs,
            @Field("id_poli") String id_poli,
            @Field("id_jadwal") String id_jadwal,
            @Field("tipe_daftar") String tipe_daftar,
            @Field("no_nik") String no_nik
    );

    @FormUrlEncoded
    @POST("register")
    Call<ModelInsert> register(
            @Field("email_user") String email_user,
            @Field("nama_user") String nama_user,
            @Field("telepon_user") String telepon_user,
            @Field("password_user") String password_user
    );

    @FormUrlEncoded
    @POST("cekpasienlama")
    Call<ModelPasien> cekpasienlama(
            @Field("no_nik") String no_nik
    );

    @FormUrlEncoded
    @POST("cekpasien")
    Call<ModelPasien> cekpasien(
            @Field("no_nik") String no_nik
    );


}
