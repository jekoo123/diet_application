package com.example.otaku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;



import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class third extends AppCompatActivity {
    Button button3 , button_back;
    TextView result_food, result_amount, result_ca, result_pl, result_ti;
    public static String pl;
    public static String ti;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        button3 = findViewById(R.id.button3);
        button_back = findViewById(R.id.button_back);
        result_food = findViewById(R.id.result_food);
        result_amount = findViewById(R.id.result_amount);
        result_ca = findViewById(R.id.result_ca);
        result_pl = findViewById(R.id.result_pl);
        result_ti = findViewById(R.id.result_ti);

//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();


//        String food = bundle.getString("naa");
//        String place = bundle.getString("pla");
//        double car = bundle.getDouble("car");
//        String time = bundle.getString("ti");
//        int amount = bundle.getInt("amm");
        result_food.setText(" " + MainActivity.na);
        result_amount.setText(" " + MainActivity.am + "인분");
        result_pl.setText(" " + pl);
        result_ca.setText(" " + MainActivity.ca*MainActivity.am);
        result_ti.setText(" " + ti);


        button3.setOnClickListener(view -> {
            Intent intent3 = new Intent(third.this, MainActivity.class);
            startActivity(intent3);
        });

        button_back.setOnClickListener(view -> {
            Intent intent3 = new Intent(third.this, second.class);
            startActivity(intent3);
        });
    }
}