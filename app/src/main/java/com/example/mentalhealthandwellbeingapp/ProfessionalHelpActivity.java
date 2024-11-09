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

public class ProfessionalHelpActivity extends AppCompatActivity {

    private TextView helpInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_help);


        helpInfo = findViewById(R.id.helpInfo);
        helpInfo.setText("We recommend reaching out to a mental health professional for guidance and support.\n\n" +
                "Here are some options:\n" +
                "1. Call a mental health helpline: 1-800-HELP (example number)\n" +
                "2. Contact a local therapist.\n" +
                "3. Visit your nearest mental health center.");
    }
}