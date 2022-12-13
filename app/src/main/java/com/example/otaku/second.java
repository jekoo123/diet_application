package com.example.otaku;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class second extends AppCompatActivity {

    Button button2, button_map, button3 , button_camera;
    ImageView imageView;
    EditText comment;
    UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper(this);
    SQLiteDatabase sqlDB;
    EditText place;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button_map = findViewById(R.id.button_map);
        button_camera = findViewById(R.id.button_camera);
        imageView = findViewById(R.id.imageview);
        place = findViewById(R.id.place);
        comment = findViewById(R.id.comment);
        String com = comment.getText().toString();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

//        String name = bundle.getString("naa");
//        int amount = bundle.getInt("amm");
//        double car = bundle.getDouble("car");
//        sqlDB = userDatabaseHelper.getWritableDatabase();
//        userDatabaseHelper.onUpgrade(sqlDB,1,2);
//        sqlDB.close();


        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissionCheck = ContextCompat.checkSelfPermission(second.this, Manifest.permission.CAMERA);
                if(permissionCheck== PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(second.this,new String[]{Manifest.permission.CAMERA},0);

                }else{
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,1);
                }
            }
        });{


        }

        button2.setOnClickListener(view -> {
            Intent intent2 = new Intent(second.this, third.class);
            String place = this.place.getText().toString();
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String getTime = sdf.format(date);
            third.ti = getTime;
            third.pl = place;
            double lon = bundle.getDouble("lon");
            double lat = bundle.getDouble("lat");

            sqlDB = userDatabaseHelper.getWritableDatabase();
            sqlDB.execSQL("INSERT INTO foodlist VALUES ('" + getTime + "' , '" + MainActivity.na + "' , '" + MainActivity.am + "' , '" + MainActivity.ca*MainActivity.am + "' , '" + place + "', '" + com + "' , '" + lat + "' , " + lon +");");
            sqlDB.close();
            startActivity(intent2);
        });

        button3.setOnClickListener(view -> {
            Intent intent3 = new Intent(second.this, MainActivity.class);
            startActivity(intent3);
        });
        button_map.setOnClickListener(view -> {
            Intent intent3 = new Intent(second.this, MapsActivity2.class);
            startActivity(intent3);
        });




    }

//    public void takePicture(){
//        Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if(imageIntent.resolveActivity(getPackageManager()) != null){
//            startActivityForResult(imageIntent,REQUEST_IMAGE_CODE);
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode , @Nullable Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_IMAGE_CODE && resultCode == RESULT_OK){
//            Bundle extras = data.getExtras();
//            imageBitmap = (Bitmap)extras.get("data");
//            imageView.setImageBitmap(imageBitmap);
//        }
//
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)    {
        super.onActivityResult(requestCode, resultCode, data);

        // 카메라 촬영을 하면 이미지뷰에 사진 삽입
        if(requestCode == 1 && resultCode == RESULT_OK) {
            // Bundle로 데이터를 입력
            Bundle extras = data.getExtras();

            // Bitmap으로 컨버전
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // 이미지뷰에 Bitmap으로 이미지를 입력
            imageView.setImageBitmap(imageBitmap);
        }
    }
//
//    public void showCameraBtn(){
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivity(intent);
//    }
}