package com.vacay.vacayandroid;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextView loginButton, registerButton;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       email = (EditText)findViewById(R.id.email);
       password = (EditText)findViewById(R.id.password);
       loginButton = (TextView) findViewById(R.id.login);
       registerButton = (TextView) findViewById(R.id.newUser);

       firebaseAuth = FirebaseAuth.getInstance();
       progressDialog = new ProgressDialog(this);

//       If I want to keep user logged in on phone add this code
//       FirebaseUser user = firebaseAuth.getCurrentUser();
//       if (user != null) {
//           finish();
//           startActivity(new Intent(Login.this, MainActivity.class));
//       }

       loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               validate(email.getText().toString(), password.getText().toString());
           }
       });

       registerButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Login.this, CreateAccount.class);
               startActivity(intent);
           }
       });

    }

    private void validate(String userEmail, String userPassword) {

        progressDialog.setMessage("Validating with database");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Login.this, MainActivity.class));
                } else {
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
