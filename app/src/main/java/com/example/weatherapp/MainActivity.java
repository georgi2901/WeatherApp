package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends DBHelper {

    Button btn_current;
    Button btn_threeDays;
    Button btn_sixDays;
    Button btn_db;
    EditText et_dataInput;
    ListView lv_weatherReport;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_current = findViewById(R.id.btn_currentWeather);
        btn_threeDays = findViewById(R.id.btn_weatherThreeDays);
        btn_sixDays = findViewById(R.id.btn_weatherSixDays);
        btn_db = findViewById(R.id.btn_db);

        et_dataInput = findViewById(R.id.et_dataInput);
        lv_weatherReport = findViewById(R.id.lv_weatherReports);

        try {
            initDB();
        } catch (Exception e) {
            e.printStackTrace();
        }


        WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);
        WeatherDataServiceThreeDays weatherDataServiceThreeDays = new WeatherDataServiceThreeDays(MainActivity.this);
        WeatherDataServiceCurrentDay weatherDataServiceCurrentDay = new WeatherDataServiceCurrentDay(MainActivity.this);

        btn_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataServiceCurrentDay.getCityByName(et_dataInput.getText().toString(), new WeatherDataServiceCurrentDay.GetCityForecastByNameCallback() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<CurrentWeatherReportModel> currentWeatherReportModels) throws Exception {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, currentWeatherReportModels);
                        lv_weatherReport.setAdapter(arrayAdapter);
                        ExecSQL("INSERT INTO HISTORY(Name, Type)" + "VALUES(?, ?)", new Object[]{et_dataInput.getText().toString(), "Current day"},()-> Toast.makeText(getApplicationContext(), "Search saved!", Toast.LENGTH_SHORT).show());
                    }
                });
            }
        });

        btn_threeDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataServiceThreeDays.getCityByName(et_dataInput.getText().toString(), new WeatherDataServiceThreeDays.GetCityForecastByNameCallback() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) throws Exception {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
                        lv_weatherReport.setAdapter(arrayAdapter);
                        ExecSQL("INSERT INTO HISTORY(Name, Type)" + "VALUES(?, ?)", new Object[]{et_dataInput.getText().toString(), "Three days"},()-> Toast.makeText(getApplicationContext(), "Search saved!", Toast.LENGTH_SHORT).show());
                    }
                });
            }
        });

        btn_sixDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weatherDataService.getCityByName(et_dataInput.getText().toString(), new WeatherDataService.GetCityForecastByNameCallback() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) throws Exception {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
                        lv_weatherReport.setAdapter(arrayAdapter);
                        ExecSQL("INSERT INTO HISTORY(Name, Type)" + "VALUES(?, ?)", new Object[]{et_dataInput.getText().toString(), "Six days"},()-> Toast.makeText(getApplicationContext(), "Search saved!", Toast.LENGTH_SHORT).show());
                    }
                });


            }
        });

        btn_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VisualActivity.class);
                    startActivity(intent);
            }
        });

    }
}