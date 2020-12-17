//Окно настроек
//Здесь отображаются данные о машинах
package com.example.demo2;
//Библиотеки, требуемые для класса
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class History extends AppCompatActivity {
//Переменные кнопок для перехода на другие окна
    ImageButton imageButton;//кнопка возврата на экран с картой

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        imageButton = (ImageButton) findViewById(R.id.button_back);// нахождение кнопки

        imageButton.setOnClickListener(new View.OnClickListener() {//Обработчик нажатия на кнопку
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(History.this, StartScreen.class);//С кокого класса в какой будет переход
                startActivity(intent);//Активация перехода
            }
        });
    }

}