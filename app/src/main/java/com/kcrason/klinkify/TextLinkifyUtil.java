package com.kcrason.klinkify;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.text.util.Linkify;

import java.util.regex.Pattern;

/**
 * @author Created by KCrason on 2016/6/28.
 * @email 535089696@qq.com
 */
public class TextLinkifyUtil {

    public enum TextLinkifyStatus {
        LINK, ALL
    }

    public static SpannableString setLinkifyTextContent(String content, TextLinkifyStatus status) {
        SpannableString spannableString = SpannableString.valueOf(content);
        switch (status) {
            case LINK:
                Linkify.addLinks(spannableString, patternCompile(Constants.URL_REGEX), Constants.SCHEME_URL);
                break;
            case ALL:
                Linkify.addLinks(spannableString, patternCompile(Constants.URL_REGEX), Constants.SCHEME_URL);
                Linkify.addLinks(spannableString, patternCompile(Constants.TOPIC_REGEX), Constants.SCHEME_TOPIC);
                Linkify.addLinks(spannableString, patternCompile(Constants.AT_REGEX), Constants.SCHEME_AT);
                break;
        }

        //得到所有的span
        URLSpan[] urlSpans = spannableString.getSpans(0, spannableString.length(), URLSpan.class);

        TextClickSpan textClickSpan;

        for (URLSpan urlSpan : urlSpans) {
            int start = spannableString.getSpanStart(urlSpan);
            int end = spannableString.getSpanEnd(urlSpan);
            textClickSpan = new TextClickSpan(urlSpan.getURL(), Constants.BLUE);
            spannableString.removeSpan(urlSpan);
            spannableString.setSpan(textClickSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableString;
    }

    public static Pattern patternCompile(String pattern) {
        return Pattern.compile(pattern);
    }
}
