//Экран входа в приложение.
//Здесь вводится логин и пароль пользователя.
//Есть кнопка, позволяющая перейти на окно регестрации пользователя.
//Данные отправляются и получаются с сервера.
package com.example.demo2;

//Библиотеки, требуемые для класса
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //Объявление переменных
    EditText username, password;//Переменные для ввода текста.
    Button btnLogin,btnSignUp ;//Переменные для кнопок

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Нахождение элементов черех "Найти через айди"
        username = findViewById(R.id.input_Login);//Поле ввода логина
        password = findViewById(R.id.input_password);//Поле ввода пароля
        btnLogin = findViewById(R.id.SignIn_btn);//Кнопка "вход"
        btnSignUp = findViewById(R.id.CreateAccount);//Кнопка "Зарегистрироваться"

        btnSignUp.setOnClickListener(new View.OnClickListener() { //При нажатии кнопки "Зарегистрироваться"
            @Override
            public void onClick(View v) {
                //Переход на окно регистрации
                Intent intent;
                intent = new Intent(MainActivity.this, SignUp.class);//Переход на окно регистрации
                startActivity(intent);
            }
        });

        //Проверка пустые ли поля входа
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(TextUtils.isEmpty(username.getText().toString())||TextUtils.isEmpty(password.getText().toString())){
                   Toast.makeText(MainActivity.this, "Username / Password Required", Toast.LENGTH_LONG).show(); //да пустые
               }else{
                   login(); //Нет, не пустые, - выполнить вход
               }
            }
        });


    }
//обращение на сервер и выполнение входа
    public void login(){
        LoginRequest loginRequest = new LoginRequest();//Создание запроса-массива
        loginRequest.setUsername(username.getText().toString());//1 член массива - логин
        loginRequest.setPassword(password.getText().toString());//2 член массива - пароль
        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);//Сформировать запрос
        loginResponseCall.enqueue(new Callback<LoginResponse>() {//Вызвать запрос
            @Override
            //проверка данных
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){        //Данные верны
                    Toast.makeText(MainActivity.this, "Добро пожаловать", Toast.LENGTH_LONG).show();//Сообщение о входе в приложение
                    Intent intent;
                    intent = new Intent(MainActivity.this, StartScreen.class);//Переход на следущий экран
                    startActivity(intent);
                }else {     //данные не верны
                    Toast.makeText(MainActivity.this, "Не правильный логин или пароль", Toast.LENGTH_LONG).show();//Сообщение о провальном входе в приложение
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) { //Ошибка доступа к серверу
                Toast.makeText(MainActivity.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();////Сообщение об ошибке со стороны сервера или приложения

            }
        });
    }


}