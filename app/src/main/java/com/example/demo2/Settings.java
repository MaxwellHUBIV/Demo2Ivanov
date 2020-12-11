//Окно настроек
//Здесь отображаются данные о пользователе
package com.example.demo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Settings extends AppCompatActivity {
    //Переменные кнопок для перехода на другие окна
    ImageButton imageButton2; //кнопка возврата на экран с картой
    Button exitbtn; //Кнока выхода из аккаунта

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        imageButton2 = (ImageButton) findViewById(R.id.buttonback2); // нахождение кнопки
        exitbtn = findViewById(R.id.exitbtn);                        // нахождение кнопки

        imageButton2.setOnClickListener(new View.OnClickListener() { //Обработчик нажатия на кнопку
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, StartScreen.class); //С кокого класса в какой будет переход
                startActivity(intent); //Активация перехода
            }
        });

        exitbtn.setOnClickListener(new View.OnClickListener() { //Обработчик нажатия на кнопку
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, MainActivity.class); //С кокого класса в какой будет переход
                startActivity(intent); //Активация перехода
            }
        });
    }
}