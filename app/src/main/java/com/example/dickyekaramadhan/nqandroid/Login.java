package com.example.dickyekaramadhan.nqandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.dickyekaramadhan.nqandroid.Model.ModelLogin;
import com.example.dickyekaramadhan.nqandroid.Required.APIService;
import com.example.dickyekaramadhan.nqandroid.Required.Encryption;
import com.example.dickyekaramadhan.nqandroid.Required.RetrofitClient;
import com.example.dickyekaramadhan.nqandroid.Result.ResultJadwal;
import com.example.dickyekaramadhan.nqandroid.Result.ResultLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Login extends Activity {

    Button btnMasuk;
    TextView register,credit,title,subtitle;
    Typeface typeface1;
    EditText email,password;
    Intent i;
    TextInputLayout layEmail,layPass;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getIntent().getStringExtra("from").equals("jadwal")){
            Intent i = new Intent(Login.this, JadwalVenue.class);
            startActivity(i);
            overridePendingTransition(
                    0,
                    R.anim.top_in
            );
        }else{
            Intent i = new Intent(Login.this, MainActivity.class);
            startActivity(i);
            overridePendingTransition(
                    0,
                    R.anim.top_out
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        layEmail = (TextInputLayout) findViewById(R.id.textLayoutEmail);
        layPass = (TextInputLayout) findViewById(R.id.textLayoutPassword);

        typeface1 = Typeface.createFromAsset(getAssets(),"font/Lato-Light.ttf");

        register = (TextView) findViewById(R.id.textRegist);
        credit = (TextView) findViewById(R.id.credit);
        btnMasuk = (Button) findViewById(R.id.login);

        register.setPaintFlags(register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        credit.setTypeface(typeface1);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Register.class);
                i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.putExtra("namadokter",getIntent().getStringExtra("namadokter"));
                i.putExtra("schedule",getIntent().getStringExtra("schedule"));
                i.putExtra("from",getIntent().getStringExtra("from"));
                startActivity(i);
            }
        });

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    if (email.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
                        layEmail.setError("Email belum terisi");
                        layPass.setError("Kata Sandi belum terisi");
                        TSnackbar.make(findViewById(R.id.textRegist), Html.fromHtml("<font color=\"#ffffff\">Email & Kata Sandi belum terisi.</font>"),TSnackbar.LENGTH_LONG).show();
                    }else if(email.getText().toString().isEmpty()){
                        layEmail.setError("Email belum terisi");
                        layPass.setErrorEnabled(false);
                        TSnackbar.make(findViewById(R.id.textRegist), Html.fromHtml("<font color=\"#ffffff\">Email belum terisi.</font>"),TSnackbar.LENGTH_LONG).show();
                    }
                    else if(password.getText().toString().isEmpty()){
                        layPass.setError("Kata Sandi belum terisi");
                        layEmail.setErrorEnabled(false);
                        TSnackbar.make(findViewById(R.id.textRegist), Html.fromHtml("<font color=\"#ffffff\">Kata Sandi belum terisi.</font>"),TSnackbar.LENGTH_LONG).show();
                    }
                }else{

                    String url = "http://nuqueue.web.id/api/";                    final APIService service = RetrofitClient.getClient(url).create(APIService.class);
                    final String email1 = email.getText().toString();
                    final String password1 = password.getText().toString();

                    Encryption encryption = Encryption.getDefault("Key", "Salt", new byte[16]);
                    String encryptedpass = encryption.encryptOrNull(password1);



                    Call<ModelLogin> call = service.login(email1,encryptedpass);
                    call.enqueue(new Callback<ModelLogin>() {
                        @Override
                        public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                            if (response.isSuccessful()){
                                ModelLogin databody = response.body();
                                if (databody.getMessage().equals("data ditemukan")){
                                    List<ResultLogin> data = databody.getResult();

                                    SharedPreferences.Editor editor = getSharedPreferences("sp", MODE_PRIVATE).edit();

                                    for (ResultLogin d: data) {
                                        editor.putString("tokenkey", d.getIdUser());
                                    }
                                    editor.putBoolean("status", true);
                                    editor.commit();

                                    if (getIntent().getStringExtra("from").equals("jadwal")){
                                        i = new Intent(Login.this,PilihanDaftar.class);
                                        i.putExtra("namapoli",getIntent().getStringExtra("namapoli"));
                                        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        startActivity(i);
                                        finish();
                                    }else{
                                        i = new Intent(Login.this,MainActivity.class);
                                        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        startActivity(i);
                                        finish();
                                    }

                                }else{
                                    Toast.makeText(Login.this, "Email/Kata Sandi salah", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(Login.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelLogin> call, Throwable throwable) {
                            Toast.makeText(Login.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });
    }
}
