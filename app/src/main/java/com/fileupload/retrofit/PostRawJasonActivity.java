package com.fileupload.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.fileupload.R;
import com.fileupload.utils.StaticUtils;
import com.fileupload.utils.WsUtils;
import com.fileupload.wscalling.WsFactory;
import com.fileupload.wscalling.WsResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;

/**
 * Created by SONI on 10/1/2018.
 */

public class PostRawJasonActivity extends AppCompatActivity implements WsResponse {

    private Button btnRawData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_json);
        try {
            initComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        btnRawData = findViewById(R.id.btnRawData);
        btnRawData.setOnClickListener(v -> {
            wsCallingForRawJason();
        });
    }

    private void wsCallingForRawJason() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", "101");
        jsonObject.addProperty("name", "Rahul Kumar");

        Call loginWsCall = WsFactory.rawJSONRequest(jsonObject);
        WsUtils.getReponse(loginWsCall, StaticUtils.REQUETS_JSON_RAW, this);
    }

    @Override
    public void successResponse(Object response, int code) {

        switch (code) {
            case StaticUtils.REQUETS_JSON_RAW:
                JsonObject jsonObject = (JsonObject) response;
                Toast.makeText(PostRawJasonActivity.this, "json response:" + jsonObject, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
         }

    }

    @Override
    public void failureRespons(Throwable error, int code) {

    }
}
