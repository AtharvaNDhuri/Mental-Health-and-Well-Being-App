package com.example.mentalhealthandwellbeingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class mainpage extends AppCompatActivity {

    private Button mood, insights, goals;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        mood = findViewById(R.id.button4);
        insights = findViewById(R.id.button7);
        goals = findViewById(R.id.button6);
        searchView = findViewById(R.id.searchView2);

        mood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainpage.this, moodtrack.class));
            }
        });

        insights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainpage.this, insightpage.class));
            }
        });

        goals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainpage.this, goalspage.class));
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                openGoogleSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void openGoogleSearch(String query) {
        // Format the query for Google search URL
        String googleSearchUrl = "https://www.google.com/search?q=" + Uri.encode(query);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleSearchUrl));
        startActivity(intent);
    }
}