package functions;

import okhttp3.*;
import java.io.IOException;

public class HttpClient {
    public OkHttpClient client = new OkHttpClient();

    public String post(String url) throws IOException {
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
