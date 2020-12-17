//Класс, открывающий стартовое окно при запуске приложения.
//Длительность экрана, зависит от того, насколько мощное устройство.
//Если устройство новое, то экран будет показан очень быстро, и наоборот.

package com.example.demo2;

//Библиотеки, требуемые для класса
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this,MainActivity.class));//После загрузки класса MainActivity идёт автоматический переход на него.

    }
}