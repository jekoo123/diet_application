package com.example.otaku;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    public UserDatabaseHelper(Context context) {
        super(context, "groupDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE foodlist ( date String, foodname String, amount int, calorie double, place String, comment String, latitude double, longitude double );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS foodlist");
        onCreate(db);

    }


}
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.os.Bundle;
//
//public class UserDatabaseHelper extends SQLiteOpenHelper {
//    private static UserDatabaseHelper instance;
//
//    public static synchronized UserDatabaseHelper getInstance(Context context){
//        if(instance == null){
//            instance = new UserDatabaseHelper(context,"User" , null,1);
//        }
//        return instance;
//    }
//
//
//    public static final int DATABASE_VERSION = 1;
//    public static final String DATABASE_NAME = "User.db";
//    public static final String TABLE_NAME = "food list";
//    public static final String COULUMN_FOOD_NAME = "food name";
//    public static final String COULUMN_FOOD_CALORIE = "food calorie";
//    public static final String COULUMN_LATITUDE = "latitude";
//    public static final String COULUMN_LONGITUDE = "longitude";
//
//    public static final String SQL_CREATE_USER =
//           "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
//           COULUMN_FOOD_NAME + " TEXT," +
//           COULUMN_FOOD_CALORIE + " TEXT," +
//           COULUMN_LATITUDE + " TEXT," +
//           COULUMN_LONGITUDE + " TEXT" + ");";
//    private UserDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
//        super(context, name, factory, version);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(SQL_CREATE_USER);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if(newVersion > 1){
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME);
//        }
//    }
//
//
//    //@Override
//    //protected void onCreate(Bundle savedInstanceState) {
//    //    super.onCreate(savedInstanceState);
//    //    setContentView(R.layout.activity_user_database);
//    //}
//}