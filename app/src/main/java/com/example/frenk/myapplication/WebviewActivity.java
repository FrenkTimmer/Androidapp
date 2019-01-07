package com.example.frenk.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        String descriptionString = getIntent().getStringExtra("description");
        WebView view = (WebView) this.findViewById(R.id.webview);
                view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new MyBrowser());
        view.loadUrl("https://"+ descriptionString);
    }

    private class MyBrowserOverride extends WebViewClient implements com.example.frenk.myapplication.MyBrowserOverride {

        @Override
        public boolean OverrideLoading(WebView view, String Url)
        {
            view.loadUrl(Url);
            return true;
        }
    }
}
