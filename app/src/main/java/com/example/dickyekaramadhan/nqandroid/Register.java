package com.example.dickyekaramadhan.nqandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.dickyekaramadhan.nqandroid.Model.ModelInsert;
import com.example.dickyekaramadhan.nqandroid.Model.ModelLogin;
import com.example.dickyekaramadhan.nqandroid.Required.APIService;
import com.example.dickyekaramadhan.nqandroid.Required.Encryption;
import com.example.dickyekaramadhan.nqandroid.Required.RetrofitClient;
import com.example.dickyekaramadhan.nqandroid.Result.ResultLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Register extends Activity {

    Button btnDaftar;
    TextView login,credit,title,subtitle;
    Typeface typeface1;
    EditText nama,email,sandi,telepon;
    ProgressDialog progressDialog;
    TextInputLayout layPass;
    APIService service;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Register.this, Login.class);
        i.putExtra("from",getIntent().getStringExtra("from"));
        startActivity(i);
        overridePendingTransition(
                0,
                R.anim.top_out
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setMessage("Memuat..");
        progressDialog.setCancelable(false);

        login = (TextView) findViewById(R.id.textRegist);
        credit = (TextView) findViewById(R.id.credit);
        btnDaftar = (Button) findViewById(R.id.register);
        nama = (EditText) findViewById(R.id.editTextNama);
        email = (EditText) findViewById(R.id.editTextEmail);
        sandi = (EditText) findViewById(R.id.editTextPassword);
        telepon = (EditText) findViewById(R.id.editTextTelepon);

        layPass = (TextInputLayout) findViewById(R.id.textLayoutPassword);

        login.setPaintFlags(login.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this,Login.class);
                i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.putExtra("from",getIntent().getStringExtra("from"));
                startActivity(i);
            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nama.getText().toString().isEmpty() ||
                        email.getText().toString().isEmpty() ||
                        sandi.getText().toString().isEmpty() ||
                        telepon.getText().toString().isEmpty()){
                    if (nama.getText().toString().isEmpty()){
                        nama.setError("Nama harus di isi");
                        TSnackbar.make(findViewById(R.id.textRegist), Html.fromHtml("<font color=\"#ffffff\">Nama harus di isi.</font>"),TSnackbar.LENGTH_LONG).show();
                    }else if (email.getText().toString().isEmpty()){
                        email.setError("Email harus di isi");
                        TSnackbar.make(findViewById(R.id.textRegist), Html.fromHtml("<font color=\"#ffffff\">Email harus di isi.</font>"),TSnackbar.LENGTH_LONG).show();
                    }else if (sandi.getText().toString().isEmpty()){
                        layPass.setError("Kata Sandi harus di isi");
                        TSnackbar.make(findViewById(R.id.textRegist), Html.fromHtml("<font color=\"#ffffff\">Kata Sandi harus di isi.</font>"),TSnackbar.LENGTH_LONG).show();
                    }else if (telepon.getText().toString().isEmpty()){
                        telepon.setError("Telepon harus di isi");
                        TSnackbar.make(findViewById(R.id.textRegist), Html.fromHtml("<font color=\"#ffffff\">Telepon harus di isi.</font>"),TSnackbar.LENGTH_LONG).show();
                    }
                }else if (nama.getText().toString().length()<6){
                    nama.setError("Nama minimal 6 karakter");
                    TSnackbar.make(findViewById(R.id.textRegist), Html.fromHtml("<font color=\"#ffffff\">Nama minimal 6 karakter.</font>"),TSnackbar.LENGTH_LONG).show();
                }else if (sandi.getText().toString().length()<6){
                    layPass.setError("Kata Sandi minimal 6 karakter");
                    TSnackbar.make(findViewById(R.id.textRegist), Html.fromHtml("<font color=\"#ffffff\">Kata Sandi minimal 6 karakter.</font>"),TSnackbar.LENGTH_LONG).show();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                    email.setError("Alamat email tidak valid");
                    TSnackbar.make(findViewById(R.id.textRegist), Html.fromHtml("<font color=\"#ffffff\">Alamat email tidak valid.</font>"),TSnackbar.LENGTH_LONG).show();
                }else {
                    progressDialog.show();

                    Encryption encryption = Encryption.getDefault("Key", "Salt", new byte[16]);
                    String encryptedpass = encryption.encryptOrNull(sandi.getText().toString());

                    String url = "http://nuqueue.web.id/api/";
                    service = RetrofitClient.getClient(url).create(APIService.class);
                    Call<ModelInsert> call = service.register(email.getText().toString(),nama.getText().toString(),telepon.getText().toString(),encryptedpass);
                    call.enqueue(new Callback<ModelInsert>() {
                        @Override
                        public void onResponse(Call<ModelInsert> call, Response<ModelInsert> response) {
                            if (response.body().getMessage().equals("email sudah terdaftar")){
                                progressDialog.dismiss();
                                Toast.makeText(Register.this, "Email Sudah Terdaftar", Toast.LENGTH_SHORT).show();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(Register.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                                if (getIntent().getStringExtra("from").equals("jadwal")){
                                    Intent i = new Intent(Register.this,PilihanDaftar.class);
                                    i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    i.putExtra("namapoli",getIntent().getStringExtra("namapoli"));

                                    Call<ModelLogin> call2 = service.login(email.getText().toString(),sandi.getText().toString());
                                    call2.enqueue(new Callback<ModelLogin>() {
                                        @Override
                                        public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                                            ModelLogin databody = response.body();
                                            List<ResultLogin> data = databody.getResult();
                                            SharedPreferences.Editor editor = getSharedPreferences("sp", MODE_PRIVATE).edit();

                                            for (ResultLogin d: data) {
                                                editor.putString("tokenkey", d.getIdUser());
                                            }
                                            editor.putBoolean("status", true);
                                            editor.commit();
                                        }

                                        @Override
                                        public void onFailure(Call<ModelLogin> call, Throwable throwable) {

                                        }
                                    });

                                    startActivity(i);
                                    finish();
                                }else{
                                    Intent i = new Intent(Register.this,MainActivity.class);
                                    i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelInsert> call, Throwable throwable) {
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
