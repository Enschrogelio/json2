package com.ruby.x.json2.DataAccess;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.ruby.x.json2.Helpers.GlobalHelper;
import com.ruby.x.json2.Models.DataConstant;
import com.ruby.x.json2.Models.DataTask;
import com.ruby.x.json2.Models.ResponseError;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.ruby.x.json2.Models.DataConstant.BASE_URL;

public class ServerDataAccess extends GlobalHelper {

    public ServerDataAccess(Context context) {
        super(context);
    }

    public static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    public void errorHandling(Response response, ServerDataAccessListener listener) {
        final Gson gson = new Gson();
        if (response.code() == 403 || response.code() == 401) {
            getUserHelper().userLogout();
        }
        if (response.code() == 500) {
            ResponseError return_data = new ResponseError();
            return_data.setMessage("Unknown Error Or Internal Server Error, Please check your connection or contact your Administrator");
            listener.onResponseError(return_data.getMessage());
        }
        else if (response.code() == 204) {
            ResponseError return_data = new ResponseError();
            return_data.setMessage("Data not found");
            listener.onResponseError(return_data.getMessage());
        }
        else {
            try {
                String responseString = response.body().string();
                ResponseError return_data = gson.fromJson(responseString, ResponseError.class);
                listener.onResponseError(return_data.getMessage());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void doSave(String state, DataTask data, final ServerDataAccessListener listener) {
        String action_url = "doSave";
        MultipartBody.Builder formBody = new MultipartBody.Builder();
        formBody.setType(MultipartBody.FORM);
        formBody.addPart(
                Headers.of("Content-Disposition", "form-data; name=\"state\""),
                RequestBody.create(null, state));
        formBody.addPart(
                Headers.of("Content-Disposition", "form-data; name=\"task_id\""),
                RequestBody.create(null, data.getId()));
        formBody.addPart(
                Headers.of("Content-Disposition", "form-data; name=\"title\""),
                RequestBody.create(null, data.getTitle()));
        formBody.addPart(
                Headers.of("Content-Disposition", "form-data; name=\"description\""),
                RequestBody.create(null, data.getDescription()));

        formBody.addPart(
                Headers.of("Content-Disposition", "form-data; name=\"created_date\""),
                RequestBody.create(null, data.getCreatedDate()));

        if (data.getFileDocumentation() != null && !data.getFileDocumentation().isEmpty()) {
            File filePhoto = new File(Environment.getExternalStorageDirectory(), DataConstant.FOLDER_SAVE + data.getFileDocumentation());
            if (filePhoto.exists()) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
                Bitmap bitmap = BitmapFactory.decodeFile(filePhoto.getPath());
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                final byte[] bitmapdata = stream.toByteArray();
                formBody.addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"file_documentation\"; filename=\"" + data.getFileDocumentation() + "\""),
                        RequestBody.create(MEDIA_TYPE_PNG, bitmapdata));
                formBody.addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"file_documentation_txt\""),
                        RequestBody.create(null, data.getFileDocumentation()));
            }
        }

        RequestBody requestBody = formBody.build();

        final Request request = new Request.Builder()
                .url(BASE_URL + action_url)
                .post(requestBody)
                .addHeader("auth", getSharedPreferencesHelper().getPrefAuthToken())
                .build();

        Log.i("auth_token", getSharedPreferencesHelper().getPrefAuthToken());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(request, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    listener.onResponseSuccess(response.body().string());
                } else {
                    errorHandling(response, listener);
                }
            }
        });
    }

    public void doDelete(String task_id,final ServerDataAccessListener listener) {
        String action_url = "doDelete";
        MultipartBody.Builder formBody = new MultipartBody.Builder();
        formBody.setType(MultipartBody.FORM);
        formBody.addPart(
                Headers.of("Content-Disposition", "form-data; name=\"task_id\""),
                RequestBody.create(null, task_id));

        RequestBody requestBody = formBody.build();

        final Request request = new Request.Builder()
                .url(BASE_URL + action_url)
                .post(requestBody)
                .addHeader("auth", getSharedPreferencesHelper().getPrefAuthToken())
                .build();

        Log.i("auth_token", getSharedPreferencesHelper().getPrefAuthToken());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure(request, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    listener.onResponseSuccess(response.body().string());
                } else {
                    errorHandling(response, listener);
                }
            }
        });
    }

    public String getTaskList(Integer page) {
        String action_url = "getTaskList";
        Integer count_row = DataConstant.COUNT_ROW;
        RequestBody formBody = new FormBody.Builder()
                .add("last_page", page.toString())
                .add("count_row", count_row.toString())
                .build();

        final Request request = new Request.Builder()
                .url(BASE_URL + action_url)
                .post(formBody)
                .addHeader("auth", getSharedPreferencesHelper().getPrefAuthToken())
                .build();

        Call call = client.newCall(request);
        Response response = null;
        String jsonData = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                jsonData = response.body().string();
            }
            else {
                jsonData = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }
}
