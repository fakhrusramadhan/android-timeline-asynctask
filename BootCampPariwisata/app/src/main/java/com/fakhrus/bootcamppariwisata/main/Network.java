package com.fakhrus.bootcamppariwisata.main;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Fakhrus on 8/9/17.
 */

public class Network {

    private static final String TAG = "Network";

    public interface DataCallback {
        void onResponse(String json);
        void onError(Throwable throwable);
    }

    private DataCallback callback;

    public Network(DataCallback callback) {
        this.callback = callback;
    }

    public void process(){
        String url = buildUrl();
        new DataProcess().execute(url);
    }

    public String buildUrl() {

        final String BASE_URL = "http://www.erporate.com/bootcamp/jsonBootcamp.php";


        Uri builtUri = Uri.parse(BASE_URL)
                          .buildUpon()
                          .build();

        return builtUri.toString();
    }

    public class DataProcess extends AsyncTask<String, String, String> {

        private HttpURLConnection urlConnection;
        private BufferedReader reader;
        private String json;

        @Override protected String doInBackground(String... url) {

            try {

                URL urls = new URL(url[0]);
                urlConnection = (HttpURLConnection) urls.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                json = buffer.toString();

            } catch (MalformedURLException e) {
                callback.onError(e);
            } catch (IOException e) {
                callback.onError(e);
            }

            return json;
        }

        @Override protected void onPostExecute(String s) {
            super.onPostExecute(s);
            callback.onResponse(s);
        }
    }
}
