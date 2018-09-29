package com.fileupload.retrofit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by SONI on 9/29/2018.
 */

public  interface Service  {

    @Multipart
    @POST("uploadNew.php")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("UserName") RequestBody first, @Part("Password") RequestBody last);

}
