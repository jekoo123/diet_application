package com.example.otaku;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.atomic.AtomicReference;


public class MapsActivity2 extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    public double lat;
    public double lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        Button button_yes = findViewById(R.id.button_yes);
        Button button_back1 = findViewById(R.id.button_back1);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        button_yes.setOnClickListener(view -> {
            Intent intent = new Intent(MapsActivity2.this, second.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lon", lon);
            startActivity(intent);


        });

        button_back1.setOnClickListener(view -> {
            Intent intent = new Intent(MapsActivity2.this, second.class);
            startActivity(intent);
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMapClickListener(love -> {
            googleMap.clear();
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(love));
            MarkerOptions newMarker=new MarkerOptions();
            newMarker.position(love);
            googleMap.addMarker(newMarker);
            lat = love.latitude;
            lon = love.longitude;
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            checkLocationPermissionWithRationale();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermissionWithRationale() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("위치정보")
                        .setMessage("이 앱을 사용하기 위해서는 위치정보에 접근이 필요합니다. 위치정보 접근을 허용하여 주세요.")
                        .setPositiveButton("확인", (dialogInterface, i) -> ActivityCompat.requestPermissions(MapsActivity2.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION)).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


}

//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.Circle;
//import com.google.android.gms.maps.model.CircleOptions;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//
//public class MapsActivity2 extends AppCompatActivity {
//    private Button button1, button2;
//    private TextView textView1;
//    LocationManager manager;
//    GPSListener gpsListener;
//
//    SupportMapFragment mapFragment;
//    GoogleMap map;
//
//    Marker myMarker;
//    MarkerOptions myLocationMarker;
//    Circle circle;
//    CircleOptions circle1KM;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        setTitle("GPS 현재위치 확인하기");
//
//        button1 = findViewById(R.id.button1);
//        button2 = findViewById(R.id.button2);
//        textView1 = findViewById(R.id.textView1);
//
//        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        gpsListener = new GPSListener();
//
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // 지도 보임
//                mapFragment.getView().setVisibility(View.VISIBLE);
//                startLocationService();
//            }
//        });
//
//    }
//
//    public void startLocationService() {
//        try {
//            Location location = null;
//
//            long minTime = 0;        // 0초마다 갱신 - 바로바로갱신
//            float minDistance = 0;
//
//            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//                if (location != null) {
//                    double latitude = location.getLatitude();
//                    double longitude = location.getLongitude();
//                    String message = "최근 위치1 -> Latitude : " + latitude + "\n Longitude : " + longitude;
//
//                    textView1.setText(message);
//                    showCurrentLocation(latitude, longitude);
//                    Log.i("MyLocTest", "최근 위치1 호출");
//                }
//
//                //위치 요청하기
//                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
//                //manager.removeUpdates(gpsListener);
//                Toast.makeText(getApplicationContext(), "내 위치1확인 요청함", Toast.LENGTH_SHORT).show();
//                Log.i("MyLocTest", "requestLocationUpdates() 내 위치1에서 호출시작 ~~ ");
//
//            } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//
//                //위치 요청하기
//                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, gpsListener);
//                //manager.removeUpdates(gpsListener);
//                Toast.makeText(getApplicationContext(), "내 위치2확인 요청함", Toast.LENGTH_SHORT).show();
//                Log.i("MyLocTest","requestLocationUpdates() 내 위치2에서 호출시작 ~~ ");
//            }
//
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    class GPSListener implements LocationListener {
//
//        // 위치 확인되었을때 자동으로 호출됨 (일정시간 and 일정거리)
//        @Override
//        public void onLocationChanged(Location location) {
//            double latitude = location.getLatitude();
//            double longitude = location.getLongitude();
//            String message = "내 위치는 Latitude : " + latitude + "\nLongtitude : " + longitude;
//            textView1.setText(message);
//
//            showCurrentLocation(latitude,longitude);
//            Log.i("MyLocTest","onLocationChanged() 호출되었습니다.");
//        }
//    }
//
//    private void showCurrentLocation(double latitude, double longitude) {
//        LatLng curPoint = new LatLng(latitude, longitude);
//        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
//        showMyLocationMarker(curPoint);
//    }
//
//    private void showMyLocationMarker(LatLng curPoint) {
//        if (myLocationMarker == null) {
//            myLocationMarker = new MarkerOptions(); // 마커 객체 생성
//            myLocationMarker.position(curPoint);
//            myLocationMarker.title("최근위치 \n");
//            myLocationMarker.snippet("*GPS 확인한 최근위치");
//            myMarker = map.addMarker(myLocationMarker);
//        } else {
//            myMarker.remove(); // 마커삭제
//            myLocationMarker.position(curPoint);
//            myMarker = map.addMarker(myLocationMarker);
//        }
//
//        // 반경추가
//        if (circle1KM == null) {
//            circle1KM = new CircleOptions().center(curPoint) // 원점
//                    .radius(1000)       // 반지름 단위 : m
//                    .strokeWidth(1.0f);    // 선너비 0f : 선없음
//            //.fillColor(Color.parseColor("#1AFFIX")); // 배경색
//            circle = map.addCircle(circle1KM);
//
//        } else {
//            circle.remove(); // 반경삭제
//            circle1KM.center(curPoint);
//            circle = map.addCircle(circle1KM);
//        }
//
//
//    }
//
//
////}
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.os.Looper;
//import android.util.Log;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.Toast;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.material.snackbar.Snackbar;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//
//
//public class MapsActivity2 extends AppCompatActivity
//        implements OnMapReadyCallback,
//        ActivityCompat.OnRequestPermissionsResultCallback{
//
//
//    private GoogleMap mMap;
//    private Marker currentMarker = null;
//
//    private static final String TAG = "google map_example";
//    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
//    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
//    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초
//
//
//    private static final int PERMISSIONS_REQUEST_CODE = 100;
//    boolean needRequest = false;
//
//
//    // 앱을 실행하기 위해 필요한 퍼미션을 정의합니다.
//    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};  // 외부 저장소
//
//
//    Location mCurrentLocatiion;
//    LatLng currentPosition;
//
//
//    private FusedLocationProviderClient mFusedLocationClient;
//    private LocationRequest locationRequest;
//
//
//    private View mLayout;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
//                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//        setContentView(R.layout.activity_main);
//
//        mLayout = findViewById(R.id.map);
//
//        locationRequest = new LocationRequest()
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                .setInterval(UPDATE_INTERVAL_MS)
//                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);
//
//
//        LocationSettingsRequest.Builder builder =
//                new LocationSettingsRequest.Builder();
//
//        builder.addLocationRequest(locationRequest);
//
//
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        assert mapFragment != null;
//        mapFragment.getMapAsync(this);
//    }
//
//    @Override
//    public void onMapReady(@NonNull final GoogleMap googleMap) {
//        Log.d(TAG, "onMapReady :");
//
//        mMap = googleMap;
//
//        setDefaultLocation();
//
//
//        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION);
//        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION);
//
//
//
//        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
//                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED   ) {
//
//
//            startLocationUpdates();
//
//
//        }else {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
//
//                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
//                Snackbar.make(mLayout, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.",
//                        Snackbar.LENGTH_INDEFINITE).setAction("확인", view -> ActivityCompat.requestPermissions( MapsActivity2.this, REQUIRED_PERMISSIONS,
//                                PERMISSIONS_REQUEST_CODE)).show();
//
//
//            } else {
//
//                ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS,
//                        PERMISSIONS_REQUEST_CODE);
//            }
//
//        }
//
//
//
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//
//        mMap.setOnMapClickListener(latLng -> Log.d( TAG, "onMapClick :"));
//    }
//
//    LocationCallback locationCallback = new LocationCallback() {
//        @Override
//        public void onLocationResult(@NonNull LocationResult locationResult) {
//            super.onLocationResult(locationResult);
//
//            List<Location> locationList = locationResult.getLocations();
//
//            if (locationList.size() > 0) {
//                Location location = locationList.get(locationList.size() - 1);
//                //location = locationList.get(0);
//
//                currentPosition
//                        = new LatLng(location.getLatitude(), location.getLongitude());
//
//
//                String markerTitle = getCurrentAddress(currentPosition);
//                String markerSnippet = "위도:" + location.getLatitude()
//                        + " 경도:" + location.getLongitude();
//
//                Log.d(TAG, "onLocationResult : " + markerSnippet);
//
//
//                //현재 위치에 마커 생성하고 이동
//                setCurrentLocation(location, markerTitle, markerSnippet);
//
//                mCurrentLocatiion = location;
//            }
//
//
//        }
//
//    };
//
//
//
//    private void startLocationUpdates() {
//
//        if (!checkLocationServicesStatus()) {
//
//            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
//            showDialogForLocationServiceSetting();
//        }else {
//
//            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION);
//            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION);
//
//
//
//            if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
//                    hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED   ) {
//
//                Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음");
//                return;
//            }
//
//
//            Log.d(TAG, "startLocationUpdates : call mFusedLocationClient.requestLocationUpdates");
//
//            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
//
//            if (checkPermission())
//                mMap.setMyLocationEnabled(true);
//
//        }
//
//    }
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        Log.d(TAG, "onStart");
//
//        if (checkPermission()) {
//
//            Log.d(TAG, "onStart : call mFusedLocationClient.requestLocationUpdates");
//            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
//
//            if (mMap!=null)
//                mMap.setMyLocationEnabled(true);
//
//        }
//
//
//    }
//
//
//    @Override
//    protected void onStop() {
//
//        super.onStop();
//
//        if (mFusedLocationClient != null) {
//
//            Log.d(TAG, "onStop : call stopLocationUpdates");
//            mFusedLocationClient.removeLocationUpdates(locationCallback);
//        }
//    }
//
//
//
//
//    public String getCurrentAddress(LatLng latlng) {
//
//        //지오코더... GPS 주소로 변환
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//
//        List<Address> addresses;
//
//        try {
//
//            addresses = geocoder.getFromLocation(
//                    latlng.latitude,
//                    latlng.longitude,
//                    1);
//        } catch (IOException ioException) {
//            //네트워크 문제
//            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
//            return "지오코더 서비스 사용불가";
//        } catch (IllegalArgumentException illegalArgumentException) {
//            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
//            return "잘못된 GPS 좌표";
//
//        }
//
//
//        if (addresses == null || addresses.size() == 0) {
//            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
//            return "주소 미발견";
//
//        } else {
//            Address address = addresses.get(0);
//            return address.getAddressLine(0);
//        }
//
//    }
//
//
//    public boolean checkLocationServicesStatus() {
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//    }
//
//
//    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {
//
//
//        if (currentMarker != null) currentMarker.remove();
//
//
//        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(currentLatLng);
//        markerOptions.title(markerTitle);
//        markerOptions.snippet(markerSnippet);
//        markerOptions.draggable(true);
//
//
//        currentMarker = mMap.addMarker(markerOptions);
//
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
//        mMap.moveCamera(cameraUpdate);
//
//    }
//
//
//    public void setDefaultLocation() {
//
//
//        //디폴트 위치, Seoul
//        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
//        String markerTitle = "위치정보 가져올 수 없음";
//        String markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요";
//
//
//        if (currentMarker != null) currentMarker.remove();
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(DEFAULT_LOCATION);
//        markerOptions.title(markerTitle);
//        markerOptions.snippet(markerSnippet);
//        markerOptions.draggable(true);
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//        currentMarker = mMap.addMarker(markerOptions);
//
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
//        mMap.moveCamera(cameraUpdate);
//
//    }
//
//
//    //여기부터는 런타임 퍼미션 처리을 위한 메소드들
//    private boolean checkPermission() {
//
//        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION);
//        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION);
//
//
//        return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
//                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED;
//
//    }
//
//
//
//    @Override
//    public void onRequestPermissionsResult(int permsRequestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grandResults) {
//
//        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
//        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
//
//            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
//
//            boolean check_result = true;
//
//
//            // 모든 퍼미션을 허용했는지 체크합니다.
//
//            for (int result : grandResults) {
//                if (result != PackageManager.PERMISSION_GRANTED) {
//                    check_result = false;
//                    break;
//                }
//            }
//
//
//            if (check_result) {
//
//                // 퍼미션을 허용했다면 위치 업데이트를 시작합니다.
//                startLocationUpdates();
//            } else {
//                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
//
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
//                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
//
//
//                    // 사용자가 거부만 선택한 경우에는 앱을 다시 실행하여 허용을 선택하면 앱을 사용할 수 있습니다.
//                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
//                            Snackbar.LENGTH_INDEFINITE).setAction("확인", view -> finish()).show();
//
//                } else {
//
//
//                    // "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우에는 설정(앱 정보)에서 퍼미션을 허용해야 앱을 사용할 수 있습니다.
//                    Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
//                            Snackbar.LENGTH_INDEFINITE).setAction("확인", view -> finish()).show();
//                }
//            }
//
//        }
//    }
//
//
//    //여기부터는 GPS 활성화를 위한 메소드들
//    private void showDialogForLocationServiceSetting() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity2.this);
//        builder.setTitle("위치 서비스 비활성화");
//        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
//                + "위치 설정을 수정하실래요?");
//        builder.setCancelable(true);
//        builder.setPositiveButton("설정", (dialog, id) -> {
//            Intent callGPSSettingIntent
//                    = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
//        });
//        builder.setNegativeButton("취소", (dialog, id) -> dialog.cancel());
//        builder.create().show();
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == GPS_ENABLE_REQUEST_CODE) {//사용자가 GPS 활성 시켰는지 검사
//            if (checkLocationServicesStatus()) {
//                if (checkLocationServicesStatus()) {
//
//                    Log.d(TAG, "onActivityResult : GPS 활성화 되있음");
//
//
//                    needRequest = true;
//
//                }
//            }
//        }
//    }
//
//
//
//}


//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Bundle;
//
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.fragment.app.FragmentActivity;
//
//import com.example.otaku.databinding.ActivityMaps2Binding;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.UiSettings;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnSuccessListener;
//
//
//public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback,
//        GoogleMap.OnMyLocationButtonClickListener,
//        GoogleMap.OnMarkerClickListener {
//    private ActivityMaps2Binding binding;
//
//
//    private FusedLocationProviderClient fusedLocationClient;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(MapsActivity2.this);
//
//
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        GoogleMap mMap = googleMap;
//        mMap.setOnMarkerClickListener(MapsActivity2.this);
//        mMap.setOnMyLocationButtonClickListener(MapsActivity2.this);
//        mMap.setMyLocationEnabled(true);
//        UiSettings mUiSettings = googleMap.getUiSettings();
//        mUiSettings.setZoomControlsEnabled(true);
//        mUiSettings.setMyLocationButtonEnabled(true);
//
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//
//                        if (location == null) return ;
//
//                        double lat = location.getLatitude();
//                        double lon = location.getLongitude();
//
//                        LatLng sydney = new LatLng(lat, lon);
//                        Object markerBinding = null;
//                        String placeName = null;
//                        markerBinding.setText(placeName);
//                        mMap.addMarker(new MarkerOptions().position(sydney).title(placeName)
//                                .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getApplicationContext(), markView))));
//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 30));
//
//
//
//                    }
//                });
//    }
//
//
//    @Override
//    public boolean onMarkerClick(@NonNull Marker marker) {
//        return false;
//    }
//
//    @Override
//    public boolean onMyLocationButtonClick() {
//        return false;
//    }
//}