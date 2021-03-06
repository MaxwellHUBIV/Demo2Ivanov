package com.example.demo2;

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

    EditText username, password;
    Button btnLogin,btnSignUp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Нахождение элементов черех "Найти через айди"
        username = findViewById(R.id.input_Login);
        password = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.SignIn_btn);
        btnSignUp = findViewById(R.id.CreateAccount);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Переход на окно регистрации
                Intent intent;
                intent = new Intent(MainActivity.this, SignUp.class);
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
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());
        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            //проверка данных
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){        //Данные верны
                    Toast.makeText(MainActivity.this, "Добро пожаловать", Toast.LENGTH_LONG).show();
                    Intent intent;
                    intent = new Intent(MainActivity.this, StartScreen.class);
                    startActivity(intent);
                }else {     //данные не верны
                    Toast.makeText(MainActivity.this, "Не правильный логин или пароль", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) { //Ошибка доступа к серверу
                Toast.makeText(MainActivity.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


}