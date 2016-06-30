package com.kcrason.klinkify;

import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * @author Created by KCrason on 2016/6/30.
 * @email 535089696@qq.com
 */
public class TextOnTouchMovementMethod implements View.OnTouchListener {

    private TextClickSpan mPressedClickSpan;
    private long mStartTime;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        TextView widget = (TextView) v;
        SpannableString spannableString = SpannableString.valueOf(widget.getText());
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(widget.getText());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartTime = System.currentTimeMillis();
                mPressedClickSpan = getTextClickSpan(widget, spannable, event);
                if (mPressedClickSpan != null) {
                    BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(0xFF00ff00);
                    spannableString.setSpan(backgroundColorSpan, spannable.getSpanStart(mPressedClickSpan),
                            spannable.getSpanEnd(mPressedClickSpan), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    widget.setText(spannableString);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                /**
                 *  如果用户手指进行移动，那么获取当前手指移动的TextClickSpan的值是否与按下时的span一致，
                 *  如果一致，则说明，用户一直在当前span上，不做处理
                 *  如果不一致，则remove对应的BackgroundColorSpan
                 */
                TextClickSpan moveSpan = getTextClickSpan(widget, spannable, event);
                if (mPressedClickSpan != null && moveSpan != mPressedClickSpan) {
                    mPressedClickSpan = null;
                    removeBackgroundColorSpans(widget, spannableString);
                }
                break;
            case MotionEvent.ACTION_UP:
                /**
                 * 在抬起动作之前，确保用户所按下的span是与ACTION_DOWN时是一致的
                 * 只有在一致时才进行onClick()回调，并remove掉所有的BackgroundColorSpan
                 */
                if (mPressedClickSpan != null) {
                    //解决长按时响应Span的点击事件(实际上我们是不需要长按也响应Span的单击事件)
                    if (System.currentTimeMillis() - mStartTime < 500) {
                        mPressedClickSpan.onClick(widget);
                    }
                    removeBackgroundColorSpans(widget, spannableString);
                }
                return true;
        }
        return false;
    }

    /**
     * 移除背景span
     *
     * @param widget
     * @param spannableString
     */
    private void removeBackgroundColorSpans(TextView widget, SpannableString spannableString) {
        BackgroundColorSpan bgSpans[] = spannableString.getSpans(0, spannableString.length(), BackgroundColorSpan.class);
        for (BackgroundColorSpan colorSpan : bgSpans) {
            spannableString.removeSpan(colorSpan);
        }
        widget.setText(spannableString);
    }

    /**
     * 获取匹配到的span
     *
     * @param widget
     * @param spannable
     * @param event
     * @return
     */
    private TextClickSpan getTextClickSpan(TextView widget, Spannable spannable, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= widget.getTotalPaddingLeft();
        y -= widget.getTotalPaddingTop();

        x += widget.getScrollX();
        y += widget.getScrollY();

        Layout layout = widget.getLayout();

        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        TextClickSpan[] link = spannable.getSpans(off, off, TextClickSpan.class);
        TextClickSpan touchedSpan = null;
        if (link.length > 0) {
            touchedSpan = link[0];
        }
        return touchedSpan;
    }
}
