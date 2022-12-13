package com.example.otaku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class analyze extends AppCompatActivity {
    UserDatabaseHelper userDatabaseHelper;
    SQLiteDatabase sqlDB = null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        Button button_back3 = findViewById(R.id.button_back3);
        TextView textview_car = findViewById(R.id.textView_car);
        TextView textView_konomi = findViewById(R.id.textView_konomi);
        button_back3.setOnClickListener(view -> {
            Intent intent = new Intent(analyze.this, MainActivity.class);
            startActivity(intent);
        });

        userDatabaseHelper = new UserDatabaseHelper(this);
        sqlDB = userDatabaseHelper.getReadableDatabase();

        int count = 0;
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM foodlist;", null);
        cursor.moveToNext();
        String temp = cursor.getString(0);
        //ColumnIndex 0 에는 날짜가 들어있고, 첫번째 날짜를 temp 변수로 받아 놓는다.
        cursor.moveToPrevious();
        ArrayList<String> find = new ArrayList<>();
        ArrayList<Double> num = new ArrayList<>();
        double love = 0.0;
        while (cursor.moveToNext()) { //커서가 처음부터 끝까지 움직이면서
            find.add(cursor.getString(1));
            if (cursor.getString(0).equals(temp)) {}
            else{                   //날짜가 달라지면 temp를 그 날짜로 초기화 하고
                temp = cursor.getString(0);
                num.add(love);      //love로 받아 놓은 그 날의 칼로리 총량을 num이라는 arraylist에 넣는다.
                love = 0.0;         //날짜가 바뀌면 love를 0으로 다시 초기화한다.
                count++;            //count로 어제까지의 날짜를 센다.
            }
            love += cursor.getDouble(3); //ColumnIndex 3 은 칼로리이다.

        }

        int[] tem = new int[find.size()];

        for(int t = 0 ; t <find.size(); t++){
            tem[t] = 1;
        }

        for(int u = 0 ; u < tem.length; u++){
            for(int k = u+1 ; k < tem.length-1 ; k++){
                if(find.get(u).equals(find.get(k))){
                    tem[u] = tem[u]+1;
                }
            }
        }

        int max1 = tem[0];
        int ind = 0;
        for(int u = 0 ; u < tem.length ; u++){
            if(max1 <= tem[u]){
                max1 = tem[u];
                ind = u;

            }
        }


        double result = 0.0;

        for (int i = 0; i < num.size(); i++) {
            result += num.get(i);
        }
        //일별 칼로리를 넣어놓은 num에서 요소를 받아와서 그 합을 result에 넣는다.

        cursor.close();
        sqlDB.close();
        textview_car.setText(Math.round(result/count)+ "kcal"); //result(합) count(날짜)를 나누어서 일평균 칼로리를 출력한다.
        textView_konomi.setText(find.get(ind));

    }
}