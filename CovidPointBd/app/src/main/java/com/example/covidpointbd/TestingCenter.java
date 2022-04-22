package com.example.covidpointbd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TestingCenter extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_center);

        webView = (WebView) findViewById(R.id.listView);
        webView.setWebViewClient(new WebViewClient());
        webView.clearSslPreferences();
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        webView.loadUrl("https://covid19reports.dghs.gov.bd/");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_list);
        bottomNavigationView.setSelectedItemId(R.id.list);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplication()
                                ,MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.list:

                        return true;


                    case R.id.veccine:
                        startActivity(new Intent(getApplication()
                                ,VeccineReg.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
        {
            webView.goBack();
        }
        else{
            super.onBackPressed();
        }

    }
}