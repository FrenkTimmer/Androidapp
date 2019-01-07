package com.example.frenk.myapplication;

import android.webkit.WebView;

/**
 * Created by Frenk on 5-3-2016.
 */
public interface MyBrowserOverride {
    boolean OverrideLoading(WebView view, String Url);
}
