package com.example.otaku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.ArrayList;

public class calender extends AppCompatActivity {
    UserDatabaseHelper userDatabaseHelper;
    SQLiteDatabase sqlDB = null;
//    String y,m,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        TextView textView = findViewById(R.id.textView);
        CalendarView calendarView = findViewById(R.id.calendarView);
        Button button_back = findViewById(R.id.button_back);
        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;
        button_back.setOnClickListener(view -> {
            Intent intent = new Intent(calender.this, list.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.recyclerMemo);
        userDatabaseHelper = new UserDatabaseHelper(this);
        sqlDB = userDatabaseHelper.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM foodlist;",null);
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> cal = new ArrayList<>();

//        String[] main_text =  {"1","2", "3", "4","5","6", "7", "8","9","10"};
//        String[] main_text2 =  {"test1","test2","test3","test4","test5","test6","test7","test8","test9","test10"};
        while (cursor.moveToNext()){
            date.add(cursor.getString(0));
            name.add(cursor.getString(1));
            cal.add(cursor.getString(3));
        }
        String[] array = new String[date.size()];
        String[] array2 = new String[name.size()];
        String[] array3 = new String[cal.size()];

        int size=0;
        for(String temp : date){
            array[size++] = temp;
        }

        size=0;
        for(String temp : name){
            array2[size++] = temp;
        }
        size=0;
        for(String temp : cal){
            array3[size++] = temp;
        }

        cursor.close();
        sqlDB.close();

//        String[][] splitdate = new String[date.size()][3];
//        int index=0;
//        for(String temp : array){
//            splitdate[index] = temp.split("-");
//            index++;
//        }



        adapter = new CustomAdapter(array,array2,array3);
        recyclerView.setAdapter(adapter);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOFMonth) {
                month += 1;
                textView.setText(String.format("%d년 %d월 %d일" , year, month, dayOFMonth));

//                y = Integer.toString(year);
//                m = Integer.toString(month);
//                d = Integer.toString(dayOFMonth);
//                RecyclerView.Adapter adapter2;
//                //날짜가 같은 것들만 가져오게
//                int count = 1 , count2 = 0;
////                while(y == splitdate[t][0] && m == splitdate[t][1] && d == splitdate[t][2]){
////                    t++;
////                }
//
//                for(int t=0 ; t<date.size();t++){
//                    if(y == splitdate[t][0] && m == splitdate[t][1] && d == splitdate[t][2]){
//                        count++;
//
//                    }
//                    else{
//                        if(count == 1) {
//                            count2++;
//                        }
//                    }
//                }
//                textView.setText(splitdate[0][2]);
////                String a = Integer.toString(t);
////                textView.setText(a);
//                String[] n_array = new String[count];
//                String[] n_array2 = new String[count];
//                String[] n_array3 = new String[count];
//                for(int i = 0 ; i<count ; i++){
//                    n_array[i] = array[i];
//                    n_array2[i] = array2[i];
//                    n_array3[i] = array3[i];
//                }
//                adapter2 = new CustomAdapter(n_array,n_array2,n_array3);
//                recyclerView.setAdapter(adapter2);
            }
        });


    }
}