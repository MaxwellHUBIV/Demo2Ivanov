package com.example.demo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

public class StartScreen extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    FusedLocationProviderClient client;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //находит локацию
        client = LocationServices.getFusedLocationProviderClient(this);
        //Проверяет разрешения
        if (ActivityCompat.checkSelfPermission(StartScreen.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }else{
            ActivityCompat.requestPermissions(StartScreen.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
//Какая кнопка была нажата
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.History:
                        //Переход на историю
                        item.setChecked(true);
                        startActivity(new Intent(StartScreen.this, History.class));
                        //displayMessage("Import Selected");
                        //drawerLayout.closeDrawers();
                        return true;
                    case R.id.settings:
                        //переход на настройки
                        item.setChecked(true);
                        startActivity(new Intent(StartScreen.this, Settings.class));
                        return true;
                }

                return false;
            }
        });


    }

    //Получение координат
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            //Находит ширину и долготу
                            LatLng latLng = new LatLng(location.getLatitude(),
                                    location.getLongitude());
                            //зум камеры
                            MarkerOptions options = new MarkerOptions().position(latLng)
                                    .title("Вы здесь");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            //маркер на карте
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }
    //Сообщения
    private void displayMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setSupportActionBar(Toolbar toolbar) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Добавление элементов карты
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Масштабирование
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        //Компасс
        mMap.getUiSettings().setCompassEnabled(true);


    }

}