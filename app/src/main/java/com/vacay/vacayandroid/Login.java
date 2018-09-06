package com.vacay.vacayandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    public TextView login;
    public TextView createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfile();
            }
        });

        createAccount = findViewById(R.id.newUser);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp();
            }
        });
    }

    public void openProfile() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openSignUp() {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }


}
