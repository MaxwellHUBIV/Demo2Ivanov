//Главный экран приложения.
//На экране присутстует карта Google Maps.
//Используется Google Maps API.
//Карта автоматически наводится на ваше местоположение(Только с реального устройства, не эмулятора).
//Имеется боковое меню с кнопками для навигации.
//Открывается боковое мень посредством свайпа вправо.
//Карта масштабируется и имеет дополнительные элементы управления как компас и кнопки "+" и "-".

package com.example.demo2;

//Библиотеки, требуемые для класса
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
    //Переменные
    private GoogleMap mMap;//Переменная для карты, чтобы сократить и облегчить обращение к элементам GoogleMaps
    private Toolbar toolbar;//Переменная для элемента тулбар. Нужна для бокового меню.
    private DrawerLayout drawerLayout;//Подвязка самого бокового меню.
    private NavigationView navigationView;//Элемент, для работы свайпа.
    FusedLocationProviderClient client; //Переменная-массив для нахождения ваших координат.
    SupportMapFragment mapFragment; //Переменная для работы с картой.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);//Нахождение карты через айди
        mapFragment.getMapAsync(this);

        //находит локацию
        client = LocationServices.getFusedLocationProviderClient(this);//Получить локацию.
        //Проверяет разрешения
        if (ActivityCompat.checkSelfPermission(StartScreen.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {//если есть разрешения.
            getCurrentLocation();
        }else{
            ActivityCompat.requestPermissions(StartScreen.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);//Если разрешений нет, то идёт из запрос
        }


        toolbar = findViewById(R.id.toolbar);//тулбар для меню.
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout); //Нахождение бокового меню.
        navigationView = findViewById(R.id.navigationView);//Нахождение лемента для работы свайпа

//Какая кнопка была нажата в боковом меню
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.History:
                        //Переход на историю
                        item.setChecked(true);//Если нажат
                        startActivity(new Intent(StartScreen.this, History.class));//Активация перехода в экран истории
                        return true;
                    case R.id.settings:
                        //переход на настройки
                        item.setChecked(true);//Если нажат
                        startActivity(new Intent(StartScreen.this, Settings.class));//Активация перехода в экран настроек
                        return true;
                }

                return false;
            }
        });


    }

    //Получение координат
    private void getCurrentLocation() {//Проверка разрешений
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
            //Получение координат вашего местоположения
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

    @Override//Проверяет есть ли разрешения
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

    private void setSupportActionBar(Toolbar toolbar) {//Класс поддержка для ТулБара

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