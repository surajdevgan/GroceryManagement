package com.surajdev.grocerymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText Email, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.username);
        Password = findViewById(R.id.llpassword);
    }

    public void Login(View view) {

        startActivity(new Intent(this, HomeActivity.class));

        if(Email.getText().toString().equals("admin@123") & Password.getText().toString().equals("admin123"))
        {
            startActivity(new Intent(this, HomeActivity.class));
        }

        else {

            Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_LONG).show();
        }


    }
}