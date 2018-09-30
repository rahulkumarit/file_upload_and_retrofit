package com.fileupload.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fileupload.R;
import com.fileupload.models.Login;
import com.fileupload.utils.StaticUtils;
import com.fileupload.utils.WsUtils;
import com.fileupload.wscalling.WsFactory;
import com.fileupload.wscalling.WsResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by SONI on 9/30/2018.
 */

public class LoginActivity extends AppCompatActivity implements WsResponse {

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

        Map<String, String> map = new HashMap<>();
        map.put("UserName", userName);
        map.put("Password", password);

        Call loginWsCall = WsFactory.loginWsCall(map);
        WsUtils.getReponse(loginWsCall, StaticUtils.REQUEST_LOGIN, this);

    }

    @Override
    public void successResponse(Object response, int code) {
        switch (code) {
            case StaticUtils.REQUEST_LOGIN:
                Login login = (Login) response;
                Toast.makeText(LoginActivity.this, "Login Response:" + login.getName(), Toast.LENGTH_SHORT).show();
                break;
            case StaticUtils.REQUEST_UPLOAD:
                break;
            default:
                break;
        }
    }

    @Override
    public void failureRespons(Throwable error, int code) {
        Toast.makeText(LoginActivity.this, "Error:" + error, Toast.LENGTH_SHORT).show();
    }

}
