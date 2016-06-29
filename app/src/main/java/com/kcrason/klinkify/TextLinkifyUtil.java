package com.kcrason.klinkify;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * @author Created by KCrason on 2016/6/28.
 * @email 535089696@qq.com
 */
public class TextLinkifyUtil {

    public enum TextLinkifyStatus {
        LINK, TOPIC, AT, ALL
    }

    public static SpannableStringBuilder setLinkifyTextContent(TextView textView, String content, TextLinkifyStatus status) {
        textView.setMovementMethod(new LinkTouchMovementMethod());
        SpannableStringBuilder spannableStringBuilder = SpannableStringBuilder.valueOf(content);
        switch (status) {
            case LINK: {
                Linkify.addLinks(spannableStringBuilder, patternCompile(Constants.URL_REGEX), Constants.SCHEME_URL);
                break;
            }
            case TOPIC: {
                Linkify.addLinks(spannableStringBuilder, patternCompile(Constants.TOPIC_REGEX), Constants.SCHEME_TOPIC);
                break;
            }
            case AT: {
                Linkify.addLinks(spannableStringBuilder, patternCompile(Constants.AT_REGEX), Constants.SCHEME_AT);
                break;
            }
            case ALL: {
                Linkify.addLinks(spannableStringBuilder, patternCompile(Constants.URL_REGEX), Constants.SCHEME_URL);
                Linkify.addLinks(spannableStringBuilder, patternCompile(Constants.TOPIC_REGEX), Constants.SCHEME_TOPIC);
                Linkify.addLinks(spannableStringBuilder, patternCompile(Constants.AT_REGEX), Constants.SCHEME_AT);
                break;
            }
        }

        //得到所有的span
        URLSpan[] urlSpans = spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), URLSpan.class);

        TextClickSpan textClickSpan;
        String spanStr;

        for (URLSpan urlSpan : urlSpans) {
            int start = spannableStringBuilder.getSpanStart(urlSpan);
            int end = spannableStringBuilder.getSpanEnd(urlSpan);
            spanStr = urlSpan.getURL();
            textClickSpan = new TextClickSpan(Constants.BLUE, spanStr);
            spannableStringBuilder.removeSpan(urlSpan);
            spannableStringBuilder.setSpan(textClickSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableReplace(spannableStringBuilder, start, end, spanStr);
        }
        return spannableStringBuilder;
    }

    private static TextLinkifyStatus getStatus(String spanStr) {
        if (spanStr.startsWith(Constants.SCHEME_URL)) {
            return TextLinkifyStatus.LINK;
        } else if (spanStr.startsWith(Constants.SCHEME_TOPIC)) {
            return TextLinkifyStatus.TOPIC;
        } else if (spanStr.startsWith(Constants.SCHEME_AT)) {
            return TextLinkifyStatus.AT;
        } else {
            return null;
        }
    }

    private static void spannableReplace(SpannableStringBuilder spannableStringBuilder, int start, int end, String spanStr) {
        TextLinkifyStatus status = getStatus(spanStr);
        switch (status) {
            case LINK: {
                spannableStringBuilder.replace(start, end, "this is link");
                break;
            }
            case TOPIC: {
                spannableStringBuilder.replace(start, end, "this is a topic");
                break;
            }
            case AT: {
                spannableStringBuilder.replace(start, end, "this is at");
                break;
            }
        }
    }

    public static Pattern patternCompile(String pattern) {
        return Pattern.compile(pattern);
    }
}
