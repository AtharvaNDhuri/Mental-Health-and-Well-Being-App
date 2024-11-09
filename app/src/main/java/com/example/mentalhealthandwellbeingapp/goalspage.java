package com.example.mentalhealthandwellbeingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class goalspage extends AppCompatActivity {

    private TextView suggestionTextView;
    private TextView goalTextView;
    private Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goalspage);

        suggestionTextView = findViewById(R.id.suggestionTextView);
        goalTextView = findViewById(R.id.goalTextView);
        ok = findViewById(R.id.okbutton);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(goalspage.this, mainpage.class));
            }
        });

        // Retrieve the average mood passed from moodtrack activity
        String averageMood = getIntent().getStringExtra("averageMood");

        if (averageMood != null) {
            String suggestion = getRandomSuggestion(averageMood);
            String dailyGoal = getDailyGoal(averageMood);

            suggestionTextView.setText(suggestion);
            goalTextView.setText(dailyGoal);
        } else {
            suggestionTextView.setText("Welcome! Let's set some goals.");
            goalTextView.setText("Reflect on your mood and set a goal for today.");
        }
    }

    private String getRandomSuggestion(String mood) {
        List<String> suggestions = generateSuggestions(mood);
        if (!suggestions.isEmpty()) {
            Random random = new Random();
            return suggestions.get(random.nextInt(suggestions.size()));
        }
        return "Remember to take care of yourself. Consider setting some personal goals.";
    }

    private String getDailyGoal(String mood) {
        List<String> goals = generateGoals(mood);
        if (!goals.isEmpty()) {
            Random random = new Random();
            return goals.get(random.nextInt(goals.size()));
        }
        return "Set a small, achievable goal to keep yourself motivated!";
    }

    private List<String> generateSuggestions(String mood) {
        List<String> suggestions = new ArrayList<>();
        switch (mood.toLowerCase()) {
            case "happy":
                suggestions.add("Keep up the positive energy! Try setting new goals to maintain your mood.");
                suggestions.add("Celebrate your achievements today! Maybe treat yourself to something you enjoy.");
                break;
            case "sad":
                suggestions.add("It's okay to feel down. Try some relaxation exercises to calm your mind.");
                suggestions.add("Consider reaching out to a friend or family member to talk.");
                break;
            case "neutral":
                suggestions.add("You’re in a balanced state. Consider activities to boost your positivity!");
                suggestions.add("Try doing something creative or learning a new skill to add excitement to your day.");
                break;
            default:
                suggestions.add("Remember to take care of yourself. Consider setting some personal goals.");
                break;
        }
        return suggestions;
    }

    private List<String> generateGoals(String mood) {
        List<String> goals = new ArrayList<>();
        switch (mood.toLowerCase()) {
            case "happy":
                goals.add("Make a list of three things that brought you joy today.");
                goals.add("Set a goal to try a new hobby or activity this week.");
                break;
            case "sad":
                goals.add("Take 10 minutes to meditate or practice deep breathing.");
                goals.add("Reach out to a friend or family member for a chat.");
                break;
            case "neutral":
                goals.add("Do a small act of kindness for someone around you.");
                goals.add("Spend some time in nature, even if it's a short walk.");
                break;
            default:
                goals.add("Take time to do something kind for yourself today.");
                goals.add("Set a goal to express gratitude – write down three things you're grateful for.");
                break;
        }
        return goals;
    }
}
