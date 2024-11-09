package com.example.mentalhealthandwellbeingapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class moodtrack extends AppCompatActivity {

    private ImageButton happy1, med1, sad1;
    private ImageButton happy2, med2, sad2;
    private ImageButton happy3, med3, sad3;
    private ImageButton happy4, med4, sad4;
    private ImageButton happy5, med5, sad5;
    private Button submit;

    private String mood1 = "";
    private String mood2 = "";
    private String mood3 = "";
    private String mood4 = "";
    private String mood5 = "";

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moodtrack);

        databaseReference = FirebaseDatabase.getInstance().getReference("MoodData");

        happy1 = findViewById(R.id.happy1);
        med1 = findViewById(R.id.med1);
        sad1 = findViewById(R.id.sad1);
        happy2 = findViewById(R.id.happy2);
        med2 = findViewById(R.id.med2);
        sad2 = findViewById(R.id.sad2);
        happy3 = findViewById(R.id.happy3);
        med3 = findViewById(R.id.med3);
        sad3 = findViewById(R.id.sad3);
        happy4 = findViewById(R.id.happy4);
        med4 = findViewById(R.id.med4);
        sad4 = findViewById(R.id.sad4);
        happy5 = findViewById(R.id.happy5);
        med5 = findViewById(R.id.med5);
        sad5 = findViewById(R.id.sad5);
        submit = findViewById(R.id.login);

        setMoodButtonListeners();

        submit.setOnClickListener(view -> submitMoods());
    }

    private void submitMoods() {
        if (mood1.isEmpty() || mood2.isEmpty() || mood3.isEmpty() || mood4.isEmpty() || mood5.isEmpty()) {
            Toast.makeText(this, "Please answer all questions before submitting", Toast.LENGTH_SHORT).show();
            return;
        }

        String entryId = databaseReference.push().getKey();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Create an object to hold the mood data
        MoodData moodData = new MoodData(mood1, mood2, mood3, mood4, mood5, timestamp);

        if (entryId != null) {
            // Save mood data to Firebase
            databaseReference.child(entryId).setValue(moodData)
                    .addOnSuccessListener(aVoid -> {
                        double averageMood = calculateAverageMood(moodData);
                        String moodLabel = getMoodLabel(averageMood);

                        // Retrieve the current user's ID
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        saveDailyAverageMood(userId, getDateOnly(timestamp), averageMood);

                        Intent intent = new Intent(moodtrack.this, goalspage.class);
                        intent.putExtra("averageMood", moodLabel);
                        startActivity(intent);

                        Toast.makeText(this, "Moods submitted successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to submit moods.", Toast.LENGTH_SHORT).show());
        }
    }

    private String getMoodLabel(double averageMood) {
        if (averageMood >= 2.5) {
            return "Happy";
        } else if (averageMood >= 1.5) {
            return "Neutral";
        } else {
            return "Sad";
        }
    }

    private String getDateOnly(String timestamp) {
        return timestamp.split(" ")[0]; // Get date in "yyyy-MM-dd" format
    }

    private double calculateAverageMood(MoodData moodData) {
        int totalScore = 0;
        String[] moods = {moodData.getMood1(), moodData.getMood2(), moodData.getMood3(), moodData.getMood4(), moodData.getMood5()};

        for (String mood : moods) {
            switch (mood) {
                case "Happy":
                    totalScore += 3;
                    break;
                case "Neutral":
                    totalScore += 2;
                    break;
                case "Sad":
                    totalScore += 1;
                    break;
            }
        }
        return (double) totalScore / moods.length;
    }

    private void saveDailyAverageMood(String userId, String date, double averageMood) {
        DatabaseReference dailyAverageRef = FirebaseDatabase.getInstance().getReference("DailyMoodAverages")
                .child(userId).child(date);
        dailyAverageRef.child("averageMood").setValue(averageMood);
    }

    private void setMoodButtonListeners() {
        // Group 1: How are you feeling right now?
        happy1.setOnClickListener(view -> {
            resetBackgrounds(1);
            mood1 = "Happy";
            happy1.setBackgroundColor(Color.parseColor("#00FF00")); // Green for Happy
        });

        med1.setOnClickListener(view -> {
            resetBackgrounds(1);
            mood1 = "Neutral";
            med1.setBackgroundColor(Color.parseColor("#FFFF00")); // Yellow for Neutral
        });

        sad1.setOnClickListener(view -> {
            resetBackgrounds(1);
            mood1 = "Sad";
            sad1.setBackgroundColor(Color.parseColor("#FF0000")); // Red for Sad
        });

        // Group 2: How did your day start?
        happy2.setOnClickListener(view -> {
            resetBackgrounds(2);
            mood2 = "Happy";
            happy2.setBackgroundColor(Color.parseColor("#00FF00")); // Green for Happy
        });

        med2.setOnClickListener(view -> {
            resetBackgrounds(2);
            mood2 = "Neutral";
            med2.setBackgroundColor(Color.parseColor("#FFFF00")); // Yellow for Neutral
        });

        sad2.setOnClickListener(view -> {
            resetBackgrounds(2);
            mood2 = "Sad";
            sad2.setBackgroundColor(Color.parseColor("#FF0000")); // Red for Sad
        });

        // Group 3: How do you feel after interacting with others?
        happy3.setOnClickListener(view -> {
            resetBackgrounds(3);
            mood3 = "Happy";
            happy3.setBackgroundColor(Color.parseColor("#00FF00")); // Green for Happy
        });

        med3.setOnClickListener(view -> {
            resetBackgrounds(3);
            mood3 = "Neutral";
            med3.setBackgroundColor(Color.parseColor("#FFFF00")); // Yellow for Neutral
        });

        sad3.setOnClickListener(view -> {
            resetBackgrounds(3);
            mood3 = "Sad";
            sad3.setBackgroundColor(Color.parseColor("#FF0000")); // Red for Sad
        });

        // Group 4: How do you feel about the tasks you accomplished?
        happy4.setOnClickListener(view -> {
            resetBackgrounds(4);
            mood4 = "Happy";
            happy4.setBackgroundColor(Color.parseColor("#00FF00")); // Green for Happy
        });

        med4.setOnClickListener(view -> {
            resetBackgrounds(4);
            mood4 = "Neutral";
            med4.setBackgroundColor(Color.parseColor("#FFFF00")); // Yellow for Neutral
        });

        sad4.setOnClickListener(view -> {
            resetBackgrounds(4);
            mood4 = "Sad";
            sad4.setBackgroundColor(Color.parseColor("#FF0000")); // Red for Sad
        });

        // Group 5: How do you feel about your current energy level?
        happy5.setOnClickListener(view -> {
            resetBackgrounds(5);
            mood5 = "Happy";
            happy5.setBackgroundColor(Color.parseColor("#00FF00")); // Green for Happy
        });

        med5.setOnClickListener(view -> {
            resetBackgrounds(5);
            mood5 = "Neutral";
            med5.setBackgroundColor(Color.parseColor("#FFFF00")); // Yellow for Neutral
        });

        sad5.setOnClickListener(view -> {
            resetBackgrounds(5);
            mood5 = "Sad";
            sad5.setBackgroundColor(Color.parseColor("#FF0000")); // Red for Sad
        });
    }

    private void resetBackgrounds(int group) {
        switch (group) {
            case 1:
                happy1.setBackgroundColor(Color.parseColor("#B0CA9F"));
                med1.setBackgroundColor(Color.parseColor("#B0CA9F"));
                sad1.setBackgroundColor(Color.parseColor("#B0CA9F"));
                break;
            case 2:
                happy2.setBackgroundColor(Color.parseColor("#B0CA9F"));
                med2.setBackgroundColor(Color.parseColor("#B0CA9F"));
                sad2.setBackgroundColor(Color.parseColor("#B0CA9F"));
                break;
            case 3:
                happy3.setBackgroundColor(Color.parseColor("#B0CA9F"));
                med3.setBackgroundColor(Color.parseColor("#B0CA9F"));
                sad3.setBackgroundColor(Color.parseColor("#B0CA9F"));
                break;
            case 4:
                happy4.setBackgroundColor(Color.parseColor("#B0CA9F"));
                med4.setBackgroundColor(Color.parseColor("#B0CA9F"));
                sad4.setBackgroundColor(Color.parseColor("#B0CA9F"));
                break;
            case 5:
                happy5.setBackgroundColor(Color.parseColor("#B0CA9F"));
                med5.setBackgroundColor(Color.parseColor("#B0CA9F"));
                sad5.setBackgroundColor(Color.parseColor("#B0CA9F"));
                break;
        }
    }
}
