package com.example.otaku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class list extends AppCompatActivity {
    UserDatabaseHelper userDatabaseHelper;
    SQLiteDatabase sqlDB = null;
  //  Button button_back = findViewById(R.id.button_back);
//    private ListView list01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Button button_map = findViewById(R.id.button_map);
        Button button_back = findViewById(R.id.button_back);
        Button button_calander = findViewById(R.id.button_calender);
        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;
        recyclerView = findViewById(R.id.recyclerlist);
        userDatabaseHelper = new UserDatabaseHelper(this);
        sqlDB = userDatabaseHelper.getReadableDatabase();
//        Cursor cursor = sqlDB.rawQuery("SELECT * FROM foodlist;",null);
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> amount = new ArrayList<>();
        ArrayList<String> cal = new ArrayList<>();
        ArrayList<String> place = new ArrayList<>();

        button_back.setOnClickListener(view -> {
            Intent intent = new Intent(list.this, MainActivity.class);
            startActivity(intent);
        });
        button_map.setOnClickListener(view -> {
            Intent intent = new Intent(list.this, Maps1.class);
            startActivity(intent);
        });
        button_calander.setOnClickListener(view -> {
            Intent intent = new Intent(list.this, calender.class);
            startActivity(intent);
        });


        userDatabaseHelper = new UserDatabaseHelper(this);
        sqlDB = userDatabaseHelper.getReadableDatabase();

        Cursor cursor = sqlDB.rawQuery("SELECT * FROM foodlist;",null);
       // String strNames = "목록 리스트"+"\r\n"+"\r\n";
       // String strNumbers = "수량"+"\r\n"+"\r\n";
//        list01 = findViewById(R.id.list01);
//        List<String> data = new ArrayList<>();
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1,data);
        while (cursor.moveToNext()){
            date.add(cursor.getString(0));
            name.add(cursor.getString(1));
            amount.add(cursor.getString(2));
            cal.add(cursor.getString(3));
            place.add(cursor.getString(4));
        }
        String[] array = new String[date.size()];
        String[] array2 = new String[name.size()];
        String[] array3 = new String[amount.size()];
        String[] array4 = new String[cal.size()];
        String[] array5 = new String[place.size()];
        int size=0;
        for(String temp : date){
            array[size++] = temp;
        }
        size=0;
        for(String temp : name){
            array2[size++] = temp;
        }
        size=0;
        for(String temp : amount){
            array3[size++] = temp;
        }
        size=0;
        for(String temp : cal){
            array4[size++] = temp;
        }
        size=0;
        for(String temp : place){
            array5[size++] = temp;
        }

//        while (cursor.moveToNext()){
//            String str = "";
//            str += cursor.getString(0) + "   ";
//            str += cursor.getString(1) + "   ";
//            str += cursor.getString(2) + "   ";
//            str += cursor.getString(3) + "   ";
//            str += cursor.getString(4) + "   ";
////            list01.setAdapter(adapter);
////            data.add(str);
//        }
        adapter = new CustomAdapter2(array,array2,array3,array4,array5);
        recyclerView.setAdapter(adapter);
//        edtNameResultm.setText(strNames);
//        edtNumberResult.setText(strNumbers);
        cursor.close();
        sqlDB.close();

    //    button_back.setOnClickListener(view -> {
    //        Intent intent2 = new Intent(list.this, MainActivity.class);
    //        startActivity(intent2);
    //    });

    }




}