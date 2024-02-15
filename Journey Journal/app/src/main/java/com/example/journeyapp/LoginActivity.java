package com.example.journeyapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnLogin;
    private final String CREDINTIAL_SHARED_PREF = "our_shared_pref" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        btnLogin=findViewById(R.id.btnlogin);
        TextView btnRegister=findViewById(R.id.textViewSignUp);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences credentials=getSharedPreferences(CREDINTIAL_SHARED_PREF, Context.MODE_PRIVATE);
                String strEmail= credentials.getString("Email",null);
                String strPassword= credentials.getString("Password",null);

                String email_from_id= inputEmail.getText().toString();
                String password_from_id= inputPassword.getText().toString();


                if(strEmail != null && email_from_id!=null && strEmail.equalsIgnoreCase(email_from_id)){
                    if(strPassword != null && password_from_id!=null && strPassword.equalsIgnoreCase(password_from_id)){
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(LoginActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}