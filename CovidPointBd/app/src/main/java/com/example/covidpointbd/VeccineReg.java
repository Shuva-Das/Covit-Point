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

public class VeccineReg extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veccine_reg);

        webView = (WebView) findViewById(R.id.veccineView);
        webView.setWebViewClient(new WebViewClient());
        webView.clearSslPreferences();
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        webView.loadUrl("https://surokkha.gov.bd/");

        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_Reg);
        bottomNavigationView.setSelectedItemId(R.id.veccine);

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
                        startActivity(new Intent(getApplication()
                                ,TestingCenter.class));
                        overridePendingTransition(0,0);
                        return true;


                    case R.id.veccine:
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