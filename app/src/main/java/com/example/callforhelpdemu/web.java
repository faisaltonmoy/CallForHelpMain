package com.example.callforhelpdemu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class web extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        //WEB SEARCH FROM MAP TITLE//

        webView = (WebView) findViewById(R.id.web);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient()
                   {
                       @Override
                       public boolean shouldOverrideUrlLoading(WebView view, String url) {
                           if( url.startsWith("http:") || url.startsWith("https:") ) {
                               return false;
                           }

                           // Otherwise allow the OS to handle things like tel, mailto, etc.
                           Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                           startActivity(intent);
                           return true;
                       }

                   }
        );
        webView.loadUrl("https://www.google.com/search?q=" + check("url.txt"));



    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
        {
            webView.goBack();
        }
        else
        {
            super.onBackPressed();
        }

    }

    private String check(String File_Name){

        String st = null;
        FileInputStream fis0 = null;
        try {
            fis0 =openFileInput(File_Name);
            InputStreamReader isr = new InputStreamReader(fis0);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while( (text = br.readLine()) != null ){
                sb.append(text).append("\n");
            }

            st = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fis0 != null) {
                try {
                    fis0.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return st;
    }
}
