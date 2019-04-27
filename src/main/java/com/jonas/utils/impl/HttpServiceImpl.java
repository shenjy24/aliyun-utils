package com.jonas.utils.impl;

import com.jonas.utils.HttpService;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

/**
 * 【 enter the class description 】
 *
 * @author shenjy 2018/10/25
 */
public class HttpServiceImpl implements HttpService {

    @Override
    public Response synGet(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        Request request = new Request.Builder().url(builder.build()).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Response synGet(String url, Map<String, Object> params) {
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        if (null != params) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.addQueryParameter(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url(builder.build()).build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Response synPost(String url, Map<String, Object> params) {
        OkHttpClient okHttpClient = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        if (null != params) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.addEncoded(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
