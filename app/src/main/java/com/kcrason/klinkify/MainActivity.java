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
        textView.setText(TextLinkifyUtil.setLinkifyTextContent(textView, "#hhhh#我的青春我做主 @KCrason" + "\u200B,http://www.github.com/KCrason", TextLinkifyUtil.TextLinkifyStatus.ALL));
    }
}
