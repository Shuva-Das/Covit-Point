package com.example.covidpointbd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidpointbd.api.ApiUtilities;
import com.example.covidpointbd.api.CountryData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private TextView totalConfirm, totalActive, totalRecovered, totalDeath, totalTest;
    private TextView todayConfirm, todayRecovered, todayDeath, dateTV;
    private PieChart pieChart;
    private List<CountryData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();

        //for change the data when user open the app
        init();

        ApiUtilities.getApiInterface().grtCountryData()
                .enqueue(new Callback<List<CountryData>>() {
                    @Override
                    public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                        list.addAll(response.body());

                        for(int i=0; i<list.size(); i++)
                        {
                            if(list.get(i).getCountry(). equals("Bangladesh")){
                                int confirm = Integer.parseInt(list.get(i).getCases());
                                int tconfirm = Integer.parseInt(list.get(i).getTodayCases());
                                int active = Integer.parseInt(list.get(i).getActive());
                                int recovered = Integer.parseInt(list.get(i).getRecovered());
                                int trecovered = Integer.parseInt(list.get(i).getTodayRecovered());
                                int death = Integer.parseInt(list.get(i).getDeaths());
                                int tdeath = Integer.parseInt(list.get(i).getTodayDeaths());
                                int test = Integer.parseInt(list.get(i).getTests());

                                totalConfirm.setText(NumberFormat.getInstance().format(confirm));
                                todayConfirm.setText("+ " + NumberFormat.getInstance().format(tconfirm));
                                totalActive.setText(NumberFormat.getInstance().format(active));
                                totalRecovered.setText(NumberFormat.getInstance().format(recovered));
                                todayRecovered.setText("+ " + NumberFormat.getInstance().format(trecovered));
                                totalDeath.setText(NumberFormat.getInstance().format(death));
                                todayDeath.setText("+ " + NumberFormat.getInstance().format(tdeath));
                                totalTest.setText(NumberFormat.getInstance().format(test));


                                setText(list.get(i).getUpdated());

                                pieChart.addPieSlice(new PieModel("Confirm", confirm, getResources().getColor(R.color.yellow)));
                                pieChart.addPieSlice(new PieModel("Active", active, getResources().getColor(R.color.blue_pie)));
                                pieChart.addPieSlice(new PieModel("Recovered", recovered, getResources().getColor(R.color.green_pie)));
                                pieChart.addPieSlice(new PieModel("Death", death, getResources().getColor(R.color.red_pie)));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CountryData>> call, Throwable t) {
                        //Toast.makeText(MainActivity.this, "Error : ".t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("onFailure", t.toString());

                    }
                });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_home);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())
                {
                    case R.id.home:
                        return true;

                    case R.id.list:
                        startActivity(new Intent(getApplication()
                        ,TestingCenter.class));
                        overridePendingTransition(0,0);
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

    private void setText(String updated) {
        DateFormat format = new SimpleDateFormat("MMM dd, yyyy");
        long milliseconds = Long.parseLong(updated);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        dateTV.setText("Updated at " +format.format(calendar.getTime()));
    }

    private void init()
    {
        totalConfirm = findViewById(R.id.totalConfirm);
        totalActive = findViewById(R.id.totalActive);
        totalRecovered = findViewById(R.id.totalRecovered);
        totalDeath = findViewById(R.id.totalDeath);
        totalTest = findViewById(R.id.totalTest);
        todayConfirm = findViewById(R.id.todayConfirm);
        todayRecovered = findViewById(R.id.todayRecovered);
        todayDeath = findViewById(R.id.todayDeath);
        pieChart = findViewById(R.id.piechart);
        dateTV = findViewById(R.id.date);
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