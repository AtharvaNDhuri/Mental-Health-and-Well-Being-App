package com.example.mentalhealthandwellbeingapp;

import android.content.Intent;
import android.os.Bundle;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class insightpage extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private DatabaseReference dailyAverageRef;
    private Map<String, Double> dailyAverages = new HashMap<>();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Button contactHelpButton;
    private Button counselingButton;
    private String userId;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insightpage);

        // Initialize UI elements
        contactHelpButton = findViewById(R.id.contactHelpButton);
        counselingButton = findViewById(R.id.counselingButton);
        calendarView = findViewById(R.id.calendarView);
        back = findViewById(R.id.backbutton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(insightpage.this, mainpage.class));
            }
        });

        // Retrieve the current user's ID
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Reference to Firebase node where daily averages are stored for this user
        dailyAverageRef = FirebaseDatabase.getInstance().getReference("DailyMoodAverages").child(userId);

        // Retrieve daily mood averages from Firebase and display on calendar
        retrieveDailyAverages();
    }

    private void retrieveDailyAverages() {
        dailyAverageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dailyAverages.clear(); // Clear old data
                for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                    String date = dateSnapshot.getKey(); // Date in "yyyy-MM-dd" format
                    Double averageMood = dateSnapshot.child("averageMood").getValue(Double.class);
                    if (averageMood != null) {
                        dailyAverages.put(date, averageMood);
                    }
                }
                // Display mood data on the calendar
                displayMoodOnCalendar(dailyAverages);
                checkTodayMoodAndShowOptions();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error fetching data", error.toException());
            }
        });
    }

    private void displayMoodOnCalendar(Map<String, Double> dailyAverages) {
        // Clear all previous decorators to avoid overlapping
        calendarView.removeDecorators();

        for (Map.Entry<String, Double> entry : dailyAverages.entrySet()) {
            String dateStr = entry.getKey();
            double averageMood = entry.getValue();

            // Convert date string to CalendarDay using the formatter
            try {
                LocalDate localDate = LocalDate.parse(dateStr, dateFormatter);
                CalendarDay day = CalendarDay.from(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());

                // Determine color based on mood score
                int moodColor;
                if (averageMood >= 2.5) {
                    moodColor = Color.GREEN; // Happy mood
                } else if (averageMood >= 1.5) {
                    moodColor = Color.YELLOW; // Neutral mood
                } else {
                    moodColor = Color.RED; // Sad mood
                }

                // Log the date and color for debugging
                Log.d("MoodDecorator", "Date: " + day + " Color: " + moodColor);

                // Add decoration to highlight this day on the calendar
                calendarView.addDecorator(new MoodDecorator(moodColor, day));

            } catch (Exception e) {
                Log.e("DateError", "Error parsing date: " + dateStr, e);
            }
        }
    }

    private void checkTodayMoodAndShowOptions() {
        LocalDate today = LocalDate.now();
        String todayStr = today.format(dateFormatter);

        Double todayMood = dailyAverages.get(todayStr);
        if (todayMood != null) {
            if (todayMood >= 2.5) {
                contactHelpButton.setVisibility(View.GONE);
                counselingButton.setVisibility(View.GONE);
            } else if (todayMood >= 1.5) {
                counselingButton.setVisibility(View.VISIBLE);
                contactHelpButton.setVisibility(View.GONE);

                counselingButton.setOnClickListener(v -> {
                    Intent intent = new Intent(insightpage.this, CounselingActivity.class);
                    startActivity(intent);
                });
            } else {
                contactHelpButton.setVisibility(View.VISIBLE);
                counselingButton.setVisibility(View.GONE);

                contactHelpButton.setOnClickListener(v -> {
                    Intent intent = new Intent(insightpage.this, ProfessionalHelpActivity.class);
                    startActivity(intent);
                });
            }
        } else {
            // Hide both options if no mood data is available for today
            contactHelpButton.setVisibility(View.GONE);
            counselingButton.setVisibility(View.GONE);
        }
    }
}
