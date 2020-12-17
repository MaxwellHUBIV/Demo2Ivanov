//Интерфейс, который обращается на определённую часть на сервере
package com.example.demo2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

        @POST("authenticate/")//Ветка на сервере, к которой идёт обращение
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);//Вызов операции входа и отправка данных на проверку


}
