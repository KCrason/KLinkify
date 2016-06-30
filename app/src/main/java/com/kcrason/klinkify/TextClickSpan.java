package com.kcrason.klinkify;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import com.kcrason.klinkify.common.Constants;

/**
 * @author Created by KCrason on 2016/6/28.
 * @email 535089696@qq.com
 */
public class TextClickSpan extends ClickableSpan {

    private String mColor = Constants.BLUE;
    private String mRegexStr;

    private boolean mPressed;

    public TextClickSpan(String color, String regexStr) {
        this.mColor = color;
        this.mRegexStr = regexStr;
    }

    public void setPressed(boolean isPressed) {
        this.mPressed = isPressed;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        //更改背景色颜色
        ds.bgColor = mPressed ? Color.parseColor("#ffff00") : Color.TRANSPARENT;
        //设置文字颜色
        ds.setColor(Color.parseColor("#ff0000"));
        //设置下划线为false,即不需要下划线
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
        if (!TextUtils.isEmpty(mRegexStr)) {
            if (mRegexStr.startsWith(Constants.SCHEME_URL)) {
                //url点击
                Toast.makeText(widget.getContext(), mRegexStr, Toast.LENGTH_SHORT).show();
            } else if (mRegexStr.startsWith(Constants.SCHEME_TOPIC)) {
                //话题点击
                Toast.makeText(widget.getContext(), mRegexStr, Toast.LENGTH_SHORT).show();
            } else if (mRegexStr.startsWith(Constants.SCHEME_AT)) {
                //@点击
                Toast.makeText(widget.getContext(), mRegexStr, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
