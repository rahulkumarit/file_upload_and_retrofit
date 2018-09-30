package com.fileupload.retrofit;
/**
 * Created by SONI on 9/29/2018.
 */


import com.fileupload.models.Login;
import com.fileupload.models.User;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @FormUrlEncoded
    @POST("user/edit")
    Call<User> updateUser(@Field("first_name") String first, @Field("last_name") String last);


    @FormUrlEncoded
    @POST("LoginNew.php/")
    Call<Login> Login(@FieldMap Map<String, String> fields);

    @Multipart
    @POST("user/photo")
    Call<User> updateUser(@Part("photo") RequestBody photo, @Part("description") RequestBody description);

    @Multipart
    @POST("uploadNew.php")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @PartMap Map<String, RequestBody> map);





}
