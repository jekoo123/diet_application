package com.example.otaku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {

    Button button, button_list , button_analyze;
    EditText name, amount;
    public static String na;
    public static int am;
    public static double ca;
    food fd = new food();

    private void readData(String name){
        InputStream is = getResources().openRawResource(R.raw.db1);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );
        String line;
        try{
            reader.readLine();

            while ((line = reader.readLine()) != null){
                String[] tokens = line.split(",");

                if (tokens[0].equals(name)){
                    fd.setName(tokens[0]);
                    fd.setCar(Double.parseDouble(tokens[1]));
                }
            }

        } catch (IOException e) {
            Log.d("MyActivity", "Error reading data file on line");
            e.printStackTrace();
        }

    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button_list = findViewById(R.id.button_list);
        button_analyze = findViewById(R.id.button_analyze);
        name = findViewById(R.id.name);

        amount = findViewById(R.id.amount);

        button.setOnClickListener(view -> {
            this.na = name.getText().toString();
            String temp = amount.getText().toString();
            am = Integer.parseInt(temp);
            readData(na);

            this.ca = fd.getCar();
            Intent intent = new Intent(MainActivity.this, second.class);
            startActivity(intent);
        });

        button_list.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, list.class);
            startActivity(intent);
        });


        button_analyze.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, analyze.class);
            startActivity(intent);
        });

    }

}