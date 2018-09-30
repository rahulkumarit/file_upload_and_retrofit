package com.fileupload.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fileupload.EndPoints;
import com.fileupload.R;
import com.fileupload.models.Login;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by SONI on 9/30/2018.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserName, edtUPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            initCopnent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCopnent() {
        edtUserName = findViewById(R.id.edtUserName);
        edtUPassword = findViewById(R.id.edtUPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            codeForLogin();
        });
    }

    private void codeForLogin() {
        String userName = edtUserName.getText().toString().trim();
        String password = edtUPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(LoginActivity.this, "eneter user name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "eneter password", Toast.LENGTH_SHORT).show();
        } else {
            wsCalling(userName, password);
        }
    }

    //Ws calling for login
    private void wsCalling(String userName, String password) {

        // Change base URL to your upload server URL.

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Map<String, String> map = new HashMap<>();
        map.put("UserName", userName);
        map.put("Password", password);

        retrofit2.Call<Login> req = apiInterface.Login(map);

        req.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                try {

                    Log.e("Login Response:", "" + response.body());
                    Toast.makeText(LoginActivity.this, "response:" + response.body(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(LoginActivity.this, "failure response", Toast.LENGTH_SHORT).show();

            }
        });


    }


}
