package com.mongodb.tasktracker

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials


class MainActivity : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var anonymousLoginButton: Button
    private lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(this)

        app = App(AppConfiguration.Builder("finalproject-rujev").build())

        username = findViewById(R.id.input_username)
        password = findViewById(R.id.input_password)
        loginButton = findViewById(R.id.button_login)
        loginButton.setOnClickListener { login() }




        anonymousLoginButton = findViewById(R.id.button_anonymous_login)// nút đăng nhập acc fake

        anonymousLoginButton.setOnClickListener { anonymousLogin() }// đăng nhập acc fake
    }


    private fun login() {
        val userUsername = username.text.toString()
        val userPassword = password.text.toString()

        // Sử dụng Credentials.custom() nếu bạn triển khai phương thức xác thực tùy chỉnh
        // Đối với ví dụ này, tôi sẽ sử dụng emailPassword để minh họa
        app.loginAsync(Credentials.emailPassword(userUsername, userPassword)) { result ->
            if (result.isSuccess) {
                Log.v("AUTH", "Successfully logged in.")
                // Tiếp tục với việc chuyển đến một Activity khác hoặc cập nhật giao diện người dùng
            } else {
                Log.e("AUTH", "Failed to log in: ${result.error}")
                // Hiển thị thông báo lỗi
            }
        }
    }
    //update 26/1/2024

















    private fun anonymousLogin() {
        app.loginAsync(Credentials.anonymous()) { result ->
            if (result.isSuccess) {
                Log.v("AUTH", "Successfully logged in as an anonymous user.")
                // Tiếp tục với việc chuyển đến một Activity khác hoặc cập nhật giao diện người dùng
            } else {
                Log.e("AUTH", "Failed to log in anonymously: ${result.error}")
                // Xử lý lỗi đăng nhập
            }
        }
    }
}
