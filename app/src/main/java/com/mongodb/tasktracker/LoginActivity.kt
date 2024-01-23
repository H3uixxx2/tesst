package com.mongodb.tasktracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.realm.mongodb.Credentials
/*
* LoginActivity: launched whenever a user isn't already logged in. Allows a user to enter email
* and password credentials to log in to an existing account or create a new account.
*/
class LoginActivity : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        username = findViewById(R.id.input_username)
        password = findViewById(R.id.input_password)
        loginButton = findViewById(R.id.button_login)

        loginButton.setOnClickListener { login() }
    }

    override fun onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true)
    }

    private fun onLoginSuccess() {
        // successful login ends this activity, bringing the user back to the project activity
        finish()
    }

    private fun onLoginFailed(errorMsg: String) {
        Log.e(LoginActivity::class.java.simpleName, errorMsg)
        Toast.makeText(baseContext, errorMsg, Toast.LENGTH_LONG).show()
        loginButton.isEnabled = true // Re-enable the login button
    }

    private fun validateCredentials(): Boolean =
        username.text.toString().isNotEmpty() && password.text.toString().isNotEmpty()

    private fun login() {
        if (!validateCredentials()) {
            onLoginFailed("Invalid username or password")
            return
        }

        loginButton.isEnabled = false

        val username = this.username.text.toString()
        val password = this.password.text.toString()

        val creds = Credentials.emailPassword(username, password)
        taskApp.loginAsync(creds) {
            if (!it.isSuccess) {
                onLoginFailed(it.error.message ?: "An error occurred.")
            } else {
                onLoginSuccess()
            }
        }
    }
}
