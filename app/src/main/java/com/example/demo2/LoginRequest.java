//Класс, позволяющий отправлять и получать тестовые данные.
package com.example.demo2;

public class LoginRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }//Получить логин

    public void setUsername(String username) {
        this.username = username;
    }//Отправить логин

    public String getPassword() {
        return password;
    }//Получить пароль

    public void setPassword(String password) {
        this.password = password;
    }//Отправить пароль
}
