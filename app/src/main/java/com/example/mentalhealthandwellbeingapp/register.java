package com.example.mentalhealthandwellbeingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {

    private Button register1, login1;
    private EditText username, password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

            register1 = findViewById(R.id.register);
            login1 = findViewById(R.id.login);
            username = findViewById(R.id.editTextText);
            password = findViewById(R.id.editTextTextPassword);
            auth = FirebaseAuth.getInstance();

            register1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userName = username.getText().toString();
                    String passWord = password.getText().toString();

                    if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)){
                        Toast.makeText(register.this, "enter username and password both", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        regis(userName,passWord);
                    }
                }
            });

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(register.this, login.class));
            }
        });

        }

        private void regis(String username, String password){
            auth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(register.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(register.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }