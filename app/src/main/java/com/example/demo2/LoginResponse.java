//Класс, который получает большой массив данных из сервера

package com.example.demo2;

public class LoginResponse {

    private int user_id;

    public int getUser_id() {
        return user_id;//Получить порядковый номер пользователя на сервере
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }//Вставить в эемент массива айди

    public String getEmail() {
        return email;
    }//Получить почту пользователя

    public void setEmail(String email) {
        this.email = email;
    }//Вставить в эемент массива почту

    public String getUsername() {
        return username;
    }//Получить логин пользователя

    public void setUsername(String username) {
        this.username = username; //Вставить в эемент массива логин
    }

    private String email;
    private String username;


}
