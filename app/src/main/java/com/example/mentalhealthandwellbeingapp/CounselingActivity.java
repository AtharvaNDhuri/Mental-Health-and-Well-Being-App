package com.example.mentalhealthandwellbeingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CounselingActivity extends AppCompatActivity {

    private TextView counselingInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counseling);


        counselingInfo = findViewById(R.id.counselingInfo);
        counselingInfo.setText("Counseling sessions can help you manage stress, find clarity, and improve your mental well-being.\n\n" +
                "Here are some options:\n" +
                "1. Book a session with a counselor.\n" +
                "2. Join a group counseling session.\n" +
                "3. Access free resources on mental well-being.");

    }
}