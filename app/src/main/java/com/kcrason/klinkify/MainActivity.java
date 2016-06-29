package com.kcrason.klinkify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.txt_content);
        TextLinkifyUtil.setLinkifyTextContent("#高考内点事儿#祝考生们考出好成绩 @考生"+"\u200B,加油!!!www.zheblog.com", TextLinkifyUtil.TextLinkifyStatus.ALL);
    }
}
