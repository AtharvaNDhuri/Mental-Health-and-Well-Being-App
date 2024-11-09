package com.example.mentalhealthandwellbeingapp;

public class MoodData {
    private String mood1, mood2, mood3, mood4, mood5;
    private String timestamp;

    // Empty constructor required for Firebase
    public MoodData() {}

    public MoodData(String mood1, String mood2, String mood3, String mood4, String mood5, String timestamp) {
        this.mood1 = mood1;
        this.mood2 = mood2;
        this.mood3 = mood3;
        this.mood4 = mood4;
        this.mood5 = mood5;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getMood1() { return mood1; }
    public void setMood1(String mood1) { this.mood1 = mood1; }

    public String getMood2() { return mood2; }
    public void setMood2(String mood2) { this.mood2 = mood2; }

    public String getMood3() { return mood3; }
    public void setMood3(String mood3) { this.mood3 = mood3; }

    public String getMood4() { return mood4; }
    public void setMood4(String mood4) { this.mood4 = mood4; }

    public String getMood5() { return mood5; }
    public void setMood5(String mood5) { this.mood5 = mood5; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
