package com.example.mentalhealthandwellbeingapp;

import static com.example.mentalhealthandwellbeingapp.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private EditText username1, password1;
    private Button login;
    private FirebaseAuth auth1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username1 = findViewById(R.id.editTextText2);
        password1 = findViewById(R.id.editTextTextPassword2);
        login = findViewById(id.button2);
        auth1 = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName1 = username1.getText().toString();
                String passWord1 = password1.getText().toString();
                if(TextUtils.isEmpty(userName1) || TextUtils.isEmpty(passWord1)){
                    Toast.makeText(login.this, "enter both email and password", Toast.LENGTH_SHORT).show();
                }
                else{
                    login2(userName1,passWord1);
                }
            }
        });

    }

    private void login2(String username, String password){
        auth1.signInWithEmailAndPassword(username,password).addOnSuccessListener(login.this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(login.this,mainpage.class));
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(login.this, "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    };
}


