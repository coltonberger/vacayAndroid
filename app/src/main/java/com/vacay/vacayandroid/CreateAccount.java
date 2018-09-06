package com.vacay.vacayandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CreateAccount extends AppCompatActivity {

    //public TextView loginPage;
    //public TextView login;

    private EditText userFirstName, userLastName, userEmail, userPassword;
    private TextView createAccountButton;
    private TextView goBackButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        setupUIViews();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    //upload info to database
                }
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccount.this, Login.class));
            }
        });
    }

    private void setupUIViews() {
        userFirstName = (EditText) findViewById(R.id.firstName);
        userLastName = (EditText) findViewById(R.id.lastName);
        userEmail = (EditText) findViewById(R.id.email);
        userPassword = (EditText) findViewById(R.id.password);
        createAccountButton = (TextView) findViewById(R.id.createAccount);
        goBackButton = (TextView) findViewById(R.id.goBack);
    }

    //validate if everything is filled out
    private Boolean validate() {
        Boolean result = false;

        String firstName = userFirstName.getText().toString();
        String lastName = userLastName.getText().toString();
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_LONG).show();
        } else {
            result = true;
        }

        return result;
    }

}
