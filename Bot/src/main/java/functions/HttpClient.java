package functions;
//package com.example.task01;

import okhttp3.*;

import java.io.IOException;

public class HttpClient {

    public OkHttpClient client = new OkHttpClient();

    /*
        Передать нужно ссылку запроса
    */
    public String post(String url) throws IOException {
        /*
            Тело запроса
        */
        RequestBody body = new FormBody.Builder()
                .add ( "Название" , "Значение" )
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
