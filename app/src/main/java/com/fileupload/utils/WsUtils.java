package com.fileupload.utils;


import com.fileupload.wscalling.WsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Devrepublic-14 on 7/14/2017.
 */

public abstract class WsUtils {

    public abstract void init();

    public static void getReponse(Call call, final int code, final WsResponse jsonResponse) {
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                jsonResponse.successResponse(response.body(), code);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                jsonResponse.failureRespons(t, code);
                call.cancel();
            }
        });
    }
}