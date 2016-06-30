package com.kcrason.klinkify;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import com.kcrason.klinkify.utils.TextLinkifyUtil;
import com.kcrason.klinkify.widget.ExpandableTextView;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = MainActivity.class.getSimpleName();

    private String mContent = "#hhhh#我的青春我做主 @KCrason" + "\u200B,http://www.github.com/KCrason ,百度百毒：www.baidu.com%% ,#hhhh#我的青春我做主 @KCrason" + "\u200B,http://www.github.com/KCrason ,百度百毒：www.baidu.com%% ,#hhhh#我的青春我做主 @KCrason" + "\u200B,http://www.github.com/KCrason ,百度百毒：www.baidu.com%% ,Toast.makeText(getActivity(), isExpanded ? \"Expanded\" : \"Collapsed\", Toast.LENGTH_SHORT).show();";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExpandableTextView textView = (ExpandableTextView) findViewById(R.id.expand_text_view);
        textView.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
            @Override
            public void onExpandStateChanged(TextView textView, boolean isExpanded) {

            }
        });
        SpannableStringBuilder spannableStringBuilder = TextLinkifyUtil
                .setAllLinkifyTextContent(textView.getTextView(), mContent);
        textView.setText(spannableStringBuilder);
    }
}
