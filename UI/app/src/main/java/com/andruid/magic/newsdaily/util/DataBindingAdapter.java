package com.andruid.magic.newsdaily.util;

import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.databinding.BindingAdapter;

import java.text.DateFormat;

public class DataBindingAdapter {
    @BindingAdapter({"dateFromMs"})
    public static void formatDate(TextView textView, long ms){
        DateFormat dateFormat = DateFormat.getDateInstance();
        String date = dateFormat.format(ms);
        textView.setText(date);
    }

    @BindingAdapter({"readMore"})
    public static void readMoreUrl(TextView textView, String url){
        String str = "<u>" + url + "</u>";
        textView.setText(HtmlCompat.fromHtml(str, HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}