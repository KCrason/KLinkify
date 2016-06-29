package com.kcrason.klinkify;

import android.graphics.Color;
import android.net.Uri;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * @author Created by KCrason on 2016/6/28.
 * @email 535089696@qq.com
 */
public class TextClickSpan extends ClickableSpan {

    private String mColor = Constants.BLUE;
    private String mRegexStr;

    public TextClickSpan(String color, String regexStr) {
        this.mColor = color;
        this.mRegexStr = regexStr;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        //更改背景色颜色
        ds.bgColor = Color.parseColor("#ff00ff");
        //设置文字颜色
        ds.setColor(Color.parseColor(mColor));
        //设置下划线为false,即不需要下划线
        ds.setUnderlineText(false);
        super.updateDrawState(ds);
    }

    @Override
    public void onClick(View widget) {
        if (!TextUtils.isEmpty(mRegexStr)) {
            Uri uri = Uri.parse(mRegexStr);
            if (uri.getScheme().startsWith(Constants.SCHEME_URL)) {
                //url点击
            } else if (uri.getScheme().startsWith(Constants.SCHEME_TOPIC)) {
                //话题点击
            } else if (uri.getScheme().startsWith(Constants.SCHEME_AT)) {
                //@点击
            }
        }
    }
}
