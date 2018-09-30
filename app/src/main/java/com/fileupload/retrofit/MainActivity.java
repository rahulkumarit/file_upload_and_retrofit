package com.fileupload.retrofit;

import android.Manifest;
import android.content.Intent;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fileupload.EndPoints;
import com.fileupload.R;
import com.fileupload.models.UploadImage;
import com.fileupload.utils.StaticUtils;
import com.fileupload.utils.WsUtils;
import com.fileupload.wscalling.WsFactory;
import com.fileupload.wscalling.WsResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements WsResponse {

    public static final int PICK_IMAGE = 100;

    private ApiInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        Button btn = (Button) findViewById(R.id.btn_upload);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        // Change base URL to your upload server URL.
        service = new Retrofit.Builder().baseUrl(EndPoints.ROOT_URL).client(client).build().create(ApiInterface.class);

        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    grantPermission();
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);


                 }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {

            android.net.Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            android.database.Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

            if (cursor == null)
                return;

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            File file = new File(filePath);

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload_file", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "rahul");
            RequestBody paword = RequestBody.create(MediaType.parse("text/plain"), "kumar");

            Map<String, RequestBody> map = new HashMap<>();

            map.put("UserName", name);
            map.put("Password", paword);

            Call loginWsCall = WsFactory.uplaodImage(body, map);
            WsUtils.getReponse(loginWsCall, StaticUtils.REQUEST_UPLOAD, this);

        }
    }

    @Override
    public void successResponse(Object response, int code) {
        switch (code) {
            case StaticUtils.REQUEST_LOGIN:
                break;
            case StaticUtils.REQUEST_UPLOAD:
                UploadImage uploadImage = (UploadImage) response;
                Toast.makeText(MainActivity.this, "File Upload Response:" + uploadImage.getStatus(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    @Override
    public void failureRespons(Throwable error, int code) {

    }

    private void grantPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }
    }

}