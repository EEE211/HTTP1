package com.example.imac.httpurl

import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.widget.TextView

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.HashMap
import java.util.Locale
//a
class UploadTask(private val textView: TextView) : AsyncTask<String, Void, String>() {
    private val MyTask: MainActivity? = null
    // private Listener listener;
    // 非同期処理
    override fun doInBackground(vararg params: String): String {
        val urlString = "http://54.95.87.76/test.php"
        val str = params[0]
        val queryParams = HashMap<String, String>()
        queryParams["key"] = str
        var urlConnection: HttpURLConnection? = null
        val url: URL
        var responseCode = 0
        var responseData = ""
        try {
            if (queryParams == null) {
                url = URL(urlString)
            } else {
                val builder = Uri.Builder()
                val keys = queryParams.keys

                for (key in keys) {
                    builder.appendQueryParameter(key, queryParams[key])
                }
                url = URL(urlString + builder.toString())
            }
            //ステップ2:URLへのコネクションを取得する。
            urlConnection = url.openConnection() as HttpURLConnection
            //ステップ3:接続設定（メソッドの決定,タイムアウト値,ヘッダー値等）を行う。
            //接続タイムアウトを設定する。
            urlConnection.connectTimeout = 100000
            //レスポンスデータ読み取りタイムアウトを設定する。
            urlConnection.readTimeout = 100000
            //ヘッダーにUser-Agentを設定する。
            urlConnection.setRequestProperty("User-Agent", "Android")
            //ヘッダーにAccept-Languageを設定する。
            urlConnection.setRequestProperty("Accept-Language", Locale.getDefault().toString())
            //HTTPのメソッドをGETに設定する。
            urlConnection.requestMethod = "GET"
            //リクエストのボディ送信を許可しない
            urlConnection.doOutput = false
            //レスポンスのボディ受信を許可する
            urlConnection.doInput = true
            //ステップ4:コネクションを開く
            urlConnection.connect()
            //ステップ6:レスポンスボディの読み出しを行う。
            responseCode = urlConnection.responseCode
            responseData = convertToString(urlConnection.inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect()
        }
        Log.d("execute", "URL:$urlString")
        Log.d("execute", "HttpStatusCode:$responseCode")
        Log.d("execute", "ResponseData:$responseData")
        return responseData
    }

    @Throws(IOException::class)
    fun convertToString(stream: InputStream): String {
        val sb = StringBuffer()
        var line = ""
        val br = BufferedReader(InputStreamReader(stream, "UTF-8"))
        while ((line = br.readLine()) != null) {
            sb.append(line)
        }
        try {
            stream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return sb.toString()


    }

    // 非同期処理が終了後、結果をメインスレッドに返す
    override fun onPostExecute(result: String) {

        try {
            val ar = JSONArray(result) //受け取ったデータを配列にします
            val obj1 = ar.getJSONObject(0) //配列内のオブジェクトデータを取得

            val a = obj1.getString("key")
            textView.text = String(a)

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }
}
