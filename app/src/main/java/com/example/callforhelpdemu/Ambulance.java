package com.example.callforhelpdemu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

public class Ambulance extends AppCompatActivity {
    TextView phone1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);
        phone1=findViewById(R.id.phone1);

    }
}
