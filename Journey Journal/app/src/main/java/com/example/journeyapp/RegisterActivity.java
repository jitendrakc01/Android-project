package com.example.journeyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {
    private EditText edUsername;
    private EditText edEmail;
    private EditText edPassword;
    private EditText edConformPassword;
    private Button edRegister;

    private final String CREDENTIAL_SHARED_PREF = "our_shared_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView btn=findViewById(R.id.alreadyHaveAccount);
        edUsername=findViewById(R.id.inputUsername);
        edEmail=findViewById(R.id.inputEmail);
        edPassword=findViewById(R.id.inputPassword);
        edConformPassword=findViewById(R.id.inputConformPassword);
        edRegister=findViewById(R.id.btnRegister);

        edRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strPassword=edPassword.getText().toString();
                String strConfirmPassword= edConformPassword.getText().toString();
                String strEmail=edEmail.getText().toString();
                String strUsername=edUsername.getText().toString();




                if (strPassword !=null && strConfirmPassword !=null && strPassword.equalsIgnoreCase(strConfirmPassword)){
                    SharedPreferences credentials=getSharedPreferences(CREDENTIAL_SHARED_PREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=credentials.edit();
                    editor.putString("Password", strPassword);
                    editor.putString("Email", strEmail);
                    editor.commit();
                    Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();

                    RegisterActivity.this.finish();
                } else{
                    Toast.makeText(RegisterActivity.this, "Check Password", Toast.LENGTH_SHORT).show();

                }
            }


        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });





    }
}



