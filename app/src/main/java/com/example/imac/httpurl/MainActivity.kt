package com.example.imac.httpurl

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

//a

class MainActivity : AppCompatActivity() {

    private var task: UploadTask? = null
    private var textView: TextView? = null
    // wordを入れる
    private var editText: EditText? = null

    // phpがPOSTで受け取ったwordを入れて作成するHTMLページ(適宜合わせてください)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.uriname)
        val post = findViewById<Button>(R.id.post)

        // ボタンをタップして非同期処理を開始
        post.setOnClickListener {
            val param0 = editText!!.text.toString()

            if (param0.length != 0) {
                textView = findViewById(R.id.get_num)
                task = UploadTask(textView!!)
                task!!.execute(param0)
            }
        }

        textView = findViewById(R.id.text_view)
    }

}