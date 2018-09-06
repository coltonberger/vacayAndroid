package com.vacay.vacayandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class CreateAccount extends AppCompatActivity {

    private EditText userFirstName, userLastName, userEmail, userPassword;
    private TextView createAccountButton;
    private TextView goBackButton;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    //upload info to database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText (CreateAccount.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CreateAccount.this, Login.class));
                            } else {
                                Toast.makeText (CreateAccount.this, "Sign up fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
