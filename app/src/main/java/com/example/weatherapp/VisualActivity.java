package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class VisualActivity extends DBHelper {
    Button btn_back;
    ListView simpleList;
    public void FillList() throws Exception{
        final ArrayList<String> resultList = new ArrayList<>();
        SelectSQL("SELECT * FROM HISTORY", null, (ID, Name, Type)->{
            resultList.add(Name + "\t" + Type + "\n");
        });
        simpleList.clearChoices();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.activity_list_view, R.id.textView, resultList);
        simpleList.setAdapter(arrayAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual);
        btn_back = findViewById(R.id.btn_back);
        simpleList = findViewById(R.id.lv_db);
        try {
            FillList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisualActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}