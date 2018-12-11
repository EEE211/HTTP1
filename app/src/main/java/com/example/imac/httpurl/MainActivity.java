package com.example.imac.httpurl;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private UploadTask task;
    private TextView textView;
    // wordを入れる
    private EditText editText;

    // phpがPOSTで受け取ったwordを入れて作成するHTMLページ(適宜合わせてください)



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.uriname);
        Button post = findViewById(R.id.post);

        // ボタンをタップして非同期処理を開始
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String param0 = editText.getText().toString();

                if(param0.length() != 0){
                    textView = findViewById(R.id.get_num);
                    task = new UploadTask(textView);
                    task.execute(param0);
                }

            }
        });

        textView = findViewById(R.id.text_view);
    }

}