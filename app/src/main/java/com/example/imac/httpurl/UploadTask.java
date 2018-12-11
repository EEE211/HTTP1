package com.example.imac.httpurl;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

public class UploadTask extends AsyncTask<String, Void, String> {
    private MainActivity MyTask;
    private TextView textView;
    public UploadTask(TextView textView) {

        this.textView = textView;

    }
   // private Listener listener;
    // 非同期処理
    @Override
    protected String doInBackground(String... params) {
        String urlString = "http://54.95.87.76/test.php";
        String str = params[0];
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("key", str);
        HttpURLConnection urlConnection = null;
        URL url;
        int responseCode = 0;
        String responseData = "";
        try {
            if (queryParams == null) {
                url = new URL(urlString);
            } else {
                Uri.Builder builder = new Uri.Builder();
                Set keys = queryParams.keySet();

                for (Object key : keys) {
                    builder.appendQueryParameter((String) key, queryParams.get(key));
                }
                url = new URL(urlString + builder.toString());
            }
            //ステップ2:URLへのコネクションを取得する。
            urlConnection = (HttpURLConnection) url.openConnection();
            //ステップ3:接続設定（メソッドの決定,タイムアウト値,ヘッダー値等）を行う。
            //接続タイムアウトを設定する。
            urlConnection.setConnectTimeout(100000);
            //レスポンスデータ読み取りタイムアウトを設定する。
            urlConnection.setReadTimeout(100000);
            //ヘッダーにUser-Agentを設定する。
            urlConnection.setRequestProperty("User-Agent", "Android");
            //ヘッダーにAccept-Languageを設定する。
            urlConnection.setRequestProperty("Accept-Language", Locale.getDefault().toString());
            //HTTPのメソッドをGETに設定する。
            urlConnection.setRequestMethod("GET");
            //リクエストのボディ送信を許可しない
            urlConnection.setDoOutput(false);
            //レスポンスのボディ受信を許可する
            urlConnection.setDoInput(true);
            //ステップ4:コネクションを開く
            urlConnection.connect();
            //ステップ6:レスポンスボディの読み出しを行う。
            responseCode = urlConnection.getResponseCode();
            responseData = convertToString(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                //ステップ7:コネクションを閉じる。
                urlConnection.disconnect();
            }
        }
        Log.d("execute", "URL:" + urlString);
        Log.d("execute", "HttpStatusCode:" + responseCode);
        Log.d("execute", "ResponseData:" + responseData);
        return responseData;
    }
    public String convertToString(InputStream stream) throws IOException {
        StringBuffer sb = new StringBuffer();
        String line = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        try {
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();


    }

    // 非同期処理が終了後、結果をメインスレッドに返す
    @Override
    protected void onPostExecute(String result){

        try {
            JSONArray ar = new JSONArray(result); //受け取ったデータを配列にします
            JSONObject obj1 = ar.getJSONObject(0); //配列内のオブジェクトデータを取得

            String a = obj1.getString("key");
            textView.setText(new String(a));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
